package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import fr.unice.polytech.cookieFactory.users.CC;
import fr.unice.polytech.cookieFactory.users.Customer;
import io.cucumber.java8.En;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SaveCCStepdefs implements En {
    Customer customer;
    Order order;
    COD cod = new COD();
    public SaveCCStepdefs() {
        Given("^\"([^\"]*)\", a user logged into his account which will pay an order$", (String firstname) -> {
            cod.addStore("8 Mar Av",0.1);
            String customerId = cod.addCustomer("", firstname,"","", false);
            customer = cod.getCustomerById(customerId);
            cod.newOrder(customer,new ShoppingCart(), cod.getStoreManagement().getStoreByAddress("8 Mar Av"),LocalDateTime.now());  //fake order to use the function from cod "pay"
            order = cod.getOrderWaiting(customer);
        });
        When("^he provides his credit card no (\\d+) expire on (\\d+)/(\\d+) , the CVV (\\d+) and the name on the credit card \"([^\"]*)\" to pay his order and would save the CC$", (Long no, Integer month, Integer year, Integer cvv, String name) -> {
            CC cc = new CC(name, no, month, year, cvv, true);
            cod.pay(cc, order);
        });
        Then("^the payment is proceeded and the credit card is saved$", () -> {
            assertTrue(customer.hasCreditCardSaved());
        });
    }
}
