package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.statistic.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class StoreManagementTest {
    StoreManagement storeManagement;
    Store store_MarieAv = mock(Store.class);
    Store store_Square = mock(Store.class);
    Store store_East = mock(Store.class);
    Statistic statistic_MarieAv = mock(Statistic.class);
    Statistic statistic_Square = mock(Statistic.class);
    Statistic statistic_East = mock(Statistic.class);
    List<CookieQty> cookiesOrdered_MarieAv;
    List<CookieQty> cookiesOrdered_Square;
    List<CookieQty> cookiesOrdered_East;
    Cookie soChocolalala = new Cookie();
    Cookie soGood = new Cookie();
    Cookie soMuchMms = new Cookie();
    Cookie mapleSyrup = new Cookie();
    Cookie doubleChoc = new Cookie();
    Order marieAv_order1 = mock(Order.class);
    Order marieAv_order2 = mock(Order.class);
    Order marieAv_order3 = mock(Order.class);


    @BeforeEach
    void setUp() {
        storeManagement = new StoreManagement();
        storeManagement.addStore(store_MarieAv);
        storeManagement.addStore(store_East);
        storeManagement.addStore(store_Square);

        when(store_MarieAv.getPickUpStats()).thenReturn(statistic_MarieAv);
        when(store_Square.getPickUpStats()).thenReturn(statistic_Square);
        when(store_East.getPickUpStats()).thenReturn(statistic_East);

        cookiesOrdered_MarieAv = new ArrayList<>();
        cookiesOrdered_Square = new ArrayList<>();
        cookiesOrdered_East = new ArrayList<>();

        when(statistic_MarieAv.getCookieOrdered()).thenReturn(cookiesOrdered_MarieAv);
        when(statistic_Square.getCookieOrdered()).thenReturn(cookiesOrdered_Square);
        when(statistic_East.getCookieOrdered()).thenReturn(cookiesOrdered_East);

        soChocolalala.setName("soChocolalala");
        soGood.setName("soGood");
        soMuchMms.setName("soMuchMms");
        mapleSyrup.setName("mapleSyrup");
        doubleChoc.setName("doubleChoc");

        cookiesOrdered_MarieAv.add(new CookieQty(soChocolalala, 402));
        cookiesOrdered_MarieAv.add(new CookieQty(soGood, 21));

        cookiesOrdered_Square.add(new CookieQty( soChocolalala, 70));
        cookiesOrdered_Square.add(new CookieQty(soGood, 650));
        cookiesOrdered_Square.add(new CookieQty(soMuchMms, 20));
        cookiesOrdered_Square.add(new CookieQty(doubleChoc, 308));

        cookiesOrdered_East.add( new CookieQty(soChocolalala, 100));
        cookiesOrdered_East.add( new CookieQty(doubleChoc, 3));

    }

    @Test
    void getAllOrderedCookies() {
        List<CookieQty> expectedOrderedCookies = new ArrayList<>();
        expectedOrderedCookies.add(new CookieQty(soChocolalala, 572));
        expectedOrderedCookies.add(new CookieQty(soGood, 671));
        expectedOrderedCookies.add(new CookieQty(soMuchMms, 20));
        expectedOrderedCookies.add(new CookieQty(doubleChoc, 311));
        List<CookieQty> cq = storeManagement.getAllOrderedCookies();
        assertEquals(expectedOrderedCookies.size(), cq.size());
    }

    @Test
    void getCookieOfTheMonth() {
        assertEquals(soGood, storeManagement.getCookieOfTheMonth());
        cookiesOrdered_MarieAv.add(new CookieQty(doubleChoc, 372));
        assertEquals(doubleChoc, storeManagement.getCookieOfTheMonth());
    }

    @Test
    void redirectToAnotherStore(){
        when(store_East.handleOrders(anyList())).thenReturn(Arrays.asList(marieAv_order1, marieAv_order3));
        when(store_Square.handleOrders(anyList())).thenReturn(Collections.singletonList(marieAv_order3));
        storeManagement.redirectToAnotherStore(Arrays.asList(marieAv_order1, marieAv_order2, marieAv_order3), store_MarieAv);
        verify(marieAv_order3).cancelOrder();
        verify(marieAv_order2, times(0)).cancelOrder();
        verify(marieAv_order1, times(0)).cancelOrder();
        reset(marieAv_order1);
        reset(marieAv_order2);
        reset(marieAv_order3);
        when(store_East.handleOrders(anyList())).thenReturn(Arrays.asList(marieAv_order1, marieAv_order2));
        when(store_Square.handleOrders(anyList())).thenReturn(Collections.singletonList(marieAv_order2));
        storeManagement.redirectToAnotherStore(Arrays.asList(marieAv_order1, marieAv_order2, marieAv_order3), store_MarieAv);
        verify(marieAv_order2).cancelOrder();
        verify(marieAv_order3, times(0)).cancelOrder();
        verify(marieAv_order1, times(0)).cancelOrder();
    }
}