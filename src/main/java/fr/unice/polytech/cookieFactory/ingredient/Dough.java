package fr.unice.polytech.cookieFactory.ingredient;

public class Dough extends Ingredient {
    public Dough(String name) {
        super(name);
    }
    public Dough(String name , Double price){
        super(name ,price);
    }

    @Override
    public String toString() {
        return "Dough{" +
                "name='" + name + '\'' +
                '}';
    }
}
