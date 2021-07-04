package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.statistic.DayStat;
import fr.unice.polytech.cookieFactory.statistic.PeriodStat;
import fr.unice.polytech.cookieFactory.statistic.Statistic;
import fr.unice.polytech.cookieFactory.store.Manager;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.Guest;
import fr.unice.polytech.cookieFactory.users.User;
import fr.unice.polytech.cookieFactory.utils.IllogicalTimeException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckStatsStepdefs implements En {
    COD cod = new COD();
    Store store;
    Manager manager;
    Statistic stat;
    DayStat ds;
    LocalTime datetimePeriod;
    Cookie c;
    ShoppingCart sc;
    Order o;
    Customer guest;

    public CheckStatsStepdefs(){
        Given("a manager of name {string} and with store address {string}",
                (String name, String address) ->
                {
                    manager = new Manager(name, store);
                    cod.addStore(address, 0.20);
                    store = cod.getStoreByAddress(address);
                });
        And("the store openingTimes:", (DataTable table) -> {
            List<String> t = table.asList();
            for (int i = 0; i < t.size(); i++){
                LocalTime time = LocalTime.parse(t.get(i));
                store.setOpeningTime(DayOfWeek.of(i+1), time);
            }
        });
        And("the store closingTimes:", (DataTable table) -> {
            List<String> t = table.asList();
            for (int i = 0; i < t.size(); i++){
                LocalTime time = LocalTime.parse(t.get(i));
                store.setClosingTime(DayOfWeek.of(i+1), time);
            }
        });
        And("statistics with a range of {int} hours per period in a day", (Integer range) -> {
            stat = new Statistic(range, store.getOpeningTimes(),store.getClosingTimes());
            stat.init();
        });
        And("has {int} orders on: {string}", (Integer nbOrder,String date) -> {
            for(int i = 0; i < nbOrder; i++){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                sc = new ShoppingCart();
                c = new Cookie();
                c.setName("Chocolalala");
                sc.addCookie(new CookieQty( c,1));
                guest = new Guest();
                o = new Order(sc, store, guest, dateTime);
                o.setDateOfPickup(dateTime);
                cod.newOrder(guest, sc, store, dateTime);
                stat.addOrder(o);
                stat.getDaysStat().forEach(System.out::println);
            }
        });
        And("has {int} orders on:", (Integer nbOrder, DataTable table) -> {
            List<String> dates = table.asList();
            for (String date : dates) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                sc = new ShoppingCart();
                c = new Cookie();
                c.setName("Chocolalala");
                sc.addCookie( new CookieQty(c,1));
                guest = new Guest();
                o.setDateOfPickup(dateTime);
                cod.newOrder(guest, sc, store, dateTime);
                stat.addOrder(o);
            }
        });

        When("^\"([^\"]*)\" wants to check the statistics for the day (\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+)$", (String firstName, Integer year, Integer month, Integer day,Integer hour, Integer minutes) -> {
            LocalDateTime dateTime = LocalDateTime.of(year,month,day, hour,minutes);
            ds = stat.getDayStat(dateTime.getDayOfWeek());
        });

        Then("Statistics for {string} : {string}", (String date, String result) -> {
            assertEquals(ds.toString(), result);
        });
//6666666666666666666666666666666666666666666666666666

        When("^\"([^\"]*)\" wants to check the statistics for the day (\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+) \\(borderline case\\)$", (String firstName, Integer year, Integer month, Integer day,Integer hour, Integer minutes) -> {
            LocalDateTime dateTime = LocalDateTime.of(year,month,day, hour,minutes);
            ds = stat.getDayStat(dateTime.getDayOfWeek());

        });

        Then("^Statistics for orders ordered in (\\d+)-(\\d+)-(\\d+) between (\\d+):(\\d+) to (\\d+):(\\d+) is (\\d+)$", (Integer year, Integer month, Integer day, Integer hour1, Integer minutes1, Integer hour2, Integer minutes2, Integer nbOrder) -> {
            assertThrows(IllogicalTimeException.class, ()->stat.getDayStat(LocalDateTime.of(year,month,day,hour1,minutes1).getDayOfWeek()).isInPeriodList(LocalTime.of(hour1, minutes1)));
        });
//6666666666666666666666666666666666666666666666666666

        When("^\"([^\"]*)\" wants to check the statistics for the period (\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+)$", (String firstName, Integer year, Integer month, Integer day,Integer hour, Integer minutes) -> {
            LocalDateTime dateTime = LocalDateTime.of(year,month,day, hour,minutes);
            datetimePeriod= dateTime.toLocalTime();
            ds = stat.getDayStat(dateTime.getDayOfWeek());
        });

        Then("Statistics for the period {string} : {string}", (String date,String result) -> {
            for (PeriodStat p : ds.getPeriodStatList()){
                if (p.isInRange(datetimePeriod)){
                    System.out.println(result);
                    System.out.println(p);
                    assertEquals(p.toString(), result);
                }
            }
        });
    }

}
