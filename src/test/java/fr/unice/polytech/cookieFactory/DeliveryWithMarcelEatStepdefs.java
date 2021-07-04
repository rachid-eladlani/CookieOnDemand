package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.external_api.Delivery;
import fr.unice.polytech.cookieFactory.external_api.DeliveryApp;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryWithMarcelEatStepdefs implements En {
    Store store;
    Customer customer;
    DeliveryApp deliveryApp;
    Delivery delivery;
    Order order;
    ShoppingCart shoppingCart;
    COD cod = new COD();

    public DeliveryWithMarcelEatStepdefs() {
        Given("^a customer of firstname \"([^\"]*)\", of name \"([^\"]*)\" and address \"([^\"]*)\"$",
                (String name, String firstName, String address) ->
                {
                    String customerId = cod.addCustomer(name, firstName, "ok@gmail.com", address, false);
                    customer = cod.getCustomerById(customerId);
                    shoppingCart = new ShoppingCart();
                });
        And("^a store named \"([^\"]*)\" at \"([^\"]*)\" and with (\\d+\\.\\d+) percent taxes$",
                (String storeName, String storeAddress, Double taxes) ->
                {
                    store = new Store(storeName, storeAddress, taxes);
                });

        /* First Scenario with simple Delivery */
        When("^\"([^\"]*)\" a customer add (\\d+) cookie \"([^\"]*)\" and (\\d+) cookie \"([^\"]*)\" on the shopping cart and chose simple delivery with MarcelEat$", (String firstName, Integer nbCookie1, String nameCookie1, Integer nbCookie2, String nameCookie2) -> {
            Cookie c1 = new Cookie();
            Cookie c2 = new Cookie();
            c1.setName(nameCookie1);
            c2.setName(nameCookie2);
            cod.addCookie(c1);
            cod.addCookie(c2);

            customer.getShoppingCart().addCookie(new CookieQty(c1, nbCookie1));
            customer.getShoppingCart().addCookie(new CookieQty(c2, nbCookie2));
            LocalDateTime pickupTime = LocalDateTime.of(2020, 12, 25, 12, 00);
            order = new Order(shoppingCart, store, customer, pickupTime);
            delivery = new Delivery(order);
            deliveryApp = new DeliveryApp();
        });

        Then("^MarcelEat confirms his order and send him a confirmation email$", () -> {
            assertEquals("3 Marie Ave, NYC", delivery.getToAddress());
            assertEquals("630 Old Country Rd., NYC", delivery.getFromAddress());
            assertEquals("Mac", delivery.getCustomerToDeliver().getFirstName());
            assertEquals("Chris", delivery.getCustomerToDeliver().getName());

            assertEquals(order.getFinalAmount(), delivery.getBasicPrice());
            assertEquals("ok@gmail.com", delivery.getCustomerToDeliver().getEmail());

            assertEquals("Confirmation simple : OK", deliveryApp.simpleDelivery(delivery));
        });

        /* Second Scenario with last minute Delivery */
        When("^\"([^\"]*)\" a customer add (\\d+) cookie \"([^\"]*)\" and (\\d+) cookie \"([^\"]*)\" on the shopping cart and chose last minute delivery with MarcelEat$", (String firstName, Integer nbCookie1, String nameCookie1, Integer nbCookie2, String nameCookie2) -> {
            Cookie c1 = new Cookie();
            Cookie c2 = new Cookie();
            c1.setName(nameCookie1);
            c2.setName(nameCookie2);
            cod.addCookie(c1);
            cod.addCookie(c2);
            customer.getShoppingCart().addCookie(new CookieQty(c1, nbCookie1));
            customer.getShoppingCart().addCookie(new CookieQty(c2, nbCookie2));
            LocalDateTime pickupTime = LocalDateTime.of(2020, 12, 25, 12, 00);
            order = new Order(shoppingCart, store, customer, pickupTime);
            delivery = new Delivery(order);
            deliveryApp = new DeliveryApp();
        });

        Then("^MarcelEat confirms his order and send him a confirmation email and the extra costs represent 50% of the total price order$", () -> {
            assertEquals("3 Marie Ave, NYC", delivery.getToAddress());
            assertEquals("630 Old Country Rd., NYC", delivery.getFromAddress());
            assertEquals("Mac", delivery.getCustomerToDeliver().getFirstName());
            assertEquals("Chris", delivery.getCustomerToDeliver().getName());

            //set price with extra cost because of the last minute delivery option
            assertEquals("Confirmation last-minute: OK", deliveryApp.lastMinuteDelivery(delivery));;

            assertEquals(order.getFinalAmount(), delivery.getBasicPrice() + deliveryApp.estimateOrderWithMarcelEatDelivery(delivery));
            assertEquals("ok@gmail.com", delivery.getCustomerToDeliver().getEmail());


        });
    }
}
