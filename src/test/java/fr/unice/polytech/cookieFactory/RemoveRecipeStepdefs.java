package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RemoveRecipeStepdefs implements En {
    COD cod;
    Cookie cookieRecipe;
    Customer customer;

    public RemoveRecipeStepdefs(){
        Given("^a manager from the cookie factory, who has access to the system COD$", () -> {
            cod = new COD();
            cod.addStore("11 Roland Martins NYC", 0.3);
        });

        And("^a specific cookie recipe named \"([^\"]*)\", which is define by its type of dough \"([^\"]*)\", an optional flavour \"([^\"]*)\", up to three toppings (.*), a type of mixture \"([^\"]*)\", and finally a type of cooking \"([^\"]*)\" with a given price (\\d+\\.\\d+)$",
            (String name, String dough, String flavour, String toppings, String mix, String cooking, Double price) -> {
                List<IngredientQuantity> toppings1 = new ArrayList<>();
                for (String s : Collections.singletonList(toppings)){
                    toppings1.add(new IngredientQuantity (new Topping(s),1));
                }
                cookieRecipe = new Cookie(name, new IngredientQuantity (new Dough(dough),1), new IngredientQuantity (new Flavour(flavour),1), new Toppings( toppings1 , new Mix(mix)), new Cooking(cooking));
                cod.addCookie(cookieRecipe);
        });

        When("the manager remove a specific cookie recipe named \"([^\"]*)\", which is define by its type of dough \"([^\"]*)\", an optional flavour \"([^\"]*)\", up to three toppings (.*), a type of mixture \"([^\"]*)\", and finally a type of cooking \"([^\"]*)\" with a given price (\\d+\\.\\d+)$",
                (String name, String dough, String flavour, String toppings, String mix, String cooking, Double price) -> {
            cod.getCookies().stream().filter(c -> c.equals(cod.getCookieByName(name))).findFirst().ifPresent(c -> cod.getCookies().remove(c));
        });

        Then("^the cookie recipe has been removed into the COD, in the system so that it's removed also in each store so that it cannot be offered to customers$", () -> {
            assertFalse(cod.getCookies().contains(cookieRecipe));
        });
    }
}
