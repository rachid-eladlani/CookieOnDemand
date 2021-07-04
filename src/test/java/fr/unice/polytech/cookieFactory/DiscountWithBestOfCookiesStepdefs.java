package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.OrderManagement;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.statistic.Statistic;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.store.kitchen.Kitchen;
import fr.unice.polytech.cookieFactory.users.CC;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiscountWithBestOfCookiesStepdefs implements En {
    COD cod = new COD();
    Store store = mock(Store.class);
    Customer customer;
    Statistic stat = mock(Statistic.class);
    Kitchen kitch = new Kitchen();
    double DISCOUNT_TEN = 0.9;

    public DiscountWithBestOfCookiesStepdefs(){
        when(store.getKitchen()).thenReturn(kitch);
        Given("a customer with firstname {string} and a name {string} and address {string}, his mail is {string}",
                (String name, String firstName, String address, String email) ->
                {
                    cod = new COD();
                    String customerId = cod.addCustomer(name, firstName, email,address, false);
                    customer = cod.getCustomerById(customerId);
                });

        And("a shopping cart filled with {int} cookies {string}", (Integer nbCookie, String cookie) -> {
            Cookie c = new Cookie();
            List<IngredientQuantity> n = new ArrayList<>();
            n.add(new IngredientQuantity(new Topping("Milk chocolate",2.0), 3));
            c.setDough(new IngredientQuantity(new Dough("Plain", 2.0),1));
            c.setCooking(new Cooking("Crunchy"));
            c.setTopping(new Toppings(n));
            c.setFlavour(new IngredientQuantity(new Flavour("Cinnamon",2.0),1));
            c.setName(cookie);
            cod.addCookie(c);
            customer.getShoppingCart().addCookie(new CookieQty(c,nbCookie));
        });

        And("the favorite store of the customer which has a BestCookieOfTheMonth: {string}", (String bestCookie) -> {
            when(this.store.isCookieOfTheMonth(any())).thenReturn(true);
            Cookie cookie = cod.getCookieByName(bestCookie);
            when(stat.getCookieOfTheMonth()).thenReturn(cookie);
        });

        When("he validate his order to pay it",() -> {
            when(store.getTax()).thenReturn(0.0);
            cod.newOrder(customer,customer.getShoppingCart(),store, LocalDateTime.now());
        });


        Then("he got a reduction because he has ordered cookies of the month", () -> {
            assertEquals(customer.getShoppingCart().getAmountNoDiscount() * DISCOUNT_TEN,
                    cod.getOrderWaiting(customer).getFinalAmount());
        });
        And("^the current national cookie of the month is: \"([^\"]*)\"$", (String cookie) -> {
            cod.addNationalCookieInCatalogue(cod.getCookieByName(cookie));
        });
    }
}
