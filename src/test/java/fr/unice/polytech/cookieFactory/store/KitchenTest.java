package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.COD;
import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.kitchen.KitchenBroken;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitchenTest {

    private COD cod;
    private Store s;
    private Order o;
    private ShoppingCart sh;
    private Customer customer;

    @BeforeEach
    void setup() {
        cod = new COD();
        sh = new ShoppingCart();
        s = new Store("12 Av O", 0);
        customer = new User("Dupont", "Paul", "paul@gmail.com", "17 Av W");
        o = new Order(sh, s, customer, LocalDateTime.now());
    }

    @Test
    void kitchenBroken(){
        cod.addStore("Rue Picot", 0.23);
        s.getKitchen().setState(new KitchenBroken());
        assertTrue(s.getKitchen().getState().isKitchenBroken());

        s.newOrder(o);
        assertEquals(s.getOrdersToPrepare().size(), 0);
    }

    @Test
    void storeRedirectAfterKitchenBroken(){
        cod.addStore("Rue Picot 32", 0.23);
        cod.addStore("Rue Veil 32", 0.23);
        cod.addStore("Rue COCO 32", 0.23);
        cod.getStoreByAddress("Rue Picot 32").getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 40));
        cod.getStoreByAddress("Rue Picot 32").getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 40));
        cod.getStoreByAddress("Rue Veil 32").getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 20));
        cod.getStoreByAddress("Rue Veil 32").getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 20));
        cod.getStoreByAddress("Rue COCO 32").getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 20));
        cod.getStoreByAddress("Rue COCO 32").getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 20));

        List<IngredientQuantity> l = new ArrayList<>(Arrays.asList(new IngredientQuantity (new Topping("WHITECHOLATE"),10), new IngredientQuantity (new Topping("MILKCHOCOLATE"),10)));

        List<Order> o = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Cookie c = new Cookie("cookieName", new IngredientQuantity (new Dough("Papate"),1), new Toppings(l, new Mix("Un mix")), new Cooking("a feu doux"));
            CookieQty cookieQty1 = new CookieQty(c, 1);
            ShoppingCart sh = new ShoppingCart();
            sh.getCookiesOrdered().add(cookieQty1);
            o.add(new Order(sh, cod.getStoreByAddress("Rue Picot 32"), customer ,LocalDateTime.now()));
        }
        o.forEach((order)->cod.getStoreByAddress("Rue Picot 32").newOrder(order));

        cod.getStoreByAddress("Rue Picot 32").getKitchen().setState(new KitchenBroken());

        assertTrue(cod.getStoreByAddress("Rue Picot 32").getKitchen().getState().isKitchenBroken());

        assertEquals(cod.getStoreByAddress("Rue Picot 32").getOrdersToPrepare().size(), 0);
        assertEquals(cod.getStoreByAddress("Rue Veil 32").getOrdersToPrepare().size(), 2);
        assertEquals(cod.getStoreByAddress("Rue COCO 32").getOrdersToPrepare().size(), 2);
    }
}