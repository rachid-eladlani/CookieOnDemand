package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MonthStat {
    List<DayStat> dayStats = new ArrayList<>();
    Month month;


    MonthStat(List<DayStat> dayStats){
        this.dayStats = dayStats;
        this.month = LocalDateTime.now().getMonth();
    }

    public MonthStat() {
    }

    public void sortOrders(List<Order> pastOrders) {
        pastOrders.forEach(this::sortOrder);
    }

    /**
     * We will call this function when we want to store the hour of an order for stats
     * Increment the number of orders within the PeriodStat (which is depend on a dayStat) of the concerned order
     * @param order
     */
    void sortOrder(Order order){
        dayStats.stream().filter(d -> d.getDay().equals(order.getDateOfPickup().getDayOfWeek())).findFirst().ifPresent(ds -> ds.orderToSort(order));
    }

    public Month getMonth(){
        return month;
    }

    public DayStat getDayStat(DayOfWeek day){
        return dayStats.stream().filter(d->d.getDay().equals(day)).findFirst().orElse(null);
    }

    public List<DayStat> getDaysStat() {
        return dayStats;
    }

    public List<CookieQty> getMonthOrderedCookies(){
        List<CookieQty> cookiesOrdered = new ArrayList<>();
        dayStats.forEach(p -> cookiesOrdered.addAll(p.getDayOrderedCookies()));
        return cookiesOrdered.stream().sorted().collect(Collectors.toList());
    }

}
