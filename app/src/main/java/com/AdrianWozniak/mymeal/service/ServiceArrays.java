package com.AdrianWozniak.mymeal.service;

import com.AdrianWozniak.mymeal.model.FoodItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceArrays {

    /**
     * Method filter list and return only values from given date
      * @param date
     * @param totalConsumed
     * @return
     */
    private static List<FoodItem> getFoodListInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        List<FoodItem> consumedInGivenDate = new ArrayList<>();
        Object[] keys = totalConsumed.keySet().toArray();
        for (Object key : keys) {
            if (key.toString().startsWith(date.substring(0, 11))) {
                if(key.toString().endsWith(date.substring(date.length()-4))){
                    consumedInGivenDate.addAll(totalConsumed.get(key));
                }
            }
        }
        return consumedInGivenDate;
    }


    /**
     * Function to sum consumed calories in given date
     *
     * @param date          string from data object
     * @param totalConsumed Map from database
     * @return
     */
    public static double getSumCaloriesInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed) {
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getCalories()).sum();
    }


    /**
     * Function to sum consumed protein in given date
     *
     * @param date string from data object
     * @param totalConsumed Map from database
     * @return
     */
    public static double getSumConsumedProteinsInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed) {
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getProtein()).sum();
    }

    /**
     * Function to sum consumed fat in given date
     *
     * @param date string from data object
     * @param totalConsumed Map from database
     * @return
     */
    public static double getSumConsumedFatInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed) {
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getTotal_fat()).sum();
    }


    /**
     * Function to sum consumed carb in given date
     *
     * @param date string from data object
     * @param totalConsumed Map from database
     * @return
     */
    public static double getSumConsumedCarbInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed) {
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getTotal_carbohydrate()).sum();
    }

    /**
     * Function to sum consumed Saturated Fat
     * @param date
     * @param totalConsumed
     * @return
     */
    public static double getSumSaturatedFatInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getSaturated_fat()).sum();
    }

    /**
     * Function to sum consumed Sugars
     * @param date
     * @param totalConsumed
     * @return
     */
    public static double getSumSugarsInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getSugars()).sum();
    }

    /**
     * Function to sum consumed dietary fiber
     * @param date
     * @param totalConsumed
     * @return
     */
    public static double getSumDietaryFiberInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getDietary_fiber()).sum();
    }

    /**
     * Function to sum potassium
     * @param date
     * @param totalConsumed
     * @return
     */
    public static double getSumPotassiumInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getPotassium()).sum();
    }

    /**
     * Function to sum sodium
     * @return
     */
    public static double getSumSodiumInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed) {
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getSodium()).sum();

    }

    /**
     * Function to sum cholesterol
     * @param date
     * @param totalConsumed
     * @return
     */
    public static double getSumCholesterolInGivenDate(String date, Map<String, List<FoodItem>> totalConsumed){
        return getFoodListInGivenDate(date,totalConsumed).stream().mapToDouble(i -> i.getCholesterol()).sum();
    }

    /**
     * Function gives last added index
     *
     * @param map
     * @return value of last index
     */
    public static double sortAndGetLastAddedIndex(Map<String, Double> map) {

        List<Date> dataList = new ArrayList<>();
        map.keySet().forEach(s -> dataList.add(new Date(s)));
        Comparator<Date> dateComparator = (o1, o2) -> o1.compareTo(o2);
        dataList.sort(dateComparator);
        Date date = dataList.get(dataList.size() - 1);
        return map.get(date.toString());
    }

}
