package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EditRecipeStepdefs implements En {
    COD cod;
    Cookie cookieRecipe;
    Customer customer;
    Cookie cookieEdited;
    public EditRecipeStepdefs(){
        Given("^a manager from the cookie factory, who has access to the system CookieOnDemand$", () -> {
            cod = new COD();
            cod.addStore("11 Roland Martins NYC", 0.3);
        });

        When("^the manager edit a specific cookie recipe named \"([^\"]*)\", which is defined by its type of dough \"([^\"]*)\", an optional flavour \"([^\"]*)\", up to three toppings (.*), a type of mixture \"([^\"]*)\", and finally a type of cooking \"([^\"]*)\" with a given price (\\d+\\.\\d+)$", (String name, String dough, String flavour, String toppings, String mix, String cooking, Double price) -> {
            List<IngredientQuantity> toppings1 = new ArrayList<>();
            for (String s : Collections.singletonList(toppings)){
                toppings1.add(new IngredientQuantity (new Topping(s),1));
            }
            cookieRecipe = new Cookie(name, new IngredientQuantity (new Dough(dough),1), new IngredientQuantity (new Flavour(flavour),1), new Toppings( toppings1 , new Mix(mix)), new Cooking(cooking));
            cod.addCookie(cookieRecipe);
        });

        Then("^the cookie recipe with name \"([^\"]*)\" has been edited to \"([^\"]*)\", the type of dough will be \"([^\"]*)\", the flavour will be \"([^\"]*)\", up to three toppings \"([^\"]*)\", the type of mixture is change by \"([^\"]*)\", and finally the type of cooking is changed by \"([^\"]*)\" with a given price (\\d+)$",
                (String nameBegin,String toName, String dough, String flavour, String toppings, String mix, String cooking, Double price) -> {
                    List<IngredientQuantity> toppings1 = new ArrayList<>();
                    for (String s : Collections.singletonList(toppings)){
                        toppings1.add(new IngredientQuantity (new Topping(s),1));
                    }
                    cod.getCookieByName(nameBegin).editCookie(toName, new IngredientQuantity(new Dough(dough),1), new IngredientQuantity (new Flavour(flavour),1), new Toppings( toppings1 , new Mix(mix)), new Cooking(cooking));
                    assertEquals(toName, cod.getCookieByName(toName).getName());
                    assertEquals(dough, cod.getCookieByName(toName).getDough().getIngredient().getName());
                    assertEquals(flavour, cod.getCookieByName(toName).getFlavour().getIngredient().getName());
                    int i =0;
                    for (String s : Collections.singletonList(toppings)){
                        assertEquals(cod.getCookieByName(toName).getTopping().getToppings().get(i).getIngredient().getName(), s);
                        i++;
                    }
                    assertEquals(mix, cod.getCookieByName(toName).getTopping().getMix().getName());
                    assertEquals(cooking, cod.getCookieByName(toName).getCooking().getName());
        });

    }
}
