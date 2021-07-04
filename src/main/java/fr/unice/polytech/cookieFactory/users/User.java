package fr.unice.polytech.cookieFactory.users;
import fr.unice.polytech.cookieFactory.order.Order;

public class User extends Customer {
    private CC creditCard;
    private boolean isMemberLP = false;
    private LoyaltyProgram loyaltyProgram;

    public User(String name, String firstName, String mail, String address) {
        super(name, firstName, mail, address);
    }

    @Override
    public boolean hasLoyaltyDiscount() {
        if (isMemberLP)
            return loyaltyProgram.hasDiscount();
        return false;
    }

    @Override
    public boolean isMemberLP() {
        return isMemberLP;
    }

    @Override
    public void subscribeLoyaltyProgram() {
        isMemberLP = true;
        loyaltyProgram = new LoyaltyProgram();
    }

    @Override
    public double applyDiscount(double price) {
        if (hasLoyaltyDiscount()) {
            return loyaltyProgram.applyDiscount(price);
        }
        return price;
    }

    @Override
    public void saveCreditCard(CC cc) {
        this.creditCard = cc;
    }

    @Override
    public boolean hasCreditCardSaved() {
        return creditCard != null;
    }

    @Override
    public void memberLp(Order order) {
        loyaltyProgram.discountNextOrder(order);
    }

    public CC getSavedCC() {
        return creditCard;
    }
}

