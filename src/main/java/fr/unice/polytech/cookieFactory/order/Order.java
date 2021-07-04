package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Order implements PropertyChangeListener {
    private final LocalDateTime dateOrder;
    private double finalAmount;
    private final ShoppingCart shoppingCart;
    private boolean paid;
    private final Store store;
    private final Customer customer;
    private final double basicAmount;
    private String[] discountReason;
    private boolean hasDiscount;
    private final String orderId;
    private boolean ready;
    private boolean picked;
    private boolean cancelled;
    private final LocalDateTime theoreticalPickDate; //theoretical date
    private LocalDateTime dateOfPickup; //date when the customer came
    private LocalDateTime deliveryTime; //

    public Order(ShoppingCart shoppingCart, Store store, Customer customer, LocalDateTime theoreticalPickDate){ // Ou mettre le shoppingCart
        this.shoppingCart = shoppingCart;
        Random r = new Random();
        this.dateOrder = LocalDateTime.now();
        orderId = Long.toString(Long.parseLong(customer.getId()) + r.nextLong());
        basicAmount = shoppingCart.getTotalAmount();
        finalAmount = basicAmount;
        this.store = store;
        this.customer = customer;
        this.theoreticalPickDate = theoreticalPickDate;
        this.paid = false;
        this.hasDiscount = false;
        this.ready = false;
        this.picked = false;
        this.cancelled = false;
        store.getKitchen().addPropertyChangeListener(this);
    }

    public LocalDateTime getDateOrder() {
        return this.dateOrder;
    }


    public double getAmount() {
        return this.finalAmount;
    }

    public double getBasicAmount() {
        return this.basicAmount;
    }

    public double getFinalAmount() {
        return this.finalAmount;
    }
    public void determineFinalAmount(double orderPrice){
        finalAmount = orderPrice;

    }
    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    public void paid() {
        this.paid = true;
        if(customer.isMemberLP())
            customer.memberLp(this);
    }

    public boolean waitPayment() {
        return !paid;
    }

    public Store getStore() {
        return store;
    }

    public String getEmail() {
        return customer.getEmail();
    }

    public void newPriceWithDelivery(double extraCost){
        this.finalAmount = finalAmount + extraCost;
    }

    public Customer getCustomer(){
        return customer;
    }

    public boolean isEmpty() {
        return getSumCookiesOrdered() == 0;
    }

    public int getSumCookiesOrdered(){
        return shoppingCart.getSumCookiesOrdered();
    }


    public String getOrderId() {
        return orderId;
    }

    public void ready() {
        this.ready = true;
    }

    public boolean isReady() {
        return ready;
    }


    /**
     * Mark the order picked and the date of picking
     */
    public void picked() {
        this.picked = true;
        this.dateOfPickup = LocalDateTime.now();
    }

    public LocalDateTime getTheoreticalPickDate() {
        return theoreticalPickDate;
    }

    public LocalDateTime getDateOfPickup(){
        return dateOfPickup;
    }
    public void setDateOfPickup(LocalDateTime date){
        dateOfPickup = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                "dateOrder=" + dateOrder +
                (hasDiscount ? ", amount before discount=" + basicAmount + ", discountReason='" + discountReason : "amount=" + finalAmount)
                +
                ", Cookies=" + shoppingCart +
                ", paid=" + paid +
                ", ready=" + ready +
                ", store=" + store +
                ", customer=" + customer +
                ", basicAmount=" + basicAmount +
                ", hasDiscount=" + hasDiscount +
                '}';
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        store.propertyChange(evt);
    }

    public void cancelOrder() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public double getTax() {
        return store.getTax();
    }

    public Cookie getCookieOfTheMonth() {
        return store.getCookieOfTheMonth();
    }

    public LocalDateTime getDeliveringTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime time){
        this.deliveryTime = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.finalAmount, finalAmount) == 0 &&
                paid == order.paid &&
                Double.compare(order.basicAmount, basicAmount) == 0 &&
                hasDiscount == order.hasDiscount &&
                ready == order.ready &&
                picked == order.picked &&
                Objects.equals(shoppingCart, order.shoppingCart) &&
                Objects.equals(customer, order.customer) &&
                Arrays.equals(discountReason, order.discountReason) &&
                Objects.equals(theoreticalPickDate, order.theoreticalPickDate) &&
                Objects.equals(dateOfPickup, order.dateOfPickup) &&
                Objects.equals(deliveryTime, order.deliveryTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(finalAmount, shoppingCart, paid, customer, basicAmount, hasDiscount, ready, picked, theoreticalPickDate, dateOfPickup, deliveryTime);
        result = 31 * result + Arrays.hashCode(discountReason);
        return result;
    }
}
