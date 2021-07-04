package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.COD;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.CoderMalfunctionError;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PeriodStatTest {
    private PeriodStat stat;
    private final Order order1 = mock(Order.class);
    private final Order order2 = mock(Order.class);
    private final LocalTime _9_AM = LocalTime.of(9,0);
    private final LocalTime _9_30_AM = LocalTime.of(9,30);
    private final LocalTime _10_AM = LocalTime.of(10,0);
    private final ShoppingCart shoppingCart1 = mock(ShoppingCart.class);
    private final ShoppingCart shoppingCart2 = mock(ShoppingCart.class);
    private final List<CookieQty> cookiesOrder1 = new ArrayList<>();
    private final List<CookieQty> cookiesOrder2 = new ArrayList<>();
    private final List<CookieQty> cookiesExpected = new ArrayList<>();
    private final COD cod = new COD();


    @BeforeEach
    void setUp(){
        stat = new PeriodStat(_9_AM, _10_AM);
        cookiesOrder1.add(new CookieQty(cod.getCookieByName("SooChocolalala"), 10));
        cookiesOrder2.add(new CookieQty(cod.getCookieByName("White Chocolate"), 15));
        cookiesOrder2.add(new CookieQty(cod.getCookieByName("SooChocolalala"), 15));
        cookiesExpected.add( new CookieQty(cod.getCookieByName("SooChocolalala"), 25));
        cookiesExpected.add(new CookieQty(cod.getCookieByName("White Chocolate"), 15));
        when(order1.getShoppingCart()).thenReturn(shoppingCart1);
        when(order2.getShoppingCart()).thenReturn(shoppingCart2);
        when(shoppingCart1.getCookiesOrdered()).thenReturn(cookiesOrder1);
        when(shoppingCart2.getCookiesOrdered()).thenReturn(cookiesOrder2);
    }

    @Test
    void sortCookiesOrdered() {
        assertTrue(stat.getCookiesOrdered().isEmpty());
        stat.sortCookiesOrdered(order1);
        stat.sortCookiesOrdered(order2);
        List<CookieQty> c = stat.getCookiesOrdered();
        int i =0;
        assertEquals(0, i);
        assertEquals(cookiesExpected, c);
    }

    @Test
    void isInRange() {
        assertTrue(stat.isInRange(_9_AM));
        assertTrue(stat.isInRange(_9_30_AM));
        assertFalse(stat.isInRange(_10_AM));
    }
}