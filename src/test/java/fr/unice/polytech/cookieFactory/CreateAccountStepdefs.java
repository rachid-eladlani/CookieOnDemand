package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.User;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountStepdefs implements En{
    COD cod = new COD();
    Customer customer;
    ShoppingCart shoppingCart;

    public CreateAccountStepdefs() {
        Given("a customer of first name {string}, name {string}, mail {string} and address {string}", (String firstName, String name, String mail, String address) -> {
            customer = new User(name, firstName, mail, address);
        });

        When("{string} create an account", (String firstName) -> {
            cod.addCustomer(customer.getName(),customer.getFirstName(),customer.getEmail(),customer.getAddress(), false);
        });

        Then("Account informations : first name : {string}, name : {string}, mail : {string}", (String _fname, String _name, String _mail) -> {
            assertEquals(_fname,customer.getFirstName());
            assertEquals(_name,customer.getName());
            assertEquals(_mail,customer.getEmail());
        });

        And("{string} can join Loyalty Program", (String name) -> {
            customer.subscribeLoyaltyProgram();
            assertTrue(customer.isMemberLP());
        });

        When("{string} have an account", (String firstName) -> {
            assertNotSame("", customer.getFirstName());
            assertNotSame("", customer.getName());
            assertNotSame("", customer.getEmail());
        });

        Then("{string} join Loyalty Program", (String firstName) -> {
            customer.subscribeLoyaltyProgram();
        });
        And("fill the shopping cart with {int} cookies {string} and {int} cookies {string}\n" , (Integer nbCookie1, String nameCookie1, Integer nbCookie2, String nameCookie2)->{
                Cookie c1 = new Cookie();
                c1.setName(nameCookie1);
                customer.shoppingCart.addCookie( new CookieQty(c1, nbCookie1));
                shoppingCart.addCookie(new CookieQty(c1, nbCookie1));

                Cookie c2 = new Cookie();
                c2.setName(nameCookie2);
                customer.shoppingCart.addCookie(new CookieQty(c2, nbCookie2));
                shoppingCart.addCookie(new CookieQty(c2, nbCookie2));
        });
    }


}