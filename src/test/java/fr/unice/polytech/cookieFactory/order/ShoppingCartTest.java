package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShoppingCartTest {

    ShoppingCart sc1;
    ShoppingCart sc2;
    Order order ;
    Catalogue catalogue ;
    List<Cookie> cookies;
    Cookie chocolalala = new Cookie();
    Cookie soooChocolate = new Cookie();
    Cookie darkTemptation = new Cookie();
    Dough dough_vanillaCost_1dot3 = new Dough("vanilla", 1.3);
    Dough dough_darkChocolateCost_1dot5 = new Dough("dark chocolate", 1.5);

    IngredientQuantity doughVanilla_qty2_Cost_2dot6 = new IngredientQuantity(dough_vanillaCost_1dot3, 2);
    IngredientQuantity doughVanilla_qty1_Cost_1dot3 = new IngredientQuantity(dough_vanillaCost_1dot3, 1);
    IngredientQuantity doughDarkChocolate_qty3_Cost_4dot5 = new IngredientQuantity(dough_darkChocolateCost_1dot5, 3);
    PriceCalculator priceCalculator;
    Store store;
    @BeforeEach
    void setUp() {
        sc1 = new ShoppingCart();
        sc2 = new ShoppingCart();
        cookies = new ArrayList<>();
        chocolalala.setName("Milk chocolate");
        soooChocolate.setName("White chocolate");
        darkTemptation.setName("Black chocolate");
        order = mock(Order.class);
        catalogue = mock(Catalogue.class);
        priceCalculator = new PriceCalculator(catalogue);
        store = mock(Store.class);
    }

    @Test
    void addCookie(){
        CookieQty chocola1 = new CookieQty(chocolalala, 3);
        CookieQty chocola2 = new CookieQty(chocolalala, 3);
        sc1.addCookie(chocola1);
        assertEquals(3, sc1.getSumCookiesOrdered());
        sc1.addCookie(chocola2);
        assertEquals(6, sc1.getSumCookiesOrdered());
        sc1.addCookie(new CookieQty(soooChocolate, 3));
        assertEquals(9, sc1.getSumCookiesOrdered());
        //assertEquals(16.50, sc1.getTotalAmount());
        sc1.addCookie(new CookieQty(soooChocolate, 2));
        //assertEquals(14, sc1.getTotalAmount());
        assertTrue(sc1.getCookiesOrdered().contains(chocola1));
        CookieQty chocola3 = new CookieQty(chocolalala, 0);
        sc1.addCookie(chocola3);
        assertFalse(sc1.getCookiesOrdered().contains(chocola3));
    }


    @Test
    void concatIngredientQuantity(){
        List<IngredientQuantity> expectedIngredientQty = new ArrayList<>();
        expectedIngredientQty.add(new IngredientQuantity(dough_vanillaCost_1dot3, 3));
        expectedIngredientQty.add(new IngredientQuantity(dough_darkChocolateCost_1dot5, 3));
        assertEquals(expectedIngredientQty, sc1.concatIngredientQuantity(Arrays.asList(doughVanilla_qty1_Cost_1dot3, doughVanilla_qty2_Cost_2dot6, doughDarkChocolate_qty3_Cost_4dot5)));
    }

    @Test
    void generateShoppingCartPrice(){
        IngredientQuantity vanillaFlavour_Cost2 = new IngredientQuantity(new Flavour("vanille", 2.0),1);
        IngredientQuantity oreoTopping_1  = new IngredientQuantity(new Topping("oreo", 1.0),1);
        IngredientQuantity kitkatTopping_1 = new IngredientQuantity(new Topping("kitkat", 1.0),1);
        ArrayList<IngredientQuantity> toppingsList = new ArrayList<>();
        toppingsList.add(oreoTopping_1);
        toppingsList.add(kitkatTopping_1);
        Mix mix = new Mix("none");
        Toppings toppings_Cost2 = new Toppings(toppingsList,mix);
        Cooking cooking = new Cooking("normal");
        Cookie cookieCost_5dot3 = new Cookie("chocolat", doughVanilla_qty1_Cost_1dot3,vanillaFlavour_Cost2,toppings_Cost2,cooking);
        Cookie cookieCost_3dot3 = new Cookie("chocolat2", doughVanilla_qty1_Cost_1dot3,toppings_Cost2,cooking);
        when(store.isCookieOfTheMonth(any())).thenReturn(false, true, false);
        when(catalogue.isCookieOfTheMonth(any())).thenReturn(false, false, true);
        when(catalogue.isInCatalogue(any())).thenReturn(true);
        //no store reduction
        assertEquals(5.3 , priceCalculator.getCookiePrice(cookieCost_5dot3, store));
        //store reduction (cookie of the month)
        assertEquals(4.77 , priceCalculator.getCookiePrice(cookieCost_5dot3, store));
        //national reduction (cookie of the month)
        assertEquals(4.77 , priceCalculator.getCookiePrice(cookieCost_5dot3, store));


        when(store.isCookieOfTheMonth(any())).thenReturn(false);
        when(catalogue.isCookieOfTheMonth(any())).thenReturn(false);
        when(catalogue.isInCatalogue(any())).thenReturn(true);
        sc1.addCookie(new CookieQty(cookieCost_5dot3 , 10));
        //no reduction for multiple same cookies present in catalogue
        assertEquals(53 , priceCalculator.getShoppingCartPrice(sc1 , store));
        //price without reduction for 10 of the same cookie which is not in catalogue (personalized cookie)
        when(catalogue.isInCatalogue(any())).thenReturn(false);
        assertEquals(66.25, priceCalculator.getShoppingCartPrice(sc1 , store));
        CookieQty cookieQty_Cost6dot6 = new CookieQty(cookieCost_3dot3,2);
        sc1.addCookie(cookieQty_Cost6dot6);
        when(catalogue.isInCatalogue(any())).thenReturn(true);
        when(store.isCookieOfTheMonth(any())).thenReturn(false);
        when(catalogue.isCookieOfTheMonth(any())).thenReturn(false);
        //price with two kind of cookies without discount but which are presents in the catalogue
        assertEquals(59.6 , priceCalculator.getShoppingCartPrice(sc1, store));

        //price where cookieCost_5dot3 is the cookie of the month (both are in the catalogue)
        when(catalogue.isCookieOfTheMonth(cookieCost_5dot3)).thenReturn(true);
        when(catalogue.isCookieOfTheMonth(cookieCost_3dot3)).thenReturn(false);
        when(catalogue.isInCatalogue(any())).thenReturn(true);
        assertEquals(54.3 , priceCalculator.getShoppingCartPrice(sc1, store));

        //price where cookieCost_3dot3 is the cookie of the month (both are in the catalogue)
        when(catalogue.isCookieOfTheMonth(cookieCost_5dot3)).thenReturn(false);
        when(catalogue.isCookieOfTheMonth(cookieCost_3dot3)).thenReturn(true);
        when(catalogue.isInCatalogue(any())).thenReturn(true);
        assertEquals(58.94 , priceCalculator.getShoppingCartPrice(sc1, store));

        //price where cookieCost_3dot3 is the cookie of the month and cookieCost_5dot3 is not in the catalogue
        when(catalogue.isInCatalogue(cookieCost_5dot3)).thenReturn(false);
        when(catalogue.isInCatalogue(cookieCost_3dot3)).thenReturn(true);
        when(catalogue.isCookieOfTheMonth(cookieCost_3dot3)).thenReturn(true);
        when(catalogue.isCookieOfTheMonth(cookieCost_5dot3)).thenReturn(false);
        assertEquals(72.19 , priceCalculator.getShoppingCartPrice(sc1, store));
    }
}