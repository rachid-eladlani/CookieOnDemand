package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CookieOnDemandeStepdefs implements En {
    Cookie cookie;
    IngredientQuantity toppingBasic ;
    IngredientQuantity toppingAdd ;
    IngredientQuantity toppingAdd2 ;
    Cookie cookie2 ;
    Toppings toppings ;
    ArrayList<IngredientQuantity> ingredientQuantityArrayList = new ArrayList<>();

    public CookieOnDemandeStepdefs(){
        Given("a cookie with the name of {string}",(String cookieName)->{
            toppingBasic = new IngredientQuantity (new Topping("MILKCHOCOLATE"),5)   ;
            List< IngredientQuantity > t = new ArrayList<>(Arrays.asList(toppingBasic, new IngredientQuantity (new Topping("WHITECHOLATE"),1)));
            cookie = new Cookie(cookieName,new IngredientQuantity (new Dough("rr"),1),new IngredientQuantity (new Flavour("rr"),1), new Toppings(t,new Mix("mixed")), new Cooking("Crunchy"));
        });
        And("a topping named {string} with a quantity {int}",(String toppingName ,Integer quantity )->{
            toppingAdd = new IngredientQuantity (new Topping(toppingName),quantity)   ;
            ingredientQuantityArrayList.add(toppingAdd);
            toppings = new Toppings(ingredientQuantityArrayList,null);
        });


        When("a customer want to add the toppingAdd to the cookie",()->{
            cookie2 = cookie.personalizedAdding(toppings);
        });
        Then("the topping is added",()->{
            assertEquals(cookie.getTopping().getToppings().size(),2);
            assertEquals(cookie2.getTopping().getToppings().size(),3);
            assertEquals(cookie2.getName(),"Personalized");
        });


        When("a customer want to add 2 toppings in a cookie with already 2 topping",()->{
            toppingAdd2 = new IngredientQuantity (new Topping("lotus"),5) ;
            toppings.addTopping(toppingAdd2);
            cookie2 = cookie.personalizedAdding(toppings);
        });
        Then("only one topping is added",()->{
            assertEquals(cookie.getTopping().getToppings().size(),2);
            assertEquals(cookie2.getTopping().getToppings().size(),3);
            assertEquals(cookie2.getName(),"Personalized");
        });


        When("delete an existing topping from a cookie",()->{
            toppings.removeTopping(toppingAdd);
            toppings.addTopping(toppingBasic);
            cookie2 = cookie.personalizedDeleting(toppings);
        });
        Then("the topping is deleted",()->{
            assertEquals(cookie.getTopping().getToppings().size(),2);
            assertEquals(cookie2.getTopping().getToppings().size(),1);
            assertEquals(cookie2.getName(),"Personalized");
        });

        When("delete non existing topping",()->{
            cookie2 = cookie.personalizedDeleting(toppings);
        });
        Then("cookie is not created",()->{
            assertNull(cookie2);
        });

        When("a customer want to change quantity of an Ingredient",()->{
            IngredientQuantity topping3 = new IngredientQuantity(new Topping("MILKCHOCOLATE"),3);
            cookie2 = cookie.personalizedQuantity(topping3);
        });

        Then("the quantity of the topping is created",()->{
            assertEquals(toppingBasic.getQuantity(),5);
            IngredientQuantity ingredientQuantity = cookie2.getTopping().getToppings().stream().filter(e-> e.getIngredient().getName().equals("MILKCHOCOLATE")).findFirst().orElse(null);
            assertEquals(ingredientQuantity.getQuantity(),3);
        });

        When("a customer want to change quantity of an non existing Ingredient",()->{
            IngredientQuantity topping3 = new IngredientQuantity(new Topping("MILK"),3);
            cookie2 = cookie.personalizedQuantity(topping3);
        });
        Then("the cookie is not created",()->{
            assertNull(cookie2);
        });


    }
}