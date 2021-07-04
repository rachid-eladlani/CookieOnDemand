package fr.unice.polytech.cookieFactory.users;

import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class Customer {
    private String id;
    private String name;
    private String firstName;
    private String mail;
    private String address;
    public ShoppingCart shoppingCart;

    public Customer() {
        Random rd = new Random();
        id =  Long.toString(rd.nextLong());
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Customer(String name, String firstName, String mail, String address) {
        Random rd = new Random();
        id =  Integer.toString(rd.nextInt());
        this.name = name;
        this.firstName = firstName;
        this.mail = mail;
        shoppingCart = new ShoppingCart();
        this.address = address;
  }


    public String getId() {
        return id;
    }

    abstract public boolean hasLoyaltyDiscount();

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public abstract boolean isMemberLP();

    public abstract void subscribeLoyaltyProgram();

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isMemberLP() == customer.isMemberLP() &&
                name.equals(customer.name) &&
                firstName.equals(customer.firstName) &&
                mail.equals(customer.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstName, mail);
    }

    public abstract double applyDiscount(double price);

    public abstract void saveCreditCard(CC cc);

    public abstract boolean hasCreditCardSaved();

    public abstract void memberLp(Order order);

}
