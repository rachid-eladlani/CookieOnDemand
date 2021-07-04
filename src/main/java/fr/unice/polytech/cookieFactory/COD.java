package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.external_api.ApiManagement;
import fr.unice.polytech.cookieFactory.order.*;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.store.StoreManagement;
import fr.unice.polytech.cookieFactory.users.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class COD {
    private final ApiManagement apiManagement;
    private final OrderManagement orderManagement;
    private final Catalogue catalogue;
    private final StoreManagement storeManagement;
    private final CustomerManagement customerManagement;

    public COD(){
        customerManagement = new CustomerManagement();
        storeManagement = new StoreManagement();
        apiManagement = new ApiManagement();
        catalogue = new Catalogue();
        orderManagement = new OrderManagement(catalogue);

    }


    public boolean addCookie(Cookie cookie){
        return catalogue.addCookie(cookie);
    }

    public boolean deleteCookie(String cookie){
        return catalogue.removeCookie(cookie);
    }

    public void addStore(String name, String address, double taxes){
        storeManagement.addStore(new Store(name, address, taxes));
    }

    public void addStore(String address, double taxes) {
        storeManagement.addStore(new Store(address, taxes));
    }

    void deleteStore(String address){
        storeManagement.removeStore(address);
    }

    //Action to do every month
    void newMonthInit(){
        storeManagement.changeMonthStat();
    }

    String addCustomer(String name, String firstName, String email, String address, boolean subscribeLoyaltyProgram){
        return customerManagement.addCustomer(name, firstName, email, address, subscribeLoyaltyProgram);

    }

    void deleteCustomer(String customerId){
        customerManagement.removeCustomer(customerId);
    }

    public void addNationalCookieInCatalogue(){
        catalogue.setCookieOfTheMonth(storeManagement.getCookieOfTheMonth());
    }
    public boolean pay(CC creditCard, Order order){
        if(creditCard.saveCC())
            customerManagement.saveCC(creditCard, order.getCustomer());
        if(orderManagement.isOrderUnpaid(order)) { //to prevent duplicate payment
            if (payment(creditCard, order.getAmount())) {
                order.paid();
                dispatchOrder(order);
                apiManagement.sendEmail(orderManagement.confirmationOrder(order));
                return true;
            }
        }
        return false;
    }

    public Cookie getNationalCookieOfTheMonth(){
        return catalogue.getCookieOfTheMonth();
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    private boolean payment(CC creditCard, double amount){ //to the API for payment
        return apiManagement.pay(creditCard, amount);
    }


    StoreManagement getStoreManagement(){
        return storeManagement;
    }

    public Set<Cookie> getCookies() {
        return catalogue.getCookies();
    }

    public Cookie getCookieByName(String cookie) {
        return catalogue.getCookiesByName(cookie);
    }

    public void newOrder(Customer customer, ShoppingCart shoppingCart, Store store, LocalDateTime pickingTime) {
        orderManagement.orderWaitForPayment(shoppingCart, store, customer, pickingTime);
    }

    private void dispatchOrder(Order order) {
        orderManagement.orderPaid(order);
        storeManagement.dispatchOrder(order);
    }

    public ShoppingCart retrieveShoppingCart(Customer customer) {
        return customerManagement.getShoppingCart(customer);
    }

    public List<Cookie> deleteCookiesOrderedLessThan(int totalOrder){
        List<Cookie> cookieToRemove = new ArrayList<>();
        storeManagement.getAllOrderedCookies().stream().filter(c-> c.getQuantity() < totalOrder).forEach(ck -> cookieToRemove.add(ck.getCookie()));
        catalogue.removeCookie(cookieToRemove);
        return cookieToRemove;
    }

    public Order getOrderWaiting(Customer customer) {
        return orderManagement.getWaitingOrder(customer);
    }


	public CustomerManagement getCustomersManagement() {
		return customerManagement;
	}

    public void changeOpeningTime(DayOfWeek day, LocalTime time, Store store){
        storeManagement.changeOpeningTime(day, time, store);
    }

    public void changeClosingTime(DayOfWeek day, LocalTime time, Store store){
        storeManagement.changeClosingTime(day, time, store);
    }

    public Store getStoreByAddress(String address) {
       return storeManagement.getStoreByAddress(address);
    }

    public Customer getCustomerById(String customerId) {
        return customerManagement.getCustomerById(customerId);
    }

    public void addNationalCookieInCatalogue(Cookie cookie) {
        catalogue.setCookieOfTheMonth(cookie);
    }
}
