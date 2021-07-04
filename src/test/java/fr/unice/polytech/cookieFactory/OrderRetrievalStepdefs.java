package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRetrievalStepdefs implements En {
    COD cod = new COD();
    Store store;
    Customer customer;
    Order order;


    public OrderRetrievalStepdefs(){
        Given("a user of name {string}, first name {string}, his email is {string} and his billing address {string}",
                (String name,String firstName, String email, String address) ->
                {
                   String customerId = cod.addCustomer(name, firstName, email,address, false);
                   customer = cod.getCustomerById(customerId);
                });
        And("^a shopping cart with (\\d+) cookies \"([^\"]*)\", he would pick his order in the store \"([^\"]*)\" on (\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+)$", (Integer qty, String cookieName, String storeAddress, Integer day, Integer month, Integer year, Integer hour, Integer minute) -> {
            List<IngredientQuantity> l = new ArrayList<>(Arrays.asList(new IngredientQuantity (new Topping("WHITECHOLATE"),1), new IngredientQuantity (new Topping("MILKCHOCOLATE"),1)));
            Cookie cookie1 = new Cookie(cookieName, new IngredientQuantity (new Dough("Papate"),1), new Toppings(l, new Mix("Un mix")), new Cooking("a feu doux"));
            cod.addCookie(cookie1);
            cod.addStore(storeAddress, 0.1);
            store = cod.getStoreByAddress(storeAddress);
            LocalDateTime pickupTime = LocalDateTime.of(year, month, day, hour, minute);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.addCookie(new CookieQty(cookie1, qty));
            order = new Order(shoppingCart, store, customer, pickupTime);
            store.getStock().addIngredients(new IngredientQuantity (new Topping("WHITECHOLATE"),100));
            store.getStock().addIngredients(new IngredientQuantity (new Topping("MILKCHOCOLATE"),100));
            store.newOrder(order);
        });

        When("the order is ready to be retrieved", ()->{
            System.out.println(store.setOrderReady(order.getOrderId()));
            System.out.println(order);
        });

        Then("user retrieve it on the store", () -> {
            assertTrue(order.isReady());
        });

    }
}
