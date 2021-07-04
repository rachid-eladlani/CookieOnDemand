package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DayStatTest {
    DayStat ds;
    Order order1 = mock(Order.class);
    LocalDateTime ldc = mock(LocalDateTime.class);
    LocalTime _10_AM= LocalTime.of(10,0);
    LocalTime _11_AM = LocalTime.of(11,0);
    LocalTime _12_PM = LocalTime.of(12,0);
    LocalTime _1_PM = LocalTime.of(13,0);
    LocalTime _2_PM = LocalTime.of(14,0);
    LocalTime _3_PM = LocalTime.of(15,0);
    LocalTime _4_PM = LocalTime.of(16,0);
    LocalTime _5_PM = LocalTime.of(17,0);
    LocalTime _6_PM = LocalTime.of(18,0);
    @BeforeEach
    void setUp() {
        ds = new DayStat(DayOfWeek.MONDAY,_10_AM,_6_PM);
        when(ldc.toLocalTime()).thenReturn(_1_PM);
        when(order1.getDateOfPickup()).thenReturn(ldc);
    }

    @Test
    void createPeriods() {
        assertTrue(ds.getPeriodStatList().isEmpty());
        ds.createPeriods(1); //we divide the 8 hours with 8 periods (we have 8 PeriodStat of 1h in the list)
        assertEquals(8, ds.getPeriodStatList().size());
        ds.createPeriods(2); //we divide the 8 hours with 4 periods (we have 4 PeriodStat of 2h, in the list)
        assertEquals(4, ds.getPeriodStatList().size());
        ds.createPeriods(3); //we divide the 8 hours with 3 periods -> the last period cover only 2 hours (we have 3 PeriodStat of 3h in the list)
        assertEquals(3, ds.getPeriodStatList().size());
    }

    @Test
    void getPeriodByHour() {
        ds.createPeriods(1);
        assertEquals(ds.getPeriodStatList().get(0), ds.getPeriodByHour(_10_AM));
        assertEquals(ds.getPeriodStatList().get(1), ds.getPeriodByHour(_11_AM));
        assertEquals(ds.getPeriodStatList().get(2), ds.getPeriodByHour(_12_PM));
        ds.createPeriods(3);
        assertEquals(ds.getPeriodStatList().get(0), ds.getPeriodByHour(_10_AM));
        assertEquals(ds.getPeriodStatList().get(0), ds.getPeriodByHour(_11_AM));
        assertEquals(ds.getPeriodStatList().get(0), ds.getPeriodByHour(_12_PM));
        assertEquals(ds.getPeriodStatList().get(1), ds.getPeriodByHour(_1_PM));
        assertEquals(ds.getPeriodStatList().get(1), ds.getPeriodByHour(_2_PM));
        assertEquals(ds.getPeriodStatList().get(1), ds.getPeriodByHour(_3_PM));
        assertEquals(ds.getPeriodStatList().get(2), ds.getPeriodByHour(_4_PM));
        assertEquals(ds.getPeriodStatList().get(2), ds.getPeriodByHour(_5_PM));
        assertNotEquals(ds.getPeriodStatList().get(2), ds.getPeriodByHour(_6_PM));
    }

    @Test
    void getPeriodByOrder() {
        ds.createPeriods(1);
        assertEquals(ds.getPeriodStatList().get(3),ds.getPeriodByOrder(order1));
    }
}