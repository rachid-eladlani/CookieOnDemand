package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.CC;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PayOrderStepdefs implements En{
    COD cod = new COD();
    Order order;
    Store store;
    Customer customer;
    public PayOrderStepdefs() {

        Given("^a user of name \"([^\"]*)\", first name \"([^\"]*)\", his email is \"([^\"]*)\" and his billing address \"([^\"]*)\",$",
                (String customerName, String customerFirstName, String email, String address) -> // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
                {
                   String customerId = cod.addCustomer(customerName, customerFirstName, email, address, false);
                   customer = cod.getCustomerById(customerId);
                });
        And("^a shopping cart with (\\d+) cookies \"([^\"]*)\", he would pick his order in the store \"([^\"]*)\" on (\\d+)/(\\d+)/(\\d+) at (\\d+):(\\d+)$", (Integer qty, String cookieName, String storeAddress, Integer day, Integer month, Integer year, Integer hour, Integer minute) -> {
            List<IngredientQuantity> l = new ArrayList<>(Arrays.asList(new IngredientQuantity ( new Topping("WHITECHOLATE"),1),new IngredientQuantity (  new Topping("MILKCHOCOLATE"),1)));
            Cookie cookie1 = new Cookie(cookieName, new IngredientQuantity(new Dough("Papate"),1), new Toppings(l, new Mix("Un mix")), new Cooking("a feu doux"));
            cod.addCookie(cookie1);
            cod.addStore(storeAddress, 0.1);
            LocalDateTime pickupTime = LocalDateTime.of(year, month, day, hour, minute);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.addCookie(new CookieQty(cookie1, qty));
            store = cod.getStoreByAddress(storeAddress);
            store.getStock().addIngredients(new IngredientQuantity ( new Topping("WHITECHOLATE"),100));
            store.getStock().addIngredients(new IngredientQuantity ( new Topping("MILKCHOCOLATE"),100));
            cod.newOrder(customer, shoppingCart, store, pickupTime);
        });


        When("^the user give the name: \"([^\"]*)\" of the credit card, the credit card number: (\\d+) , the date of expiration : (\\d+)/(\\d+) and the CVV: (\\d+)$", (String ccName, Long ccNo, Integer ccMonth, Integer ccYear, Integer ccCVV) -> {
            CC cc = new CC(ccName, ccNo, ccMonth, ccYear, ccCVV, true);
            order = cod.getOrderWaiting(customer);
            assertTrue(cod.pay(cc, order));
        });
        Then("^if the payment is accepted, the customer is order is confirmed$", () -> {
            assertTrue(order.isPaid());
        });

    }

}