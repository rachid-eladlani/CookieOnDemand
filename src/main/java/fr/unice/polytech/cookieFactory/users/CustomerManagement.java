package fr.unice.polytech.cookieFactory.users;

import fr.unice.polytech.cookieFactory.order.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagement {
    private final List<Customer> customers = new ArrayList<>();


    public String addCustomer(String name, String firstName, String email, String address, boolean subscribeLoyaltyProgram) {
        Customer c = new User(name, firstName, email, address);
        if(subscribeLoyaltyProgram)
            c.subscribeLoyaltyProgram();
        customers.add(c);
        return c.getId();
    }

    public void removeCustomer(String customerId) {
        customers.removeIf(c -> customerId.equals(c.getId()));
    }

    public void saveCC(CC creditCard, Customer customer) {
        customer.saveCreditCard(creditCard);
    }

    public Customer getCustomerById(String customerId) {
        return customers.stream().filter(c->customerId.equals(c.getId())).findFirst().orElse(null);
    }

    public ShoppingCart getShoppingCart(Customer customer) {
       return customer.getShoppingCart();
    }
}
