package fr.unice.polytech.cookieFactory;

import fr.unice.polytech.cookieFactory.store.Manager;
import fr.unice.polytech.cookieFactory.store.Store;
import fr.unice.polytech.cookieFactory.users.Customer;
import fr.unice.polytech.cookieFactory.users.User;
import io.cucumber.java8.En;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static java.util.Locale.forLanguageTag;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeStoreHoursStepdefs implements En {
    Manager manager;
    Customer customer;
    Store store;

    LocalTime _8_AM;
    LocalTime _10_AM;
    DayOfWeek _monday;
    LocalTime _18_35_PM;
    LocalTime _17_45_PM;

    public ChangeStoreHoursStepdefs(){
        /* Background */
        Given("^a manager of name \"([^\"]*)\"$",
                (String name) ->
                {
                    manager = new Manager(name);
                });
        And("^a store named \"([^\"]*)\" at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (String storeName, String storeAddress, Double taxes) ->
                {
                    store = new Store(storeName, storeAddress, taxes);
                });
        And("^a customer of first name \"([^\"]*)\" and name \"([^\"]*)\" and mail \"([^\"]*)\" and address \"([^\"]*)\"$",
                (String firstName, String name, String mail, String address) ->
                {
                    customer = new User(firstName, name, mail, address);
                });

        /* adding opening hours of a store */

        When("^\"([^\"]*)\" wants to define the opening hours at (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (String managerName, Integer hour, Integer minutes, String openingDay, String storeName, String storeAdress, Double taxes)->{
                    _8_AM = LocalTime.of(hour,minutes);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", forLanguageTag("en"));
                    TemporalAccessor accessor = formatter.parse(openingDay);
                    _monday = DayOfWeek.from(accessor);
                    //System.out.println(DayOfWeek.from(accessor));

                    manager.setStore(store);
                    store.setOpeningTime(_monday, _8_AM);
                });
        Then("^The opening hours of the store named \"([^\"]*)\" located \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes is define to (\\d+) hours and (\\d+) minutes on \"([^\"]*)\"$",
                (String storeName, String storeAdress, Double taxes, Integer hours, Integer minutes, String day) -> {
                    assertEquals(store, manager.getStore());

                    /*check opening hours after the manager changed it, customer will see the new ones*/
                    LocalTime trueTime = LocalTime.of(hours,minutes); // 8 o'clock
                    DayOfWeek trueDay = DayOfWeek.of(1); //monday

                    assertEquals(store.getOpeningTime(trueDay), trueTime);
                });

        /* adding closing hours of a store */

        When("^\"([^\"]*)\" wants to define the closing hours at (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (String managerName, Integer hour, Integer minutes, String openingDay, String storeName, String storeAdress, Double taxes)->{
                    _18_35_PM = LocalTime.of(hour,minutes);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", forLanguageTag("en"));
                    TemporalAccessor accessor = formatter.parse(openingDay);
                    _monday = DayOfWeek.from(accessor);
                    //System.out.println(DayOfWeek.from(accessor));

                    manager.setStore(store);
                    store.setClosingTime(_monday, _18_35_PM);
                });
        Then("^The closing hours of the store named \"([^\"]*)\" located \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes is define to (\\d+) hours and (\\d+) minutes on \"([^\"]*)\"$",
                (String storeName, String storeAdress, Double taxes, Integer hours, Integer minutes, String day) -> {
                    assertEquals(store, manager.getStore());

                    /*check closing hours after the manager changed it, customer will see the new ones*/
                    LocalTime trueTime = LocalTime.of(hours,minutes); // 8 o'clock
                    DayOfWeek trueDay = DayOfWeek.of(1); //monday

                    assertEquals(store.getClosingTime(trueDay), trueTime);
                });

        /* Scenario changing opening hours of a store --> update */

        When("^\"([^\"]*)\" wants to change opening hours which was (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" and put (\\d+) hours and (\\d+) minutes on a same day, of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (String managerName, Integer currentHours, Integer currentMinutes, String openingDay, Integer newHours, Integer newMinutes, String storeName, String storeAdress, Double taxes)->{
                    _8_AM = LocalTime.of(currentHours, currentMinutes);
                    _10_AM = LocalTime.of(newHours, newMinutes);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", forLanguageTag("en"));
                    TemporalAccessor accessor = formatter.parse(openingDay);
                    _monday = DayOfWeek.from(accessor);
                    //System.out.println(DayOfWeek.from(accessor));

                    manager.setStore(store);
                    store.setOpeningTime(_monday, _10_AM);
                });
        Then("^The opening hours are now at (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (Integer hours, Integer minutes, String day, String storeName, String storeAdress, Double taxes) -> {
                    assertEquals(store, manager.getStore());

                    /*check opening
                     hours after the manager changed it, customer will see the new ones*/
                    LocalTime trueTime = LocalTime.of(hours, minutes); // 8 o'clock
                    DayOfWeek trueDay = DayOfWeek.of(1); //monday

                    assertEquals(store.getOpeningTime(trueDay), trueTime);
                });

        /* Scenario changing closing hours of a store --> update */

        When("^\"([^\"]*)\" wants to change closing hours which was (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" and put (\\d+) hours and (\\d+) minutes on a same day, of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (String managerName, Integer currentHours, Integer currentMinutes, String openingDay, Integer newHours, Integer newMinutes, String storeName, String storeAdress, Double taxes)->{
                    _18_35_PM = LocalTime.of(currentHours, currentMinutes);
                    _17_45_PM = LocalTime.of(newHours, newMinutes);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", forLanguageTag("en"));
                    TemporalAccessor accessor = formatter.parse(openingDay);
                    _monday = DayOfWeek.from(accessor);
                    //System.out.println(DayOfWeek.from(accessor));

                    manager.setStore(store);
                    store.setClosingTime(_monday, _17_45_PM);
                });
        Then("^The closing hours are now at (\\d+) hours and (\\d+) minutes on \"([^\"]*)\" of his store named \"([^\"]*)\" located at \"([^\"]*)\" with (\\d+\\.\\d+) percent taxes$",
                (Integer hours, Integer minutes, String day, String storeName, String storeAdress, Double taxes) -> {
                    assertEquals(store, manager.getStore());

                    /*check closing hours after the manager changed it, customer will see the new ones*/
                    LocalTime trueTime = LocalTime.of(hours, minutes); // 8 o'clock
                    DayOfWeek trueDay = DayOfWeek.of(1); //monday

                    assertEquals(store.getClosingTime(trueDay), trueTime);
                });
    }
}
