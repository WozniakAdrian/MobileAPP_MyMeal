package com.AdrianWozniak.mymeal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {
    private String uid;
    private String email;
    private Boolean isAccountDataCompleted;
    private String city;
    private Map<String, Double> dailyDemandCalories;
    private Map<String, List<FoodItem>> consumedCalories;
    private String displayName;
    private Map<String, Double> weight;


    public UserData() {
    }

    public UserData(String uid, String email, Boolean isAccountDataCompleted, String city, Map<String, Double> dailyDemandCalories, Map<String, List<FoodItem>> consumedCalories, String displayName, Map<String, Double> weight) {
        this.uid = uid;
        this.email = email;
        this.isAccountDataCompleted = isAccountDataCompleted;
        this.city = city;
        this.dailyDemandCalories = dailyDemandCalories;
        this.consumedCalories = consumedCalories;
        this.displayName = displayName;
        this.weight = weight;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserData(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.displayName = "Set Yours name!";
        this.isAccountDataCompleted = false;
        this.city = "Set your city!";
        this.dailyDemandCalories = new HashMap<>();
        this.dailyDemandCalories.put(new Date().toString(), 0d);
        this.consumedCalories = new HashMap<>();
        List<FoodItem> list = new ArrayList<>();
        list.add( new FoodItem("initial", 0, "0", 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, "null"));
        this.consumedCalories.put(new Date().toString(),list);
        this.weight = new HashMap<>();
        this.weight.put(new Date().toString(), 0d);
    }

    public Map<String, Double> getWeight() {
        return weight;
    }

    public void setWeight(Map<String, Double> weight) {
        this.weight = weight;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getAccountDataCompleted() {
        return isAccountDataCompleted;
    }

    public void setAccountDataCompleted(Boolean accountDataCompleted) {
        isAccountDataCompleted = accountDataCompleted;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, Double> getDailyDemandCalories() {
        return dailyDemandCalories;
    }

    public void setDailyDemandCalories(Map<String, Double> dailyDemandCalories) {
        this.dailyDemandCalories = dailyDemandCalories;
    }

    public Map<String, List<FoodItem>> getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(Map<String, List<FoodItem>> consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", isAccountDataCompleted=" + isAccountDataCompleted +
                ", city='" + city + '\'' +
                ", dailyDemandCalories=" + dailyDemandCalories +
                ", consumedCalories=" + consumedCalories +
                '}';
    }
}
