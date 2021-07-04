package fr.unice.polytech.cookieFactory.order;

import fr.unice.polytech.cookieFactory.ingredient.Catalogue;
import fr.unice.polytech.cookieFactory.store.Store;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {
    Catalogue catalogue;
    static double COST_PERSONALIZED = 0.25;
    static double DISCOUNT_COOKIES_OF_MONTH = 0.1;

    public PriceCalculator(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public double getCookiePrice(Cookie cookie,Store store){
        double priceCalc = cataloguePrice(cookie);
        if(catalogue.isCookieOfTheMonth(cookie))
            priceCalc = applyDiscount(priceCalc, DISCOUNT_COOKIES_OF_MONTH);

        //check for store != null is needed in case we determine the price from a shopping cart
        if(store != null && store.isCookieOfTheMonth(cookie))
            priceCalc = applyDiscount(priceCalc, DISCOUNT_COOKIES_OF_MONTH);

        return priceCalc;
    }

    private double cataloguePrice(Cookie cookie) {
       double price;
        if(catalogue.isInCatalogue(cookie)) {
            price = cookie.getPrice();
        } else {
            price = applyRate(cookie.getPrice(), COST_PERSONALIZED);
        }
        return price;
    }

    private double applyRate(double price, double rate) {
        return price + price * rate;
    }

    private double applyDiscount(double price, double rate) {
        return price - price * rate;
    }

    public double getOrderPrice(Order order){
        double price = getShoppingCartPrice(order.getShoppingCart(), order.getStore());
        if(order.getCustomer().hasLoyaltyDiscount()){
            price = order.getCustomer().applyDiscount(price);
        }
        return roundPrice(applyRate(price, order.getStore().getTax()), 2);
    }

    public double getShoppingCartPrice(ShoppingCart shoppingCart){
        final double[] price = {0};
        shoppingCart.getCookiesOrdered().forEach((cookieQty) -> {
            price[0] += (getCookiePrice(cookieQty.getCookie(), null) * cookieQty.getQuantity());

        });
        return price[0];
    }

    public double getShoppingCartPrice(ShoppingCart shoppingCart, Store store){
        final double[] price = {0};
        shoppingCart.getCookiesOrdered().forEach((cookieQty) -> {
            price[0] += (getCookiePrice(cookieQty.getCookie(),store) * cookieQty.getQuantity());
        });
        return price[0];
    }

    public void setOrderPrice(Order newOrder) {
        newOrder.determineFinalAmount(getOrderPrice(newOrder));
    }

    public double roundPrice(double price, int decimals) {
        BigDecimal roundedPrice = BigDecimal.valueOf(price).setScale(decimals, RoundingMode.HALF_UP);
        return roundedPrice.doubleValue();
    }

    public double getShoppingCartPriceNoDiscount(ShoppingCart shoppingCart) {
        final double[] price = {0};
        shoppingCart.getCookiesOrdered().forEach((cookieQty) -> {
            price[0] += (cookieQty.getCookie().getPrice() * cookieQty.getQuantity());
        });
        return price[0];
    }
}
