package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.CC;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class loyaltyProgram implements En {
    Customer customer;
    Cookie cookieMocked = mock(Cookie.class);
    Order order;
    Order order2;
    CC cc = mock(CC.class);
    ShoppingCart shoppingCart = mock(ShoppingCart.class);
    COD cod;

    public loyaltyProgram() {
        cod =  new COD();
        cod.addCookie(cookieMocked);
        Given("^a user of name \"([^\"]*)\", first name \"([^\"]*)\", his email is \"([^\"]*)\" and his billing address \"([^\"]*)\", which subscribed to the loyaltyProgram$", (String name, String firstname, String email, String address) -> {
            cod.addStore("8 Mar Av",0);
            String customerId = cod.addCustomer(name, firstname,email,address, true);
            customer = cod.getCustomerById(customerId);
        });
        When("^the user makes an order with (\\d+) cookies, which is more or equal to 30, and pay (\\d+.\\d+) for it$", (Integer qty, Double price) -> {
            when(shoppingCart.getSumCookiesOrdered()).thenReturn(qty);
            when(shoppingCart.getCookiesOrdered()).thenReturn(Collections.singletonList(new CookieQty(cookieMocked, 1)));
            when(cookieMocked.getPrice()).thenReturn(price);
            when(cc.saveCC()).thenReturn(false);
            cod.newOrder(customer,shoppingCart, cod.getStoreManagement().getStoreByAddress("8 Mar Av"), LocalDateTime.now());  //fake order to use the function from cod "pay"
            order = cod.getOrderWaiting(customer);
            cod.pay(cc, order);
        });
        Then("^the user got 10% off on his next order and paid (\\d+.\\d+)$", (Double price) -> {
            assertTrue(customer.hasLoyaltyDiscount());
            assertEquals(price, order.getAmount());
        });

        Given("^a user of name \"([^\"]*)\", first name \"([^\"]*)\", his email is \"([^\"]*)\" and his billing address \"([^\"]*)\", which subscribed to the loyaltyProgram and has 10% off on his next order$", (String name, String firstname, String email, String address) -> {
            cod.addStore("8 Mar Av",0);
            String customerId = cod.addCustomer(name, firstname,email,address, true);
            customer = cod.getCustomerById(customerId);
            //simulate order with qty >= 30
            when(shoppingCart.getSumCookiesOrdered()).thenReturn(30);
            when(cc.saveCC()).thenReturn(false);
            cod.newOrder(customer,shoppingCart, cod.getStoreManagement().getStoreByAddress("8 Mar Av"), LocalDateTime.now());  //fake order to use the function from cod "pay"
            order = cod.getOrderWaiting(customer);
            when(shoppingCart.getTotalAmount()).thenReturn(50.00);
            cod.pay(cc, order);
        });

        When("^the user makes an order for an amount of (\\d+.\\d+) without discount$", (Double amount) -> {
            when(shoppingCart.getSumCookiesOrdered()).thenReturn(30);
            when(shoppingCart.getTotalAmount()).thenReturn(amount);
            when(shoppingCart.getCookiesOrdered()).thenReturn(Collections.singletonList(new CookieQty(cookieMocked, 1)));
            when(cookieMocked.getPrice()).thenReturn(amount);
            when(cc.saveCC()).thenReturn(false);
            cod.newOrder(customer,shoppingCart, cod.getStoreManagement().getStoreByAddress("8 Mar Av"), LocalDateTime.now());  //fake order to use the function from cod "pay"
            order2 = cod.getOrderWaiting(customer);
            System.out.println(order2.getBasicAmount());
            cod.pay(cc, order2);
        });

        Then("^the user get 10% off on his order and paid (\\d+.\\d+) instead of (\\d+.\\d+)$", (Double priceWDiscount, Double price) -> {
            assertEquals(priceWDiscount, order2.getFinalAmount());
            assertEquals(price, order2.getBasicAmount());
        });
    }
}
