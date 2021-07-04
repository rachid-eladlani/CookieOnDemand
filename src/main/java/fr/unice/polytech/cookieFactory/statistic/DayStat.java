package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.store.Day;
import fr.unice.polytech.cookieFactory.utils.IllogicalTimeException;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DayStat {
    private DayOfWeek day;
    private int rangeInHours;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<PeriodStat> periodStatList = new ArrayList<>();

    DayStat(DayOfWeek day, LocalTime openingTime, LocalTime closingTime){
        this.day = day;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    /**
     * En fonction du nb de découpage (en heure) donnée en params, on va diviser les heures de vente de la journée par @rangeInHours qui créera des objets PeriodStat
     * qui chacune d'entre-elles contiendront un from-to d'un interval égale au resultat de la division précedente,
     * l'objet contiendra un entier qui s'incrémentera a chaque ajout de
     * @param rangeInHours
     */
    public void createPeriods(int rangeInHours){
        periodStatList = new ArrayList<>();
        this.rangeInHours = rangeInHours;
        LocalTime from = openingTime;
        while(from.isBefore(closingTime)){
            LocalTime to = from.plusHours(rangeInHours);
            if(to.isAfter(closingTime)) to = closingTime;
            periodStatList.add(new PeriodStat(from, to));
            from = to;
        }
    }

    public void orderToSort(Order order){
        getPeriodByOrder(order).updatePeriod(order);
    }

     PeriodStat getPeriodByOrder(Order order){
        return periodStatList.stream().filter(p->p.isInRange(order.getDateOfPickup().toLocalTime())).findFirst().orElse(null);
    }

    public List<PeriodStat> getPeriodStatList(){
        return periodStatList;
    }

    public DayOfWeek getDay(){
        return day;
    }

    public PeriodStat getPeriodByHour(LocalTime hour) {
        return periodStatList.stream().filter(ps->ps.isInRange(hour)).findFirst().orElse(null);
    }

    public List<CookieQty> getDayOrderedCookies(){
        List<CookieQty> cookiesOrdered = new ArrayList<>();
        periodStatList.forEach(p -> cookiesOrdered.addAll(p.getCookiesOrdered()));
        return cookiesOrdered;
    }

    public boolean isInPeriodList(LocalTime time) throws IllogicalTimeException{
        for(PeriodStat p :periodStatList){
            if (p.isInRange(time)){
                return true;
            }
        }
        throw new IllogicalTimeException("This time isn't among the periodList");
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < periodStatList.size();){
            s += periodStatList.get(i).toString();
            i++;
            if (i != periodStatList.size())
                s += " | ";
        }
        return s;
    }
}
