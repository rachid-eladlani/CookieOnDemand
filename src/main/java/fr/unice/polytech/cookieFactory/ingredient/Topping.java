package fr.unice.polytech.cookieFactory.ingredient;

public class Topping extends Ingredient {
    public Topping(String name) {
        super(name);
    }
    public Topping(String name , Double price){
        super(name ,price);
    }
    @Override
    public String toString() {
        return "Topping{" +
                "name='" + name + '\'' +
                '}';
    }
}
