package fr.unice.polytech.cookieFactory.ingredient;

public class Flavour extends Ingredient {
    public Flavour(String name) {
        super(name);
    }
    public Flavour(String name , Double price){
        super(name ,price);
    }

    @Override
    public String toString() {
        return "Flavour{" +
                "name='" + name + '\'' +
                '}';
    }
}
