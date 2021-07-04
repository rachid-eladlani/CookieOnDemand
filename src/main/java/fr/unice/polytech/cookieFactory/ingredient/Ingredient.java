package fr.unice.polytech.cookieFactory.ingredient;

import java.util.Objects;

public abstract class Ingredient {
    public String name;
    public double price ;

    Ingredient(String name){
        this.name = name;
        this.price = 0 ;
    }
    Ingredient(String name , double price){
        this.name = name ;
        this.price = price ;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return name.equals(ingredient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public  double getPrice(){
        return price ;
    }
    public String getName(){
        return name;
    }
}
