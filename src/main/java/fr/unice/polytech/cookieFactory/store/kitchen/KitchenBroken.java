package fr.unice.polytech.cookieFactory.store.kitchen;

public class KitchenBroken implements StateKitchen {

    @Override
    public boolean isKitchenBroken() {
        return true;
    }

    @Override
    public String toString() {
        return "kitchenBroken";
    }
}
