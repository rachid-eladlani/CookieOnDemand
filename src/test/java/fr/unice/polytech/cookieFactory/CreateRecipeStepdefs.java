package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateRecipeStepdefs implements En {
    COD cod;
    Cookie cookieRecipe;
    Customer customer;

    public CreateRecipeStepdefs(){
        Given("^a manager from the cookie factory who has access to the system COD$", () -> {
            cod = new COD();
            cod.addStore("11 Roland Martins NYC", 0);
        });

        When("^the manager create a specific cookie recipe named \"([^\"]*)\", which is define by its type of dough \"([^\"]*)\", an optional flavour \"([^\"]*)\", up to three toppings (.*), a type of mixture \"([^\"]*)\", and finally a type of cooking \"([^\"]*)\" with a given price (\\d+\\.\\d+) and add it into the COD$",
                (String name, String dough, String flavour, String toppings, String mix, String cooking, Double price) -> {
                    List<IngredientQuantity > toppings1 = new ArrayList<>();
                    for (String s : Collections.singletonList(toppings)){
                        toppings1.add(new IngredientQuantity (new Topping(s),1));
                    }
                    cookieRecipe = new Cookie(name, new IngredientQuantity (new Dough(dough),1), new IngredientQuantity (new Flavour(flavour),1), new Toppings( toppings1 , new Mix(mix)), new Cooking(cooking));
                    cod.addCookie(cookieRecipe);
        });

        Then("^the cookie recipe has been added into the COD, in the system so that it's present in each store so that it can be offered to customers$", () -> {
            /*Now the recipe is availble in each store and visible for customers*/
            assertTrue(cod.getCookies().contains(cookieRecipe));
        });
    }
}
