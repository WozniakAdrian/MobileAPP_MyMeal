package com.AdrianWozniak.mymeal.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FoodItemTest {

    private FoodItem foodItemWithData;
    private FoodItem foodItem;

    @Before
    public void setUp() throws Exception {
//        foodItemWithData =  new FoodItem("coffe", 1, "cup", 236.98d, 2.37d, "https://d2xdmhkmkbyw75.cloudfront.net/1718_highres.jpg");
//        foodItem = new FoodItem();
    }

    @Test
    public void setFood_name() {
        foodItem.setFood_name("name");
        assertEquals("name", foodItem.getFood_name());
    }

    @Test
    public void setServing_quantity() {
        foodItem.setServing_quantity(3);
        assertEquals(3, foodItem.getServing_quantity());
    }

    @Test
    public void setServing_unit() {
        foodItem.setServing_unit("cup");
        assertEquals("cup", foodItem.getServing_unit());
    }

    @Test
    public void setServing_weight_grams() {
        foodItem.setServing_weight_grams(300d);
        assertEquals(300d , foodItem.getServing_weight_grams(), 0.0);
    }

    @Test
    public void setCalories() {
        foodItem.setCalories(321d);
        assertEquals(321d, foodItem.getCalories(), 0.0);
    }

    @Test
    public void setPhotoUrl() {
        foodItem.setPhotoUrl("https://d2xdmhkmkbyw75.cloudfront.net/1718_highres.jpg");
        assertEquals("https://d2xdmhkmkbyw75.cloudfront.net/1718_highres.jpg", foodItem.getPhotoUrl());
    }

    @Test
    public void classExist(){
        assertNotNull(foodItemWithData);
    }


}