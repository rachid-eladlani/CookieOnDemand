package fr.unice.polytech.cookieFactory.users;

import fr.unice.polytech.cookieFactory.order.Order;

public class Guest extends Customer {
    public Guest(String name, String firstName, String mail, String address) {
        super(name, firstName, mail, address);
    }

    public Guest() {
        super();
    }

    @Override
    public boolean hasLoyaltyDiscount() {
        return false;
    }

    @Override
    public boolean isMemberLP() {
        return false;
    }

    @Override
    public void subscribeLoyaltyProgram(){
        //do nothing
    }

    @Override
    public double applyDiscount(double price) {
        return price;
        //no discount
    }

    @Override
    public void saveCreditCard(CC cc) {
        //do nothing
    }

    @Override
    public boolean hasCreditCardSaved() {
        return false;
    }

    @Override
    public void memberLp(Order order) {

    }
}
