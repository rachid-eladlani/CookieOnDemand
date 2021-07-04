package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.store.kitchen.KitchenBroken;
import fr.unice.polytech.cookieFactory.users.Guest;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KitchenReportStepDefs implements En {
    COD cod;
    Store store;
    String addAnotherStore2;
    String addAnotherStore3;
    String addressCurStore ;
    List<Order> o;

    public KitchenReportStepDefs(){
        Given("a store located at {string}, named {string}, has {double}",(String addressS, String name, Double tax)->{
            cod = new COD();
            addressCurStore = addressS;
            cod.addStore(name,addressS, tax);
            cod.addStore("Rue Veil 32", 0.23);
            addAnotherStore2 = "Rue Veil 32";
            cod.addStore("Rue COCO 32", 0.23);
            addAnotherStore3 = "Rue COCO 32";
            cod.getStoreByAddress(addressS).getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 40));
            cod.getStoreByAddress(addressS).getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 40));
            cod.getStoreByAddress(addAnotherStore2).getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 20));
            cod.getStoreByAddress(addAnotherStore2).getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 20));
            cod.getStoreByAddress(addAnotherStore3).getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOLATE"), 20));
            cod.getStoreByAddress(addAnotherStore3).getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"), 20));

     });

        And("this store has {int} orders to prepare", (Integer nbOrders) -> {
            List<IngredientQuantity> l = new ArrayList<>(Arrays.asList(new IngredientQuantity (new Topping("WHITECHOLATE"),10), new IngredientQuantity (new Topping("MILKCHOCOLATE"),10)));

            o = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Cookie c = new Cookie("cookieName", new IngredientQuantity (new Dough("Papate"),1), new Toppings(l, new Mix("Un mix")), new Cooking("a feu doux"));
                CookieQty cookieQty1 = new CookieQty(c, 1);
                ShoppingCart sh = new ShoppingCart();
                sh.getCookiesOrdered().add(cookieQty1);
                o.add(new Order(sh, cod.getStoreByAddress(addressCurStore), new Guest(),LocalDateTime.now()));
            }
            o.forEach((order)->cod.getStoreByAddress(addressCurStore).newOrder(order));
            for (Order order : o) {
                System.out.println("order = " + order);
            }
        });

        When("the kitchen is out of service",()->{
            cod.getStoreByAddress(addressCurStore).getKitchen().setState(new KitchenBroken());
        });

        Then("the store cannot get any new order and the orders to prepare are redirect to other nearest stores",()->{
            assertEquals(cod.getStoreByAddress(addressCurStore).getOrdersToPrepare().size(), 0);
            assertEquals(cod.getStoreByAddress(addAnotherStore2).getOrdersToPrepare().size(), 2);
            assertAll(()->{
                assertTrue(cod.getStoreByAddress(addAnotherStore2).getOrdersToPrepare().contains(o.get(0)));
                assertTrue(cod.getStoreByAddress(addAnotherStore2).getOrdersToPrepare().contains(o.get(1)));
            });
            assertEquals(cod.getStoreByAddress(addAnotherStore3).getOrdersToPrepare().size(), 2);
            assertAll(()->{
                assertTrue(cod.getStoreByAddress(addAnotherStore3).getOrdersToPrepare().contains(o.get(2)));
                assertTrue(cod.getStoreByAddress(addAnotherStore3).getOrdersToPrepare().contains(o.get(3)));
            });
        });

    }
}
