package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.utils.IllogicalTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    private Manager m1;
    private Store store;
    LocalTime _9_AM = LocalTime.of(9,0);
    LocalTime _6_PM = LocalTime.of(18,0);

    @BeforeEach
    void setUp() {
        store = new Store("6 av NYC.", 16);
        store.setOpeningTime(DayOfWeek.MONDAY, _9_AM);
        store.setClosingTime(DayOfWeek.MONDAY, _6_PM);
        m1 = new Manager("Marcel", store);
    }

    @DisplayName("Change opening time")
    @Test
    void changeOpeningTimeTest() throws IllogicalTimeException {
        m1.changeOpeningTime(DayOfWeek.MONDAY, LocalTime.of(8,35));
        assertEquals(m1.getStore().getOpeningTime(DayOfWeek.MONDAY), LocalTime.of(8,35));
    }

    @DisplayName("Change closing time")
    @Test
    void changeClosingTimeTest() throws IllogicalTimeException {
        m1.changeClosingTime(DayOfWeek.THURSDAY, LocalTime.of(22,0));
        assertEquals(m1.getStore().getClosingTime(DayOfWeek.THURSDAY), LocalTime.of(22,0));
    }

    @DisplayName("set opening time after closing times")
    @Test
    void setOpeningTimeAfterClosingTime() throws IllogicalTimeException {
        // Check exception.
        assertThrows(IllogicalTimeException.class, () ->m1.changeOpeningTime(DayOfWeek.MONDAY, LocalTime.of(20,0)));
    }

    @DisplayName("set closing time before opening times")
    @Test
    void setClosingTimeBeforeOpeningTime(){
        //check exception
        assertThrows(IllogicalTimeException.class, () ->m1.changeClosingTime(DayOfWeek.MONDAY, LocalTime.of(7,0)));

    }
}