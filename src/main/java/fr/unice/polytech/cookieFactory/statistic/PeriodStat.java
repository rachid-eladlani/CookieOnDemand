package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PeriodStat {
    private int orderQty;
    private List<CookieQty> cookiesOrdered;
    private LocalTime from;
    private LocalTime to;

    PeriodStat(LocalTime from, LocalTime to){
        cookiesOrdered = new ArrayList<>();
        this.from = from;
        this.to = to;
    }


    public void updatePeriod(Order order){
        sortCookiesOrdered(order);
        incOrder();
    }

    public void incOrder(){
        orderQty++;
    }

    void sortCookiesOrdered(Order order) {
        order.getShoppingCart().getCookiesOrdered().forEach(i-> {
            CookieQty cq = cookiesOrdered.stream().filter(c-> c.sameCookie(i)).findFirst().orElse(null);
            if (cq != null)
                cq.addQuantity(i);
            else cookiesOrdered.add(new CookieQty(i.getCookie(), i.getQuantity()));
        });
//        order.getShoppingCart().getCookiesOrdered().forEach(cookieOrderList -> cookiesOrdered.stream().filter(cookieOrderList::equals).forEach((e)->e.addQuantity(cookieOrderList)));
    }

    public int getOrderQty() {
        return orderQty;
    }

    public List<CookieQty> getCookiesOrdered(){
        return cookiesOrdered;
    }

    public boolean isInRange(LocalTime timeOfPickup) {
        return (timeOfPickup.equals(from) || timeOfPickup.isAfter(from)) && timeOfPickup.isBefore(to);
    }

    @Override
    public String toString() {
        return  "from=" + from.toString() +
                ", to=" + to.toString() +
                ", orderQty=" + orderQty;
    }
}
