package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.statistic.Statistic;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class OrderCookieStepdefs implements En {
    COD cod = new COD();
    Store store;
    Customer customer;
    Cookie cookie;
    ShoppingCart shoppingCart;
    Statistic stat = mock (Statistic.class);
	Cookie BestofCookieL = new Cookie();
	Cookie BestofCookieN = new Cookie();

    public OrderCookieStepdefs(){
        Given("a customer of firstname {string} and of name {string} and address {string}",
                (String name, String firstName, String address) ->
                {
                    String customerId = cod.addCustomer(name, firstName, "ok@gmail.com",address, false);
                    customer = cod.getCustomerById(customerId);
                    store = new Store("ardeche", 0.2);
                    shoppingCart = new ShoppingCart();
                });
        And("a cookie named {string}", (String nameCookie) -> {
            cookie = new Cookie();
            cookie.setName(nameCookie);
            cod.addCookie(cookie);
            shoppingCart.addCookie( new CookieQty(cod.getCookieByName(nameCookie),1) );
        });


        When("{string} adds {int} cookies {string} and {int} cookies {string} to the shopping cart", (String firstName, Integer nbCookie1 ,String nameCookie1, Integer nbCookie2, String nameCookie2)->{

            Cookie c1 = new Cookie();
            Cookie c2 = new Cookie();
            c1.setName(nameCookie1);
            c2.setName(nameCookie2);
            cod.addCookie(c1);
            cod.addCookie(c2);
            customer.getShoppingCart().addCookie(new CookieQty(c1, nbCookie1));
            customer.getShoppingCart().addCookie(new CookieQty(c2, nbCookie2));
        });
        Then("There is {int} cookies in his shopping cart", (Integer nbCookie1) -> {
            assertEquals(nbCookie1, customer.getShoppingCart().getSumCookiesOrdered());
        });
        
        Then("There is no cookie {string} in his shopping cart", (String cookieName) -> {
            assertEquals(0, customer.getShoppingCart().getSumCookiesOrdered());
        });
        
        When("{string} adds {int} cookies {string} to the shopping cart", (String firstName, Integer nbCookie1 ,String nameCookie1)->{
            Cookie c1 = new Cookie();
            c1.setName(nameCookie1);
            cod.addCookie(c1);
            customer.getShoppingCart().addCookie(new CookieQty(c1, nbCookie1));
        });
        And("{string} removes {int} cookies {string} from the shopping cart", (String firstName, Integer nbCookie1 ,String nameCookie1)->{
        	Cookie c1 = new Cookie();
        	c1.setName(nameCookie1);
            cod.addCookie(c1);
            customer.getShoppingCart().removeCookie(new CookieQty(c1, nbCookie1));
        });
    }
}
