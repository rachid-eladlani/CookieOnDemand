package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.ingredient.*;
import fr.unice.polytech.cookieFactory.store.Stock;
import fr.unice.polytech.cookieFactory.store.Store;
import io.cucumber.java8.En;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockSupplyStepdefs implements En {
    COD cod = new COD();
    Stock stock;
    Store store;
    List<Store> stores;
    IngredientQuantity ingredientQuantity;
    Set<IngredientQuantity> baseStock;

    public StockSupplyStepdefs(){
        Given("^a store located at \"([^\"]*)\"$",
                (String address) ->
                {
                    cod.addStore(address, 22);
                });
        And("^an employee who has access to the stock of the store which is empty$", () -> {
           //stock is empty
            stores = cod.getStoreManagement().getStores();
            stock = stores.get(0).getStock();
        });
        When("^the employee add (\\d+) \"([^\"]*)\" flavour and (\\d+) \"([^\"]*)\" topping that he received$", (Integer qty1, String ingredientName1, Integer qty2, String ingredientName2) -> {
            stock.addIngredients(new IngredientQuantity(new Flavour(ingredientName1), qty1));
            stock.addIngredients(new IngredientQuantity(new Topping(ingredientName2), qty2));
        });
        Then("^the stock balance is now (\\d+) of \"([^\"]*)\" flavour and (\\d+) of \"([^\"]*)\" topping$", (Integer qty1, String ingredientName1, Integer qty2, String ingredientName2) -> {
            assertEquals(qty1, stock.getIngredientQuantity(new Flavour(ingredientName1)));
            assertEquals(qty2, stock.getIngredientQuantity(new Topping(ingredientName2)));
            assertEquals(qty1 + qty2, stock.getAllIngredientsQuantity());
        });
        //scenario : missing ingredient
        When("^the employee wants to add (\\d+) \"([^\"]*)\" flavour, he realises that (\\d+) percent of those ingredient are missing$", (Integer qty1, String ingredientName1, Integer percentageMissing) -> {
            Set<IngredientQuantity> ig = new HashSet<>();
            ig.add(new IngredientQuantity(new Flavour(ingredientName1), (int) (qty1 - (qty1 * percentageMissing / 100))));
            stock.setIngredients(ig);
        });
        Then("^the employee declare that they are missing (\\d+) of \"([^\"]*)\" flavour so they are lost$", (Integer qtyMissing, String ingredientName1) -> {
            int theoricalStock = stock.getIngredientQuantity(new Flavour(ingredientName1)) + qtyMissing;
            assertEquals(theoricalStock - qtyMissing, stock.getIngredientQuantity(new Flavour(ingredientName1)));
            assertEquals(80, stock.getAllIngredientsQuantity());
        });
    }
}
