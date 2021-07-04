package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.store.Day;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class Statistic {
    private MonthStat previousMonth;
    private MonthStat monthStat;
    private int range;
    private Map<DayOfWeek, LocalTime> openingTimes;
    private Map<DayOfWeek, LocalTime> closingTimes;

     /**
      *  Process: Statistic -> dayStat -> periodStat
      */

     public Statistic(int range, Map<DayOfWeek, LocalTime> openingTimes, Map<DayOfWeek, LocalTime> closingTimes){
         this.range = range;
         this.openingTimes = openingTimes;
         this.closingTimes = closingTimes;
         this.monthStat = new MonthStat();
     }

    public Statistic(int range, Map<DayOfWeek, LocalTime> openingTimes, Map<DayOfWeek, LocalTime> closingTimes, Statistic oldStat){
         this(range,openingTimes,closingTimes);
         if(oldStat != null)
             previousMonth = oldStat.getMonthStat();
        this.monthStat = new MonthStat();
    }

    public void saveLastMonth() {
                 previousMonth = monthStat;
                 monthStat = initMonthStat();
    }

    public MonthStat getMonthStat() {
        return monthStat;
    }


    public MonthStat getPreviousMonth() {
        return previousMonth;
    }



    /**
     * Initialize a statistic with the opening and closing hours of each days give on the constructor
     */
    public void init(){
        monthStat = initMonthStat();
    }

    private MonthStat initMonthStat(){
        List<DayStat> dayStats = new ArrayList<>();
        for(DayOfWeek day : openingTimes.keySet()){
            DayStat ds = new DayStat(day, openingTimes.get(day), closingTimes.get(day));
            ds.createPeriods(range); // create [range] periods within the dayStat object
            dayStats.add(ds);
        }
        return new MonthStat(dayStats);
    }

    public void sortOrders(List<Order> orders){
        monthStat.sortOrders(sameMonthOrders(orders));
    }

    private List<Order> sameMonthOrders(List<Order> orders) {
       return orders.stream()
               .filter(o->o.getDateOfPickup().getYear() == LocalDateTime.now().getYear())
               .filter(o->o.getDateOfPickup().getMonth() == LocalDateTime.now().getMonth())
               .collect(Collectors.toList());
    }

    public void addOrder(Order order) {
        checkMonth();
        monthStat.sortOrder(order);
    }

    private void checkMonth() {
        if(!monthStat.getMonth().equals(LocalDateTime.now().getMonth())){
            previousMonth = monthStat;
            monthStat = initMonthStat();
        }
    }

    public DayStat getDayStat(DayOfWeek day){
        return monthStat.getDayStat(day);
    }


    public List<DayStat> getDaysStat() {
        return monthStat.getDaysStat();
    }

    public Cookie getCookieOfTheMonth(){
        if(previousMonth == null || previousMonth.getMonthOrderedCookies().isEmpty()) return null;
        return previousMonth.getMonthOrderedCookies().stream().sorted().findFirst().orElseThrow().getCookie();
    }

    public List<CookieQty> getCookieOrdered(){
        if(previousMonth == null) return Collections.emptyList();
        return previousMonth.getMonthOrderedCookies();
    }

}
