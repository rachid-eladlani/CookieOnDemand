package fr.unice.polytech.cookieFactory.store;

import fr.unice.polytech.cookieFactory.utils.IllogicalTimeException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Random;

//Responsable du store
public class Manager {
    private String idManager;
    private String name;
    private Store store;

    public Manager(String name, Store store) {
        Random rd = new Random();
        this.idManager =  Integer.toString(rd.nextInt());
        this.name = name;
        this.store = store;
    }

    public Manager(String name){
        Random rd = new Random();
        this.idManager =  Integer.toString(rd.nextInt());
        this.name = name;
    }

    public void changeOpeningTime(DayOfWeek day, LocalTime time) throws IllogicalTimeException {
        if(store.getClosingTimes().containsKey(day) && time.isAfter(store.getClosingTime(day))){
            throw new IllogicalTimeException("Opening time after closing time of the store");
        }
        store.setOpeningTime(day, time);
    }

    public void changeClosingTime(DayOfWeek day, LocalTime time) throws IllogicalTimeException {
        if(store.getOpeningTimes().containsKey(day) && time.isBefore(store.getOpeningTime(day))){
            throw new IllogicalTimeException("Closing time before opening time of the store");
        }
        store.setClosingTime(day, time);
    }

    public String getIdManager() {
        return idManager;
    }

//    public void setIdManager(String idManager) {
//        this.idManager = idManager;
//    }

    public String getName() {
        return name;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    @Override
    public String toString() {
        return "Manager{" +
                "idManager=" + idManager +
                ", name='" + name + '\'' +
                ", store=" + store +
                '}';
    }
}
