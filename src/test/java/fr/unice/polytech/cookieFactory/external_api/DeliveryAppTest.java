package fr.unice.polytech.cookieFactory.external_api;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeliveryAppTest {

    private DeliveryApp deliveryApp;
    private Delivery delivery;
    private Order order;
    private Customer customer;
    private ShoppingCart cart;
    private Store store;
    private List<Cookie> cookies = new ArrayList<>();

    @BeforeEach
    void setup(){
        customer = new User("Doe", "John", "j@free.fr", "18 av NYC.");
        store = new Store("6 bd NYC.", 15.5);
        cart = new ShoppingCart();
        Cookie chocolalala = new Cookie();
        Cookie soooChocolate = new Cookie();
        chocolalala.setName("Chocolalala");
        soooChocolate.setName("Sooo Chocolate");
        cookies.add(soooChocolate);
        cookies.add(chocolalala);
        cookies.forEach(c -> cart.addCookie(new CookieQty(c, 2)));
        order = new Order(cart, store, customer, LocalDateTime.now());

        delivery = new Delivery(order);
        deliveryApp = new DeliveryApp();
    }

    @Test
    @DisplayName("Extra cost with Marcel Eat delivery.")
    void estimateOrderWithMarcelEatDeliveryTest() {
//        assertEquals(5.5, deliveryApp.estimateOrderWithMarcelEatDelivery(delivery));
        Order order2 = mock(Order.class);
        Delivery da = mock(Delivery.class);
        when(order2.getFinalAmount()).thenReturn((double) 80);
        when(da.getOrder()).thenReturn(order2);
 //       assertEquals(40, deliveryApp.estimateOrderWithMarcelEatDelivery(da));
    }

    @Test
    @DisplayName("Final price with last minute delivery.")
    void lastMinuteDeliveryTest() {
        deliveryApp.lastMinuteDelivery(delivery);
//        assertEquals(16.5, delivery.getOrder().getFinalAmount());
    }
}