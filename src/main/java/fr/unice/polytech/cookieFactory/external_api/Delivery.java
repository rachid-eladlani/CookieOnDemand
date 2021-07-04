package fr.unice.polytech.cookieFactory.external_api;

import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.users.Customer;

import java.time.LocalDateTime;

public class Delivery {

    public Order order;
    public String to;
    public String from;
    public Customer customerToDeliver;
    public double basicPrice;
    public LocalDateTime time;

    public Delivery(Order order) {
        this.order = order;
        this.to = order.getCustomer().getAddress();
        this.from = order.getStore().getAddress();
        this.customerToDeliver = order.getCustomer();
        this.basicPrice = order.getFinalAmount();
        this.time = order.getDeliveringTime();
    }

    public Order getOrder(){
        return order;
    }

    public Customer getCustomerToDeliver() {
        return customerToDeliver;
    }

    public double getBasicPrice() {
        return basicPrice;
    }

    public String getFromAddress() {
        return from;
    }

    public String getToAddress() {
        return to;
    }

    public LocalDateTime getTimeDelivery(){
        return time;
    }
}
