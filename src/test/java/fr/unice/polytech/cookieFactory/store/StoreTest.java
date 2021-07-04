package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.COD;
import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreTest {
    private Store store1;
    private final Order order1 = mock(Order.class);
    private final Order order2 = mock(Order.class);
    private final String ORDER_1 = "ORD1";
    private final String ORDER_2 = "ORD2";
    private final boolean READY = true;
    private boolean UNREADY = false;
    LocalTime _9_AM = LocalTime.of(9,0);
    LocalTime _6_PM = LocalTime.of(18,0);


    @BeforeEach
    void setUp() {
        List<CookieQty> listCookies = new ArrayList<>();
        List<IngredientQuantity> l = new ArrayList<>(Arrays.asList(new IngredientQuantity( new  Topping("WHITECHOCOLATE"),1),new IngredientQuantity( new Topping("MILKCHOCOLATE"),1)));
        listCookies.add(new CookieQty(new Cookie("Chocolate", new IngredientQuantity(new Dough("Papate"),1), new Toppings(l, new Mix("mixed")), new Cooking("cooked")),1));
        listCookies.add(new CookieQty(new Cookie("Temptation", new IngredientQuantity(new Dough("Papate"),1), new Toppings(l, new Mix("mixed")), new Cooking("cooked")),1));
        ShoppingCart sc= new ShoppingCart(listCookies);
        when(order1.getOrderId()).thenReturn(ORDER_1);
        when(order1.isReady()).thenReturn(READY);
        when(order1.getDateOfPickup()).thenReturn(LocalDateTime.of(0,1,1,14,0));
        when(order1.getShoppingCart()).thenReturn(sc);
        when(order2.getDateOfPickup()).thenReturn(LocalDateTime.of(0,1,1,14,0));
        when(order2.getOrderId()).thenReturn(ORDER_2);
        when(order2.isReady()).thenReturn(UNREADY);
        when(order2.getShoppingCart()).thenReturn(sc);

        store1 = new Store("",0.1 );
        store1.getStock().addIngredients(new IngredientQuantity(new Topping("WHITECHOCOLATE"),100));
        store1.getStock().addIngredients(new IngredientQuantity(new Topping("MILKCHOCOLATE"),100));
        store1.newOrder(order1);
        store1.setOpeningTime(DayOfWeek.MONDAY, _9_AM);
        store1.setClosingTime(DayOfWeek.MONDAY, _6_PM);
        store1.getPickUpStats();
    }


    @Test
    void setOrderReady() {
        assertTrue(store1.getOrdersToPrepare().contains(order1));
        assertFalse(store1.getOrdersWaitingPickUp().contains(order1));
        assertTrue(store1.setOrderReady(ORDER_1)); //an employee scan the bag which contains the order "ORD1" ->
        assertFalse(store1.getOrdersToPrepare().contains(order1));
        assertTrue(store1.getOrdersWaitingPickUp().contains(order1));
        assertFalse(store1.setOrderReady(ORDER_2)); //an employee scan the bag which contains the order "ORD2" -> false because not found
    }

    @Test
    void validateOrder() {
        assertTrue(store1.setOrderReady(ORDER_1));
        assertTrue(store1.getOrdersWaitingPickUp().contains(order1));
        assertFalse(store1.getPastOrders().contains(order1));
        assertTrue(store1.validateOrder(ORDER_1));
        assertFalse(store1.getOrdersWaitingPickUp().contains(order1));
        assertTrue(store1.getPastOrders().contains(order1));
        assertFalse(store1.validateOrder(ORDER_1)); //cannot pickup twice
        store1.newOrder(order2);
        assertFalse(store1.validateOrder(ORDER_2)); //order 2 is not ready
        when(order2.isReady()).thenReturn(READY);
        store1.setOrderReady(ORDER_2);
        assertTrue(store1.validateOrder(ORDER_2)); //order 2 is now ready
    }

    @Test
    void setOpeningTime() {
        store1.setOpeningTime(DayOfWeek.MONDAY, _9_AM);
        assertEquals(_9_AM, store1.getOpeningTime(DayOfWeek.MONDAY));
    }

    @Test
    void setClosingTime() {
        store1.setClosingTime(DayOfWeek.MONDAY, _6_PM);
        assertEquals(_6_PM, store1.getClosingTime(DayOfWeek.MONDAY));

    }

    @Test
    void getOrderToPrepareBefore() {
        store1.newOrder(order2);
        when(order1.getTheoreticalPickDate()).thenReturn(LocalDateTime.of(2020,10,22,14,0));
        when(order2.getTheoreticalPickDate()).thenReturn(LocalDateTime.of(2020,10,22,18,0));
        LocalDateTime orderDateMax = LocalDateTime.of(2020,10,22,17,59);
        List<Order> orderToPrepare = store1.getOrderToPrepareBefore(orderDateMax);
        assertTrue(orderToPrepare.contains(order1));
        assertFalse(orderToPrepare.contains(order2));
        orderDateMax = LocalDateTime.of(2020,10,22,18,1);
        orderToPrepare = store1.getOrderToPrepareBefore(orderDateMax);
        assertTrue(orderToPrepare.contains(order1));
        assertTrue(orderToPrepare.contains(order2));
    }

    @Test
    void getPickUpStats() {
    }

    @Test
    void canHandleOrders(){
        COD cod = new COD();
        cod.addStore("rue des Cookies", 0.2);
        cod.addStore("rue des Colles", 0.2);

        Set<IngredientQuantity> map1 = new HashSet<>();

        map1.add(new IngredientQuantity (new Topping("Chocolala"),230));
        map1.add(new IngredientQuantity (new Dough("Popopooo"),230));
        map1.add(new IngredientQuantity(new Flavour("Tornado"),230));
        Store s1 = cod.getStoreByAddress("rue des Cookies");
        s1.getStock().setIngredients(map1);

        Set<IngredientQuantity> map2 = new HashSet<>();

        map2.add(new IngredientQuantity (new Topping("Chocolala"),2));
        map2.add(new IngredientQuantity (new Dough("Popopooo"),2));
        map2.add(new IngredientQuantity (new Flavour("Tornado"),2));

        Store s2 = cod.getStoreByAddress("rue des Colles");
        s2.getStock().setIngredients(map2);

        ShoppingCart sc1 = new ShoppingCart();
        List<IngredientQuantity> topping = new ArrayList<>();
        topping.add(new IngredientQuantity (new Topping("Chocolala"),3));
        Cookie c = new Cookie("nano",new IngredientQuantity (new Dough("pate"),1),new IngredientQuantity (new Flavour("poe"),1), new Toppings(topping), new Cooking("A feu doux"));
        sc1.addCookie( new CookieQty(c,12));

        Order o = new Order(sc1, s1, new Guest(), LocalDateTime.now());
        List<Order> orders = new ArrayList<>();
        orders.add(o);
        assertEquals(0, s1.handleOrders(orders).size());
        List<Order> orderNonTraite = s2.handleOrders(orders);
        assertEquals(1, orderNonTraite.size());
        assertTrue(s1.getOrdersToPrepare().containsAll(orderNonTraite));
    }

}