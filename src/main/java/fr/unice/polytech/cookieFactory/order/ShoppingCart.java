package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.IngredientQuantity;
import fr.unice.polytech.cookieFactory.store.Store;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	private final List<CookieQty> cookieOrdered;

	private double amount;

	public ShoppingCart(){
		cookieOrdered = new ArrayList<>();
	}

	public ShoppingCart(List<CookieQty> shoppingCart){
		this();
		shoppingCart.forEach(this::addCookie);
	}

	/***
	 * Ajoute une quantité d'un cookie dans le shopping cart
	 * @param cookieQty le cookie dont on veut ajouter une unité de quantité
	 */
	public void addCookie(CookieQty cookieQty) {
		if (cookieQty.getQuantity() == 0) cookieOrdered.remove(cookieQty);
		else cookieOrdered.stream().filter((c)->c.sameCookie(cookieQty)).findFirst().ifPresentOrElse((e)-> e.addQuantity(cookieQty.getQuantity()), ()->cookieOrdered.add(cookieQty));
		updateAmount();
		System.out.println(amount);
	}

	public void removeCookie(CookieQty cookieQty) {
		cookieOrdered.stream().filter(c -> c.getCookie().
				equals(cookieQty.getCookie())).findFirst().ifPresent((e)-> e.reduceQuantity(cookieQty));
		updateAmount();
	}

	private void updateAmount() {
		final double[] newAmount = {0}; //array is needed by lambda expression
		cookieOrdered.forEach((c) -> newAmount[0] += c.getCookie().getPrice() * c.getQuantity());
		amount = newAmount[0];
	}


	public List<CookieQty> getCookiesOrdered() {
		return cookieOrdered;
	}

	//Amount without tax because the store is not known
	public double getTotalAmount() {
		amount = OrderManagement.priceCalculator.getShoppingCartPrice(this);
		return amount;
  	}

  	public double getAmountNoDiscount(){
		return OrderManagement.priceCalculator.getShoppingCartPriceNoDiscount(this);
	}


	public List<IngredientQuantity> getIngredientOfCookiesOrdered(){
		List<IngredientQuantity> ingredientQuantityList = new ArrayList<>();
		cookieOrdered.forEach((c)->c.getCookie().getAllIngredients().forEach(iQ-> ingredientQuantityList.add(new IngredientQuantity(iQ, c.getQuantity()))));
		return concatIngredientQuantity(ingredientQuantityList);
	}

	 List<IngredientQuantity> concatIngredientQuantity(List<IngredientQuantity> ingredientQuantityList) {
		List<IngredientQuantity> concatIngredientList = new ArrayList<>();
		ingredientQuantityList.forEach(i-> {
			IngredientQuantity iq = concatIngredientList.stream().filter(iqt-> iqt.sameIngredient(i)).findFirst().orElse(null);
			if (iq != null)
				iq.addQuantity(i);
			else concatIngredientList.add(new IngredientQuantity(i.getIngredient(), i.getQuantity()));
		});
		return concatIngredientList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(!cookieOrdered.isEmpty()) {
			cookieOrdered.forEach((c) -> sb.append(c.getQuantity()).append(" ").append(c.getCookie()).append(','));
			sb.deleteCharAt(sb.length() - 1); //to delete the last comma
			return sb.toString();
		}
		else return "no cookies in the shopping cart";
	}

	public int getSumCookiesOrdered(){
		return cookieOrdered.stream().mapToInt(CookieQty::getQuantity).sum();
	}


}
