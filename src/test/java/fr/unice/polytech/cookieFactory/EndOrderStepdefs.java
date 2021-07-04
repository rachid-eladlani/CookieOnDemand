package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.store.Store;
import io.cucumber.java8.En;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EndOrderStepdefs implements En {
    Order order ;
    Store store ;
    ShoppingCart shoppingCart ;
    User user;
    public EndOrderStepdefs(){
        shoppingCart = new ShoppingCart();
        List< IngredientQuantity > t = new ArrayList<IngredientQuantity>(Arrays.asList(new IngredientQuantity (new Topping("MILKCHOCOLATE"),1), new IngredientQuantity (new Topping("WHITECHOLATE"),1)));

        Cookie cookie = new Cookie("rr",new IngredientQuantity (new Dough("rr"),1),new IngredientQuantity (new Flavour("rr"),1), new Toppings(t,new Mix("mixed")), new Cooking("Crunchy"));
        shoppingCart.addCookie(new CookieQty(cookie,5));
        store= new Store("73 avenue simone veil", 100);
        user = new User("fert","med","t@t.fr","simone veil");
        LocalDateTime pickupTime = LocalDateTime.of(2020, 02, 12, 12, 30);
        order = new Order(shoppingCart,store,user,pickupTime);


        Given("the command is ready to be picked up in a Store", ()->{
            store.getStock().addIngredients(new IngredientQuantity (new Topping("WHITECHOLATE"),100));
            store.getStock().addIngredients(new IngredientQuantity (new Topping("MILKCHOCOLATE"),100));
            store.newOrder(order);
            store.setOrderReady(order);
        });
        When("the customer came to collect his order with the id {string}", (String orderId)->{
            store.getPickUpStats();
            store.validateOrder(order);
        });
        Then("the order can't be reused and archived",()->{
           assertEquals(store.getOrdersWaitingPickUp().size() , 0);
           assertFalse(store.getOrdersWaitingPickUp().contains(order));
           assertEquals(store.getPastOrders().size(),1);
           assertTrue(store.getPastOrders().contains(order));
        });
    }

}
