package fr.unice.polytech.cookieFactory.ingredient;

public class Cooking {
    private String name;
    public Cooking(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cooking{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName(){
        return name;
    }
}