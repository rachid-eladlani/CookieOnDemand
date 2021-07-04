package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.statistic.Statistic;
import fr.unice.polytech.cookieFactory.store.kitchen.Kitchen;
import fr.unice.polytech.cookieFactory.store.kitchen.StateKitchen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Store implements PropertyChangeListener{
    private static final int ONE_HOUR = 1;
    private static final double BEST_COOKIE_DISCOUNT = 0.1;
    private boolean isOpen;
    private final String id;
    private String address;
    private String name;
    private double tax;
    private final List<Order> ordersToPrepare;
    private final List<Order> ordersWaitingPickUp;
    private final List<Order> pastOrders;
    private final Map<DayOfWeek, LocalTime> openingTimes;
    private final Map<DayOfWeek, LocalTime> closingTimes;
    private Statistic pickUpStats;
    private final Kitchen kitchen;
    private final Stock stock;
    private final PropertyChangeSupport support;

    public Store(String address, double taxes) {
        isOpen = true;
        Random rd = new Random();
        id = Long.toString(rd.nextLong());
        this.tax= taxes;
        ordersToPrepare = new ArrayList<>();
        ordersWaitingPickUp = new ArrayList<>();
        pastOrders = new ArrayList<>();
        openingTimes = new HashMap<>();
        closingTimes = new HashMap<>();
        this.address = address;
        kitchen = new Kitchen();
        kitchen.addPropertyChangeListener(this);
        stock = new Stock();
        support = new PropertyChangeSupport(this);
        pickUpStats = getPickUpStats();
    }

    public Store(String name, String address, double taxes) {
        this(address, taxes);
        this.name = name;
    }

    public String getId() {
        return id;
    }

    /**
     * Add a new order to prepare to the shop
     * @param order: order added from the COD class
     */
    public void newOrder(Order order){
        if(isOpen)
            if(canHandleOrder(order)) {
                this.stock.removeIngredientsFromOrder(order);
                ordersToPrepare.add(order);
            }
        else
            System.out.println("Kitchen is broken or out of stock");
    }

    /**
     * When an order is ready to pick, an employee mark the order ready
     * @param orderId : barcode value given by the employee
     * @return true if the order was found
     */
    public boolean setOrderReady(String orderId){
        Order order = ordersToPrepare.stream().filter(o->orderId.equals(o.getOrderId())).findFirst().orElse(null);
        if(order != null) {
            order.ready();
            ordersWaitingPickUp.add(ordersToPrepare.remove(ordersToPrepare.indexOf(order)));
            return true; //order ready to picking up
        }
        return false; //order not found
    }
    public boolean setOrderReady(Order order){
        if(ordersToPrepare.contains(order) ) {
            order.ready();
            ordersWaitingPickUp.add(ordersToPrepare.remove(ordersToPrepare.indexOf(order)));
            return true; //order ready to picking up
        }
        return false; //order not found
    }

    public Stock getStock() {
        return stock;
    }

    /**
     * When a customer come to pickUp is order, an employee check if the order exist, if the order is ready and close it
     * @param orderId : barcode value given by the customer
     * @return true if the order was found and ready
     */
    public boolean validateOrder(String orderId){
        Order order = ordersWaitingPickUp.stream().filter(o->orderId.equals(o.getOrderId())).findFirst().orElse(null);
        if(order == null) return false; //order not found
        if(order.isReady()){
            order.picked();
            pastOrders.add(ordersWaitingPickUp.remove(ordersWaitingPickUp.indexOf(order)));
            pickUpStats.addOrder(order);
            return true; //order picked
        }
        return false; //order not ready
    }
    public boolean validateOrder(Order order){
        if(ordersWaitingPickUp.contains(order) && order.isReady()){
            order.picked();
            pastOrders.add(ordersWaitingPickUp.remove(ordersWaitingPickUp.indexOf(order)));
            pickUpStats.addOrder(order);
            return true; //order picked
        }

        return  false;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpeningTime(DayOfWeek day, LocalTime openHour) {
        openingTimes.put(day, openHour);
    }


    public void setClosingTime(DayOfWeek day, LocalTime closeHour) {
        closingTimes.put(day, closeHour);
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public void setTax(double newTaxValue){
        this.tax = newTaxValue;
    }

    public Map<DayOfWeek, LocalTime> getOpeningTimes() {
        return openingTimes;
    }

    public LocalTime getOpeningTime(DayOfWeek day) {
        return openingTimes.get(day);
    }

    public Map<DayOfWeek, LocalTime> getClosingTimes() {
        return closingTimes;
    }

    public LocalTime getClosingTime(DayOfWeek day) {
        return closingTimes.get(day);
    }

    public double getTax() {
        return tax;
    }

    public List<Order> getOrdersToPrepare() {
        return ordersToPrepare;
    }
    public List<Order> getOrdersWaitingPickUp() {
        return ordersWaitingPickUp;
    }
    public List<Order> getPastOrders() {
        return pastOrders;
    }


    /**
     * @param orderBefore : date given
     * @return a list of order that the shop must prepare before the date given in param
     */

    public List<Order> getOrderToPrepareBefore(LocalDateTime orderBefore){
        return ordersToPrepare.stream().filter(o-> o.getTheoreticalPickDate().isBefore(orderBefore)).collect(Collectors.toList());
    }

    /**
     * @param range : range in hours used to cut a day in period
     * @return a list of order that the shop must prepare before the date given in param
     */
    public Statistic getPickUpStats(int range){
        pickUpStats = new Statistic(range, openingTimes, closingTimes, pickUpStats);
        pickUpStats.init();
        pickUpStats.sortOrders(pastOrders);
        return pickUpStats;
    }


    public Statistic getPickUpStats(){
        if(pickUpStats == null)
            getPickUpStats(ONE_HOUR);
        return pickUpStats;
    }

    public void saveOldMonth(){
        getPickUpStats(ONE_HOUR);
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(address).append("\n");
        for(DayOfWeek day : openingTimes.keySet()){
            sb.append(day).append(": ").append(openingTimes.get(day)).
                    append(" - ").append(closingTimes.get(day)).append("\n");
        }
        return sb.toString();
    }

    public double getAmountWTaxes(double totalAmount) {
        return totalAmount + (totalAmount * tax);
    }

    public String getAddress() {
        return address;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /**
     * Retrieve orders of a store who can't handle his orders because of problem
     * @param orders
     * @return List of orders to prepare, his size can be zero
     */
    public List<Order> handleOrders(List<Order> orders){
        List<Order> ordersRemaining = new ArrayList<>(orders);
        for (Order o : orders){
            if(canHandleOrder(o) && !o.isCancelled()) {
                o.cancelOrder();
                o.getStore().getOrdersToPrepare().remove(o);
                newOrder(new Order(o.getShoppingCart(),this,o.getCustomer(),o.getTheoreticalPickDate()));
                ordersRemaining.remove(o);
            }
        }
        return ordersRemaining;
    }

    public boolean canHandleOrder(Order order){
        return this.stock.enoughIngredient(order.getShoppingCart().getIngredientOfCookiesOrdered());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ( ((StateKitchen) evt.getNewValue()).isKitchenBroken() ){
            isOpen = false;
            support.firePropertyChange("this",new Store("", 0),this);
        }else{
            isOpen = true;
            support.firePropertyChange("",new Store("", 0),this);
        }
    }


    public double discountBestCookie(Cookie cookie) {
        if(pickUpStats.getCookieOfTheMonth().equals(cookie)){
            return cookie.getPrice() + (cookie.getPrice() * BEST_COOKIE_DISCOUNT);
        }
        return cookie.getPrice();
    }

    public Cookie getCookieOfTheMonth(){
        return pickUpStats.getCookieOfTheMonth();
    }

    public boolean isCookieOfTheMonth(Cookie cookie) {
       return cookie.equals(pickUpStats.getCookieOfTheMonth());
    }
    
    public void setStatistics(Statistic stat) {
    	pickUpStats = stat;
    }
}