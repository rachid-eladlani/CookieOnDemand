package fr.unice.polytech.cookieFactory.users;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.store.kitchen.Kitchen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class LoyaltyProgramTest {
    LoyaltyProgram lp;
    Order order1 = mock(Order.class);
    Order order2;
    Cookie cookie = mock(Cookie.class);
    Customer customer = mock(Customer.class);
    Store store = mock(Store.class);
    ShoppingCart sc = mock(ShoppingCart.class);
    @BeforeEach
    void setUp() {
        lp = new LoyaltyProgram();
        when(order1.getSumCookiesOrdered()).thenReturn(5,30,4);
        when(store.getAmountWTaxes(anyInt())).thenReturn(20.0);
        when(order1.getStore()).thenReturn(store);
        when(customer.getId()).thenReturn("1");
        when(sc.getTotalAmount()).thenReturn(20.0);
        when(store.getKitchen()).thenReturn(new Kitchen());
        when(order1.getTax()).thenReturn(10.0);
        when(cookie.getName()).thenReturn("stan smith");
        when(store.getCookieOfTheMonth()).thenReturn(cookie);
        //when(store.getPickUpStats().getCookieOfTheMonth()).thenReturn(new Cookie());
        order2 = new Order(sc, store, customer, LocalDateTime.now());
    }

    @Test
    void discountNextOrder() {
        assertFalse(lp.hasDiscount());
        lp.discountNextOrder(order1);
        assertFalse(lp.hasDiscount()); //sum = 5 -> discount nok
        lp.discountNextOrder(order1); //sum = 30 -> discount ok
        assertTrue(lp.hasDiscount());
        lp.discountNextOrder(order1); //sum = 4 -> discount nok but previous one not used
        assertTrue(lp.hasDiscount());
    }

    @Test
    void applyDiscount() {
        assertEquals(20.0, order2.getAmount()); //amount to pay
        double finalAmount = lp.applyDiscount(order2.getAmount());
        assertEquals(20.0, order2.getAmount()); //amount to pay
        assertEquals(18.0, finalAmount); //amount w/ discount
    }

}