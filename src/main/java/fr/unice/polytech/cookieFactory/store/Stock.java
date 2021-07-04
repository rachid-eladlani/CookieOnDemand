package fr.unice.polytech.cookieFactory.store;

import java.util.*;

import fr.unice.polytech.cookieFactory.ingredient.Ingredient;
import fr.unice.polytech.cookieFactory.ingredient.IngredientQuantity;
import fr.unice.polytech.cookieFactory.ingredient.Topping;
import fr.unice.polytech.cookieFactory.order.Order;

public class Stock {
	private Set<IngredientQuantity> ingredients;

	public Stock(){
		ingredients = new HashSet<>();
		ingredients.add(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 0));
		ingredients.add(new IngredientQuantity(new Topping("MMS"), 0));
		ingredients.add(new IngredientQuantity(new Topping("REESESBUTTERCUP"), 0));
		ingredients.add(new IngredientQuantity(new Topping("WHITECHOCOLATE"), 0));
		ingredients.add(new IngredientQuantity(new Topping("Vanilla"), 0));
	}
	/***
	 * Retire une quantité d'un ingrédient dans le stock
	 * @param ingredientQuantity l'ingrédient dont on veut retirer une quantité
	 * @return un booléen, true si l'action a pu être effectuée, false si ça n'est pas le cas
	 */
	public boolean removeIngredients(IngredientQuantity ingredientQuantity) {
		IngredientQuantity ingrStock =  ingredients.stream().filter(e->ingredientQuantity.getIngredient().equals(e.getIngredient())).findFirst().orElse(null);
		if(ingrStock != null) {
			return ingrStock.reduceQuantity(ingredientQuantity);
		}
		return false;
	}
	/***
	 * Ajoute une quantité d'un ingrédient dans le stock
	 * @param ingredient l'ingrédient dont on veut retirer une quantité
	 */
	public void addIngredients(IngredientQuantity ingredient) {
		IngredientQuantity ingredientQuantity = ingredients.stream().filter( e -> e.getIngredient().equals(ingredient.getIngredient())).findFirst().orElse(null);
		if(ingredientQuantity != null)
			ingredientQuantity.addQuantity(ingredient);
		else
			ingredients.add(ingredient);

	}
	public Set<IngredientQuantity> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<IngredientQuantity> ingredients) {
		this.ingredients = ingredients;
	}

	public boolean hasEnoughStock(IngredientQuantity ingredient) {
		return ingredients.stream().filter(e -> ingredient.getIngredient().equals(e.getIngredient())).allMatch(e -> e.enoughQuantity(ingredient));
	}

	public boolean enoughIngredient(List<IngredientQuantity> ingredientQuantities){
		for(IngredientQuantity m : ingredientQuantities){
			if(!hasEnoughStock(m))
				return false;
		}
		return true;
	}
	public int getIngredientQuantity(Ingredient ingredientQuantity){
		IngredientQuantity ingredientQuantity1 = ingredients.stream().filter( e -> e.getIngredient().equals(ingredientQuantity)).findFirst().orElse(null);
		return ingredientQuantity1==null?0:ingredientQuantity1.getQuantity() ;
	}

	public int getAllIngredientsQuantity(){
		return ingredients.stream().mapToInt(IngredientQuantity::getQuantity).sum();
	}

	public IngredientQuantity getIngredientQuantity(IngredientQuantity ingredientQuantity){
		return ingredients.stream().filter( e -> e.equals(ingredientQuantity)).findFirst().orElse(null);
	}

	public void removeIngredientsFromOrder(Order order) {
		order.getShoppingCart().getIngredientOfCookiesOrdered().forEach(this::removeIngredients);
	}
}
