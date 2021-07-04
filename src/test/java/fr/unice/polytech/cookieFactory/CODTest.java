package fr.unice.polytech.cookieFactory;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.store.kitchen.Kitchen;
import fr.unice.polytech.cookieFactory.users.CC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.User;

public class CODTest {
	COD cod ;
    Customer customer;
    ShoppingCart shoppingCart;
    Cookie cookie;
    CC creditCard = mock(CC.class);
    Order order;
    Store store = mock(Store.class);
    Kitchen kitchen = mock(Kitchen.class);

    @BeforeEach
    void setup() {
    	 customer = new User("kaplan", "julien", "j.K@mail.com", "chezjulien"); 
    	 cod = new COD();
    	 List<IngredientQuantity > toppingList = new ArrayList<>();
		 toppingList.add(new IngredientQuantity (new Topping("Chocolala"),1));
		 toppingList.add(new IngredientQuantity (new Topping("CoffeePowder"),1));
    	 cookie = new Cookie("cookie",new IngredientQuantity ( new Dough("dough"),1), new IngredientQuantity ( new Flavour("Vanilla"),1), new Toppings(toppingList,new Mix("mixed")), new Cooking("cooking"));
    	 shoppingCart = new ShoppingCart();
    	 when(store.getKitchen()).thenReturn(kitchen);
	 }


    @Test
    void addValidCookie() {
    	List<IngredientQuantity > toppingList = new ArrayList<>();
    	toppingList.add(new IngredientQuantity (new Topping("Chocoo"),1));
    	Cookie c = new Cookie("cookie1", new IngredientQuantity (new Dough("dough1"),1), new Toppings( toppingList, new Mix("mix1")), new Cooking("cooking1"));
    	cod.addCookie(c);
    	assertEquals(c, cod.getCookieByName("cookie1"));
    	cod.addCookie(cookie);
		assertEquals(cookie, cod.getCookieByName("cookie"));
    }

    @Test
	void pay(){
    	when(creditCard.saveCC()).thenReturn(true);
		cod.newOrder(customer,shoppingCart, store, LocalDateTime.now());
		order = cod.getOrderWaiting(customer);
		assertTrue(cod.pay(creditCard, order));
		assertFalse(cod.pay(creditCard, order));
	}
}
