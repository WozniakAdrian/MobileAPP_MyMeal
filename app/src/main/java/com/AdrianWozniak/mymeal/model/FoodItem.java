package com.AdrianWozniak.mymeal.model;

/**
 * Class that represent one item from Nutrionix api
 */
public class FoodItem {
    private String food_name;

    private int serving_quantity;

    private String serving_unit;
    private double serving_weight_grams;
    private double calories;

    private double total_fat;
    private double saturated_fat;
    private double cholesterol;
    private double sodium;
    private double total_carbohydrate;
    private double dietary_fiber;
    private double sugars;
    private double protein;
    private double potassium;
    private String photoUrl;

    public FoodItem() {
    }

    public FoodItem(String food_name, int serving_quantity, String serving_unit, double serving_weight_grams, double calories, double total_fat, double saturated_fat, double cholesterol, double sodium, double total_carbohydrate, double dietary_fiber, double sugars, double protein, double potassium, String photoUrl) {
        this.food_name = food_name;
        this.serving_quantity = serving_quantity;
        this.serving_unit = serving_unit;
        this.serving_weight_grams = serving_weight_grams;
        this.calories = calories;
        this.total_fat = total_fat;
        this.saturated_fat = saturated_fat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.total_carbohydrate = total_carbohydrate;
        this.dietary_fiber = dietary_fiber;
        this.sugars = sugars;
        this.protein = protein;
        this.potassium = potassium;
        this.photoUrl = photoUrl;
    }

    public FoodItem(double calories) {
        this.calories = calories;
        this.food_name = "Calories added manually";
        this.serving_quantity = 0;
        this.serving_unit = "";
        this.serving_weight_grams =0 ;
        this.total_fat = 0;
        this.saturated_fat = 0;
        this.cholesterol = 0;
        this.sodium = 0;
        this.total_carbohydrate = 0;
        this.dietary_fiber = 0;
        this.sugars = 0;
        this.protein = 0;
        this.potassium = 0;
        this.photoUrl = "";
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getServing_quantity() {
        return serving_quantity;
    }

    public void setServing_quantity(int serving_quantity) {
        this.serving_quantity = serving_quantity;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public void setServing_unit(String serving_unit) {
        this.serving_unit = serving_unit;
    }

    public double getServing_weight_grams() {
        return serving_weight_grams;
    }

    public void setServing_weight_grams(double serving_weight_grams) {
        this.serving_weight_grams = serving_weight_grams;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotal_fat() {
        return total_fat;
    }

    public void setTotal_fat(double total_fat) {
        this.total_fat = total_fat;
    }

    public double getSaturated_fat() {
        return saturated_fat;
    }

    public void setSaturated_fat(double saturated_fat) {
        this.saturated_fat = saturated_fat;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getTotal_carbohydrate() {
        return total_carbohydrate;
    }

    public void setTotal_carbohydrate(double total_carbohydrate) {
        this.total_carbohydrate = total_carbohydrate;
    }

    public double getDietary_fiber() {
        return dietary_fiber;
    }

    public void setDietary_fiber(double dietary_fiber) {
        this.dietary_fiber = dietary_fiber;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    @Override
    public String toString() {
        return "FoodItem{" +
                "food_name='" + food_name + '\'' +
                ", serving_quantity=" + serving_quantity +
                ", serving_unit='" + serving_unit + '\'' +
                ", serving_weight_grams=" + serving_weight_grams +
                ", calories=" + calories +
                ", total_fat=" + total_fat +
                ", saturated_fat=" + saturated_fat +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", total_carbohydrate=" + total_carbohydrate +
                ", dietary_fiber=" + dietary_fiber +
                ", sugars=" + sugars +
                ", protein=" + protein +
                ", potassium=" + potassium +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}

