package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.Catalogue;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.Email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderManagement {
    public static PriceCalculator priceCalculator;
    List<Order> ordersWaitingPayment = new ArrayList<>();
    List<Order> ordersPaidWaitingPickUp = new ArrayList<>();
    public OrderManagement(Catalogue catalogue){
        priceCalculator = new PriceCalculator(catalogue);
    }

    public void orderWaitForPayment(ShoppingCart shoppingCart, Store store, Customer customer, LocalDateTime pickingTime) {
        Order newOrder = new Order(shoppingCart, store, customer, pickingTime);
        ordersWaitingPayment.add(newOrder);
        priceCalculator.setOrderPrice(newOrder);
    }

    public void orderPaid(Order order) {
        ordersWaitingPayment.remove(order);
        ordersPaidWaitingPickUp.add(order);
    }

    public Order getWaitingOrder(Customer customer) {
        return ordersWaitingPayment.stream().filter(o->o.getCustomer().equals(customer)).findFirst().orElse(null);
    }

    public Email confirmationOrder(Order order){
        return new Email(order.getEmail(), "Confirmation order", "Your id Order needed for the pickup is: " + order.getOrderId(), "...");
    }

    public boolean isOrderUnpaid(Order order) {
        return ordersWaitingPayment.contains(order);
    }
}
