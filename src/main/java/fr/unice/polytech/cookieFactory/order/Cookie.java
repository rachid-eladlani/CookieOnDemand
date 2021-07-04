package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.*;

import java.util.*;

public class Cookie implements Cloneable {
    private String name;
    private IngredientQuantity dough;
    private IngredientQuantity flavour ;
    private Toppings toppings;
    private Cooking cooking;
    private boolean personalized;

    public double getPrice() {
        return (dough == null ? 0 : dough.getPrice()) +
                    (toppings == null ? 0 : toppings.getPrice()) +
                    (flavour == null ? 0 : flavour.getPrice());
        }

    public Cookie(String name, IngredientQuantity dough, IngredientQuantity flavour, Toppings toppings, Cooking cooking) {
        this.name = name;
        this.dough = dough;
        this.flavour = flavour;
        this.toppings = toppings;
        this.cooking = cooking;
    }

    public Cookie(String name, IngredientQuantity dough, Toppings toppings , Cooking cooking) {
        this.name = name;
        this.dough = dough;
        this.toppings = toppings;
        this.cooking = cooking;
        this.flavour = new IngredientQuantity(new Flavour("nature", 0.0) , 1) ;
    }
    public Cookie( IngredientQuantity dough, IngredientQuantity flavour, Toppings toppings, Cooking cooking) {
        this.name = "Personalized";
        this.dough = dough;
        this.flavour = flavour;
        this.toppings = toppings;
        this.cooking = cooking;
    }

    public Cookie(){

    }

    public String getName() {
        return name;
    }

    public IngredientQuantity getDough() {
        return dough;
    }

    public IngredientQuantity getFlavour() {
        return flavour;
    }

    public Toppings getTopping() {
        return toppings;
    }


    public Cooking getCooking() {
        return cooking;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDough(IngredientQuantity dough) {
        this.dough = dough;
    }

    public void setFlavour(IngredientQuantity flavour) {
        this.flavour = flavour;
    }

    public void setTopping(Toppings topping) {
        if(topping.getToppings().size() <= 3){
            this.toppings = topping;
        }else{
            System.out.println("Too much toppings");
        }
    }


    public void setCooking(Cooking cooking) {
        this.cooking = cooking;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return name.equals(cookie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public Cookie clone() throws CloneNotSupportedException {
        Cookie clone = (Cookie) super.clone();
        clone.name = "Personalized" ;
        clone.setDough(new IngredientQuantity( new Dough(dough.getIngredient().name,dough.getIngredient().price),dough.getQuantity()));
        clone.setFlavour(new IngredientQuantity( new Flavour(flavour.getIngredient().name,flavour.getIngredient().price),flavour.getQuantity()));
        clone.setCooking(new Cooking(cooking.getName()));
        ArrayList<IngredientQuantity> toppings = new ArrayList<>();
        for(IngredientQuantity ingredientQuantity : this.getTopping().getToppings()){
            toppings.add(new IngredientQuantity(new Topping(ingredientQuantity.getIngredient().name, ingredientQuantity.getIngredient().getPrice()),ingredientQuantity.getQuantity()));
        }
        Mix mix = new Mix(this.toppings.getMix().getName());
        clone.setTopping(new Toppings(toppings,mix));
        return clone;
    }

    public void removeFlavour(Ingredient f){
        if(this.flavour.getIngredient().equals(f)){
            this.flavour = null;
        }
    }
    public Cookie personalizedAdding(Toppings toppings) throws CloneNotSupportedException {
        Cookie cookie1 = this.clone();
        for(IngredientQuantity topping : toppings.getToppings()){
            cookie1.addTopping(topping);
        }
        return cookie1;
    }

    public Cookie personalizedDeleting(Toppings toppings) throws CloneNotSupportedException {
        Cookie cookie1 = this.clone();
        for(IngredientQuantity topping : toppings.getToppings()){
            if(this.toppings.getToppings().contains(topping))
                cookie1.removeTopping(topping);
            else
                return null ;
        }
        return cookie1;
    }

    public Cookie personalizedQuantity(IngredientQuantity ingredientQuantity) throws CloneNotSupportedException {
        Cookie cookie1 = this.clone();
        IngredientQuantity ig = cookie1.getAllIngredients().stream().filter(i->i.sameIngredient(ingredientQuantity)).findFirst().orElse(null);
        if(ig != null ) {
            ig.setQuantity(ingredientQuantity);
            return cookie1 ;
        }
        return null ;
    }

    public void removeTopping(IngredientQuantity t){
        toppings.removeTopping(t);
    }

    public void addTopping(IngredientQuantity t){
        this.toppings.addTopping(t);
    }

    public List<IngredientQuantity> getAllIngredients(){
        List<IngredientQuantity> allIngredient = new ArrayList<>(toppings.getToppings());
        allIngredient.addAll(Arrays.asList(dough,flavour));

        return allIngredient;
    }

    public void editCookie(String name, IngredientQuantity dough, IngredientQuantity flavour, Toppings toppings, Cooking cooking){
        this.name = name;
        this.dough = dough;
        if(flavour != null)
            this.flavour = flavour;
        this.toppings = toppings;
        this.cooking = cooking;
    }
    public void editCookie(String name, IngredientQuantity dough, Toppings toppings, Cooking cooking){
        this.name = name;
        this.dough = dough;
        this.toppings = toppings;
        this.cooking = cooking;
    }
}
