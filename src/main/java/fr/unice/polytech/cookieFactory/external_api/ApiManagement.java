package fr.unice.polytech.cookieFactory.external_api;

import fr.unice.polytech.cookieFactory.users.CC;
import fr.unice.polytech.cookieFactory.users.Email;

public class ApiManagement {
    EmailApp emailApp;
    PaymentApp paymentApp;

    public ApiManagement(){
        emailApp = new EmailApp();
        paymentApp = new PaymentApp();
    }

    public void sendEmail(Email confirmationOrder) {
        emailApp.sendEmail(confirmationOrder);
    }

    public boolean pay(CC creditCard, double amount) {
       return paymentApp.pay(creditCard, amount);
    }
}
