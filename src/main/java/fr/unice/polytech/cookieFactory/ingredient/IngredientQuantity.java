package fr.unice.polytech.cookieFactory.ingredient;



import java.util.Objects;

public class IngredientQuantity {
    private Ingredient ingredient ;
    private int quantity ;

    public IngredientQuantity(Ingredient ingredient , int quantity){
        this.ingredient = ingredient ;
        this.quantity = quantity ;
    }

    public IngredientQuantity(IngredientQuantity iQ, Integer qty) {
        ingredient = iQ.ingredient;
        quantity = iQ.quantity * qty;
    }



    public void addQuantity(int quantity){
        this.quantity+= quantity ;
    }
    public void addQuantity(IngredientQuantity ingredientQuantity){
        this.quantity+= ingredientQuantity.getQuantity() ;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity ;
    }

    public void reduceQuantity(int quantity){
        if(this.enoughQuantity(quantity))
            this.quantity-= quantity ;
    }
    public boolean reduceQuantity(IngredientQuantity ingredientQuantity){
        if(this.enoughQuantity(ingredientQuantity.getQuantity())) {
            this.quantity -= ingredientQuantity.getQuantity();
            return true ;
        }
        return false ;
    }

    public Ingredient getIngredient(){
        return ingredient;
    }
    public int getQuantity(){
        return  quantity ;
    }

    public boolean enoughQuantity(int quantity){
        return this.quantity >= quantity;
    }
    public boolean enoughQuantity(IngredientQuantity ingredientQuantity){
        return this.quantity >= ingredientQuantity.getQuantity();
    }
    public boolean sameIngredient(IngredientQuantity ingredientQuantity){
        return ingredientQuantity.getIngredient().equals(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientQuantity that = (IngredientQuantity) o;
        return ingredient.equals(that.ingredient) && quantity == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, quantity);
    }

    @Override
    public String toString() {
        return "IngredientQuantity{" +
                "ingredient=" + ingredient +
                ", quantity=" + quantity +
                '}';
    }

    public double getPrice(){
        return quantity*ingredient.getPrice();
    }

    public void setQuantity(IngredientQuantity ingredientQuantity) {
        this.quantity = ingredientQuantity.getQuantity();
    }
}
