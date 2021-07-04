package fr.unice.polytech.cookieFactory.statistic;

import fr.unice.polytech.cookieFactory.COD;
import fr.unice.polytech.cookieFactory.order.Cookie;
import fr.unice.polytech.cookieFactory.order.CookieQty;
import fr.unice.polytech.cookieFactory.order.Order;
import fr.unice.polytech.cookieFactory.order.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

class StatisticTest {
    private Statistic stat;
    private int RANGE_1 = 1;
    private Map<DayOfWeek, LocalTime> openingTime;
    private Map<DayOfWeek, LocalTime> closingTime;
    private final Order order1 = mock(Order.class);
    private final Order order2 = mock(Order.class);
    private final List<Order> pastOrder = new ArrayList<>();
    private final LocalDateTime monday = mock(LocalDateTime.class);
    private final LocalDateTime friday = mock(LocalDateTime.class);
    private final ShoppingCart shoppingCart1 = mock(ShoppingCart.class);
    private final ShoppingCart shoppingCart2 = mock(ShoppingCart.class);
    private final List<CookieQty> cookiesOrder1 = new ArrayList<>();
    private final List<CookieQty> cookiesOrder2 = new ArrayList<>();
    private final List<CookieQty> expectedCookies = new ArrayList<>();
    private final COD cod = new COD();
    LocalTime _9_AM = LocalTime.of(9,0);
    LocalTime _10_AM = LocalTime.of(10,0);
    LocalTime _1_PM = LocalTime.of(13,0);
    LocalTime _6_PM = LocalTime.of(18,0);

    @BeforeEach
    void setUp() {
        initOpening();
        initClosing();
        stat = new Statistic(RANGE_1, openingTime, closingTime);
        pastOrder.addAll(Arrays.asList(order1,order2));
        when(monday.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(monday.toLocalTime()).thenReturn(_10_AM);
        when(friday.getDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);
        when(friday.toLocalTime()).thenReturn(_1_PM);
        cookiesOrder1.add(new CookieQty(cod.getCookieByName("SooChocolalala"), 10));
        cookiesOrder2.add(new CookieQty(cod.getCookieByName("White Chocolate"), 15));
        expectedCookies.add(new CookieQty(cod.getCookieByName("SooChocolalala"), 10));
        expectedCookies.add(new CookieQty(cod.getCookieByName("White Chocolate"), 15));

        when(order1.getShoppingCart()).thenReturn(shoppingCart1);
        when(order2.getShoppingCart()).thenReturn(shoppingCart2);
        when(shoppingCart1.getCookiesOrdered()).thenReturn(cookiesOrder1);
        when(shoppingCart2.getCookiesOrdered()).thenReturn(cookiesOrder2);
        when(monday.getMonth()).thenReturn(LocalDateTime.now().getMonth());
        when(monday.getYear()).thenReturn(LocalDateTime.now().getYear());
        when(friday.getMonth()).thenReturn(LocalDateTime.now().getMonth());
        when(friday.getYear()).thenReturn(LocalDateTime.now().getYear());

    }

    @Test
    void init() {
        assertTrue(stat.getDaysStat().isEmpty());
        stat.init();
        assertEquals(7, stat.getDaysStat().size());
        assertEquals(DayOfWeek.MONDAY, stat.getDayStat(DayOfWeek.MONDAY).getDay());
    }

    @Test
    void sortOrders() {
        stat.init();
        LocalTime time_10am = LocalTime.of(10,0);
        LocalTime time_1pm = LocalTime.of(13,0);
        when(order1.getDateOfPickup()).thenReturn(monday);
        when(order2.getDateOfPickup()).thenReturn(friday);
        assertEquals(0, stat.getDayStat(DayOfWeek.MONDAY).getPeriodByHour(time_10am).getOrderQty());
        assertEquals(0, stat.getDayStat(DayOfWeek.FRIDAY).getPeriodByHour(time_1pm).getOrderQty());
        stat.sortOrders(pastOrder);
        assertEquals(1, stat.getDayStat(DayOfWeek.MONDAY).getPeriodByHour(time_10am).getOrderQty());
        assertEquals(1, stat.getDayStat(DayOfWeek.FRIDAY).getPeriodByHour(time_1pm).getOrderQty());

    }

    @Test
    void getDayStat() {
        stat.init();
        assertEquals(DayOfWeek.MONDAY, stat.getDayStat(DayOfWeek.MONDAY).getDay());
    }

    @Test
    void getCookieOfTheMonth(){
        stat.init();
        when(order1.getDateOfPickup()).thenReturn(monday);
        when(order2.getDateOfPickup()).thenReturn(friday);
        stat.sortOrders(pastOrder);
        stat = new Statistic(RANGE_1, openingTime, closingTime, stat);
        Cookie c = stat.getCookieOfTheMonth();
        assertEquals(cod.getCookieByName("White Chocolate"), c);
    }

    @Test
    void getMonthOrderedCookies(){
        stat.init();
        when(order1.getDateOfPickup()).thenReturn(monday);
        when(order2.getDateOfPickup()).thenReturn(friday);
        stat.sortOrders(pastOrder);
        assertEquals(expectedCookies.stream().sorted().collect(Collectors.toList()), stat.getMonthStat().getMonthOrderedCookies());
    }

    @Test
    void sameMonthOrders() {
        stat.init();
        when(order1.getDateOfPickup()).thenReturn(monday);
        when(order2.getDateOfPickup()).thenReturn(friday);
        when(monday.getMonth()).thenReturn(LocalDateTime.now().getMonth());
        when(monday.getYear()).thenReturn(LocalDateTime.now().getYear());
        when(friday.getMonth()).thenReturn(LocalDateTime.now().getMonth().minus(1));
        when(friday.getYear()).thenReturn(LocalDateTime.now().getYear());
        assertTrue(stat.getMonthStat().getMonthOrderedCookies().isEmpty());
        stat.sortOrders(pastOrder);
        assertEquals(cookiesOrder1, stat.getMonthStat().getMonthOrderedCookies());
    }


        private void initClosing() {
        closingTime = new HashMap<>();
        for(DayOfWeek day : DayOfWeek.values()){
            closingTime.put(day, _6_PM);
        }
    }

    private void initOpening() {
        openingTime = new HashMap<>();
        for(DayOfWeek day : DayOfWeek.values()){
            openingTime.put(day, _9_AM);
        }
    }


}