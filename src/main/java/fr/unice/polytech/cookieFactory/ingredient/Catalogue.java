package fr.unice.polytech.cookieFactory.ingredient;

import fr.unice.polytech.cookieFactory.order.Cookie;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Catalogue {
    private List<Cooking> cookings = new ArrayList<>();
    private List<Flavour> flavours = new ArrayList<>();
    private List<Mix> mixes = new ArrayList<>();
    private List<Topping> toppings = new ArrayList<>();
    private List<Dough> doughs = new ArrayList<>();
    private static Set<Cookie> cookies = new HashSet<>();
    private Cookie cookieOfTheMonth;
    public Catalogue(){
        initialize();
    }

    public void initialize(){
        //Cookies
        Cookie sooChocolalala = new Cookie();
        sooChocolalala.setName("SooChocolalala");
        cookies.add(sooChocolalala);

        Cookie whiteChocolate = new Cookie();
        whiteChocolate.setName("White Chocolate");
        cookies.add(whiteChocolate);

        Cookie blackChocolate = new Cookie();
        blackChocolate.setName("Black Chocolate");
        cookies.add(blackChocolate);

        //type de cuisson dispo
        cookings.add(new Cooking("Crunchy"));
        cookings.add(new Cooking("Chewy"));

        //type de pâte dispo
        doughs.add(new Dough("Plain"));
        doughs.add(new Dough("Chocolate"));
        doughs.add(new Dough("Peanut butter"));
        doughs.add(new Dough("Oatmeal"));

        //type de topping dispo
        toppings.add(new Topping("White chocolate"));
        toppings.add(new Topping("Milk chocolate"));
        toppings.add(new Topping("M&M’s"));
        toppings.add(new Topping("Reese’s buttercup"));

        //type de mélange dispo
        mixes.add(new Mix("Mixed"));
        mixes.add(new Mix("Topped"));

        //type de saveur dispo
        flavours.add(new Flavour("Vanilla"));
        flavours.add(new Flavour("Cinnamon"));
        flavours.add(new Flavour("Chili"));
    }

    public void addCooking(Cooking cooking){
        if(!cookings.contains(cooking)){
            cookings.add(cooking);
        }
    }

    public void removeCooking(Cooking cooking){
        cookings.removeIf(c -> c.equals(cooking));
    }

    public void addFlavour(Flavour flavour){
        if(!flavours.contains(flavour)){
            flavours.add(flavour);
        }
    }

    public void removeFlavour(Flavour flavour){
        flavours.removeIf(f -> f.equals(flavour));
    }

    public void addMix(Mix mix){
        if(!mixes.contains(mix)){
            mixes.add(mix);
        }
    }

    public void removeMix(Mix mix){
        mixes.removeIf(m -> m.equals(mix));
    }

    public void addDough(Dough dough){
        if(!doughs.contains(dough)){
            doughs.add(dough);
        }
    }

    public void removeDough(Dough dough){
        doughs.removeIf(d -> d.equals(dough));
    }

    public void addTopping(Topping topping){
        if(!toppings.contains(topping)){
            toppings.add(topping);
        }
    }

    public boolean addCookie(Cookie cookie){
            return cookies.add(cookie);
    }

    public void removeTopping(Topping topping){
        toppings.removeIf(t -> t.equals(topping));
    }

    public List<Cooking> getCookings() {
        return cookings;
    }

    public List<Flavour> getFlavours() {
        return flavours;
    }

    public List<Dough> getDoughs() {
        return doughs;
    }

    public List<Mix> getMixes() {
        return mixes;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public Cookie getCookiesByName(String name){
        return cookies.stream().filter(c->name.equals(c.getName())).findFirst().orElse(null);
    }

    public boolean isInCatalogue(Cookie cookie){
        return cookies.contains(cookie);
    }


    public boolean removeCookie(String cookie) {
        return cookies.removeIf(c->cookie.equals(c.getName()));
    }

    public static Set<Cookie> getCookies() {
        return cookies;
    }

    public void setCookieOfTheMonth(Cookie cookieOfTheMonth) {
        this.cookieOfTheMonth = cookieOfTheMonth;
    }

    public Cookie getCookieOfTheMonth() {
        return cookieOfTheMonth;
    }

    public boolean isCookieOfTheMonth(Cookie cookie) {
        return cookie.equals(cookieOfTheMonth);
    }

    public void removeCookie(List<Cookie> cookieToRemove) {
        cookies.removeAll(cookieToRemove);
    }
}
