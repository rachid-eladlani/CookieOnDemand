package fr.unice.polytech.cookieFactory.ingredient;

import java.util.List;

public class Toppings {
    private List<IngredientQuantity> toppings;
    private Mix mix;

    public Toppings(List<IngredientQuantity> toppings, Mix mix){
        this.toppings = toppings;
        this.mix = mix;
    }

    public Toppings(List<IngredientQuantity> toppings){
        this.toppings = toppings;
    }

    public List<IngredientQuantity> getToppings() {
        return toppings;
    }

    public Mix getMix() {
        return mix;
    }

    @Override
    public String toString() {
        return "Toppings{" +
                "toppings=" + toppings +
                ", mix=" + mix +
                '}';
    }

    public void removeTopping(IngredientQuantity t) {
        IngredientQuantity ig = toppings.stream().filter(i->i.getIngredient().equals(t.getIngredient())).findFirst().orElse(null);
        if(ig != null){
            ig.reduceQuantity(t);
            if(ig.getQuantity() == 0)
                toppings.remove(ig);
        }
    }
    public void addTopping(IngredientQuantity t){
        if(this.getToppings().size() >= 3){
            System.err.println("Too much toppings already");
        }else{
            if(this.getToppings().contains(t)){
                int index = this.getToppings().indexOf(t);
                this.getToppings().get(index).addQuantity(t.getQuantity());
            }
            else{
                this.getToppings().add(t);
            }
        }
    }
    public double getPrice(){
        return toppings.stream().mapToDouble(IngredientQuantity::getPrice).sum();
    }

    public int nbTopping(){
        return toppings.size();
    }
}
