package fr.unice.polytech.cookieFactory.order;

import java.util.Objects;

public class CookieQty implements Comparable<CookieQty> {
    private final Cookie cookie;
    private int quantity;

    public CookieQty(Cookie cookie, int quantity){
        this.cookie = cookie;
        this.quantity = quantity;
    }
    public CookieQty(Cookie cookie){
        this.cookie = cookie;
    }

    public void addQuantity(int quantity){
        this.quantity+= quantity ;
    }
    public void addQuantity(CookieQty cookieQty){
        if(cookie.equals(cookieQty.getCookie()))
            this.quantity+= cookieQty.getQuantity() ;
    }

    public void reduceQuantity(int quantity){
        if(this.enoughQuantity(quantity))
            this.quantity-= quantity ;
    }
    public void reduceQuantity(CookieQty cookieQty){
        if(this.enoughQuantity(cookieQty.getQuantity())) {
            this.quantity -= cookieQty.getQuantity();
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public boolean enoughQuantity(int quantity){
        return this.quantity >= quantity;
    }

    public boolean sameCookie(CookieQty cookieQty){
        return cookieQty.getCookie().equals(cookie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookieQty cookieQty = (CookieQty) o;
        return cookie.equals(cookieQty.cookie) && quantity == cookieQty.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie, quantity);
    }

    @Override
    public int compareTo(CookieQty o) {
        return o.getQuantity() - this.quantity;
    }

}
