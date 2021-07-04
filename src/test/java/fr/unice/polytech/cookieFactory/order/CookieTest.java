package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;


public class CookieTest {
    IngredientQuantity dough ;
    IngredientQuantity flavour ;
    IngredientQuantity topping;
    IngredientQuantity topping2 ;
    List<IngredientQuantity> toppingsList;
    Mix mix;
    Toppings toppings;
    Cooking cooking;
    Cookie cookie;
    Cookie cookie1;
    List<IngredientQuantity> toppingsListAdd;
    IngredientQuantity topping4;
    IngredientQuantity topping5;
    Toppings toppings3;
    Cookie cookie4;
    PriceCalculator priceCalculator;
    Store store = mock(Store.class);

    @BeforeEach
    void setup(){
        dough = new IngredientQuantity(new Dough("normal", 2.0),1);
        flavour = new IngredientQuantity(new Flavour("vanille", 1.0),1);
        topping  = new IngredientQuantity(new Topping("oreo", 0.5),1);
        topping2 = new IngredientQuantity(new Topping("kitkat", 1.0),1);
        toppingsList = new ArrayList<>();
        toppingsList.add(topping);
        toppingsList.add(topping2);
        mix = new Mix("none");
        toppings = new Toppings(toppingsList,mix);
        cooking = new Cooking("normal");
        cookie = new Cookie("chocolat",dough,flavour,toppings,cooking);
        cookie1 = new Cookie("chocolat",dough,toppings,cooking);
        toppingsListAdd = new ArrayList<>();
        topping4 = new  IngredientQuantity(new Topping("caramel"),1);
        topping5 = new IngredientQuantity(new Topping("blanc"),1);
        priceCalculator = new PriceCalculator(new Catalogue());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Cookie cookie2 = cookie.clone();
        assertEquals(cookie2.getAllIngredients(),cookie.getAllIngredients());
        Cookie cookie3 = cookie1.clone();
        assertEquals(cookie1.getAllIngredients(),cookie3.getAllIngredients());

    }

    @Test
    void personalizedAdd() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        toppingsListAdd.add(topping4);
        toppings3 = new Toppings(toppingsListAdd,mix);
        cookie4 = cookie.personalizedAdding(toppings3);
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        assertEquals(cookie4.getTopping().getToppings().size(),3);
        assertEquals(cookie4.getTopping().getToppings().get(2),topping4);
    }
    @Test
    void personalizedAddMoreThan3() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        toppingsListAdd.add(topping4);
        toppingsListAdd.add(topping5);
        toppings3 = new Toppings(toppingsListAdd,mix);
        Cookie cookie4 = cookie.personalizedAdding(toppings3);
        assertEquals(cookie4.getTopping().getToppings().size(),3);
        assertEquals(cookie4.getTopping().getToppings().get(2),topping4);
    }

    @Test
    void personalizedRemoveNonExistent() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        toppingsListAdd.add(topping4);
        Toppings toppings3 = new Toppings(toppingsListAdd,mix);
        Cookie cookie4 = cookie.personalizedDeleting(toppings3);
        assertNull(cookie4);
    }

    @Test
    void personalizedRemove() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        toppingsListAdd.add(topping);
        Toppings toppings3 = new Toppings(toppingsListAdd,mix);
        Cookie cookie4 = cookie.personalizedDeleting(toppings3);
        assertEquals(1,cookie4.getTopping().getToppings().size());
    }

    @Test
    void personalizedRemoveMoreThanExist() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        toppingsListAdd.add(topping);
        toppingsListAdd.add(topping2);
        toppingsListAdd.add(topping4);
        Toppings toppings3 = new Toppings(toppingsListAdd,mix);
        Cookie cookie4 = cookie.personalizedDeleting(toppings3);
        assertNull(cookie4);
    }

    @Test
    void getAllIngredients() {
        List<IngredientQuantity> expectedCookie1 = new ArrayList<>(toppingsList);
        expectedCookie1.add(dough);
        expectedCookie1.add(new IngredientQuantity(new Flavour("nature"),1));
        assertEquals(expectedCookie1, cookie1.getAllIngredients());
    }

    @Test
    void calculatePrice(){
        assertEquals(4.5 , cookie.getPrice());
    }

    @Test
    void personalizedQuantity() throws CloneNotSupportedException {
        assertEquals(cookie.getTopping().getToppings().size(),2 );
        assertEquals(cookie.getTopping().getToppings().get(0).getQuantity(),1);
        Cookie cookie5 = cookie.personalizedQuantity(new IngredientQuantity(new Topping("oreo"),3));
        assertEquals(cookie5.getTopping().getToppings().size(),2 );
        assertEquals(cookie5.getTopping().getToppings().get(0).getQuantity(),3);
    }
    }
