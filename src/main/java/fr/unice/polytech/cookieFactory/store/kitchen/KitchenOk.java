package fr.unice.polytech.cookieFactory.store.kitchen;

public class KitchenOk implements StateKitchen {

    @Override
    public boolean isKitchenBroken() {
        return false;
    }

    @Override
    public String toString() {
        return "kitchenOk";
    }
}
