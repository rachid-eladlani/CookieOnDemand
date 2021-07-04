package fr.unice.polytech.cookieFactory.users;

import fr.unice.polytech.cookieFactory.order.Order;


public class LoyaltyProgram {
    public static final double  DISCOUNT = 0.1;
    private boolean discountOnNextOrder;

    public void discountNextOrder(Order order){
        if(order.getSumCookiesOrdered() >= 30) discountOnNextOrder = true;
    }

    public double applyDiscount(double price){
        discountOnNextOrder = false;
        return calculateNewPrice(price);
    }

    private double calculateNewPrice(double price){
        return price - (price* DISCOUNT);
    }

    public boolean hasDiscount() {
        return discountOnNextOrder;
    }
}
