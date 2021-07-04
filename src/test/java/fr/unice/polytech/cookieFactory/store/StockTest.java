package fr.unice.polytech.cookieFactory.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import fr.unice.polytech.cookieFactory.COD;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.cookieFactory.ingredient.*;

import java.util.ArrayList;
import java.util.List;

public class StockTest {
	private Stock stock;
	private COD cod;
	private Store store;
	private Order o;
	private ShoppingCart sh;
	private Customer c;
	
	@BeforeEach
	void setup() {
		cod = new COD();
		cod.addStore("Rue des colles", 0.30);
		store = cod.getStoreByAddress("Rue des colles");
		stock = new Stock();
	}

	 @Test
	 void addIngredients() {
		 stock.addIngredients(new IngredientQuantity( new Topping("MILKCHOCOLATE") , 50));
		 stock.addIngredients(new IngredientQuantity(new Flavour("Vanilla"), 5));
		 assertEquals(50, stock.getIngredientQuantity(new Topping("MILKCHOCOLATE")));
		 assertEquals(5, stock.getIngredientQuantity(new Flavour("Vanilla")));
	 }
	 
	 @Test
	 void removeIngredients() {
		 stock.addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 50));
		 stock.addIngredients(new IngredientQuantity(new Flavour("Vanilla"), 5));
		 
		 assertFalse(stock.removeIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 60)));
		 assertEquals(50, stock.getIngredientQuantity(new Topping("MILKCHOCOLATE")));
		 
		 assertTrue(stock.removeIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 2)));
		 assertEquals(48, stock.getIngredientQuantity(new Topping("MILKCHOCOLATE")));
	 
		 assertTrue(stock.removeIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 48)));
		 assertEquals(0, stock.getIngredientQuantity(new Topping("MILKCHOCOLATE")));
	 }

	@Test
	void enoughIngredient(){
		List<IngredientQuantity> lI = new ArrayList<>();
		store.getStock().addIngredients(new IngredientQuantity( new Topping("MILKCHOCOLATE") , 50));
		store.getStock().addIngredients(new IngredientQuantity(new Flavour("Vanilla"), 5));
		lI.add(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 50));
		lI.add(new IngredientQuantity(new Flavour("Vanilla"), 5));
		boolean isEnough = store.getStock().enoughIngredient(lI);
		assertTrue(isEnough);
	}

}
