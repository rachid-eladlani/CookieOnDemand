package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class StoreManagement implements PropertyChangeListener {
    private final List<Store> stores;


    public StoreManagement(){
        stores = new ArrayList<>();
    }


    public List<CookieQty> getAllOrderedCookies(){
        List<CookieQty> cookiesOrdered = new ArrayList<>();
        stores.forEach(s->cookiesOrdered.addAll(s.getPickUpStats().getCookieOrdered()));
        return concatOrderedCookies(cookiesOrdered);
    }

    private List<CookieQty> concatOrderedCookies(List<CookieQty> cookiesOrdered) {
        List<CookieQty> cookiesConcat = new ArrayList<>();
        cookiesOrdered.forEach(c->{
            CookieQty cq = cookiesConcat.stream().filter(cc->cc.sameCookie(c)).findFirst().orElse(null);
            if(cq != null){
                cq.addQuantity(c);
            } else {
                cookiesConcat.add(new CookieQty(c.getCookie(), c.getQuantity()));
            }
        });
        return cookiesConcat;
    }

    public Cookie getCookieOfTheMonth(){
        CookieQty c = getAllOrderedCookies().stream().sorted().findFirst().orElse(null);
        return c == null ? null : c.getCookie();
    }
    void redirectToAnotherStore(List<Order> orders, Store store){
        List<Order> remainingOrders = new ArrayList<>(orders);
        sortNearest(store).forEach(s-> {
            List<Order> remains;
            remains = s.handleOrders(remainingOrders);
            remainingOrders.clear();
            remainingOrders.addAll(remains);
        });
        remainingOrders.forEach(Order::cancelOrder);
    }

    List<Store> sortNearest(Store store){
        return stores.stream().filter(s->!store.equals(s)).collect(Collectors.toList());
    }

    public void addStore(Store store){
        stores.add(store);
        store.addPropertyChangeListener(this);
    }

    public void removeStore(String address) {
        stores.removeIf(s -> address.equals(s.getAddress()));
    }

    public List<Store> getStores(){
        return stores;
    }

    public void dispatchOrder(Order order) {
        order.getStore().newOrder(order);
    }

    public Store getStoreByAddress(String addr) {
        return stores.stream().filter(s->addr.equals(s.getAddress())).findFirst().orElse(null);
    }


    public void changeMonthStat(){
        stores.forEach(s->s.getPickUpStats().saveLastMonth());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.redirectToAnotherStore( ((Store)evt.getNewValue()).getOrdersToPrepare(), ((Store) evt.getNewValue()) );
    }

    public void changeOpeningTime(DayOfWeek day, LocalTime time, Store store) {
        store.setOpeningTime(day,time);
    }

    public void changeClosingTime(DayOfWeek day, LocalTime time, Store store) {
        store.setClosingTime(day, time);
    }
}
