package com.AdrianWozniak.mymeal.service;

import com.AdrianWozniak.mymeal.model.FoodItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ServiceSumConsumedCaloriesTest {

    Map<String, List<FoodItem>> consumedCalories;
    Map<String, Double> dailyDemandCalories;
    @Before
    public void setData(){
        consumedCalories = new HashMap<>();
        List<FoodItem> list  = new ArrayList<>();
        list.add(new FoodItem("initial", 0, "0", 0, 100, 0,
                0, 0, 0, 0, 0, 0, 0, 0, "null"));
        list.add(new FoodItem("initial", 0, "0", 0, 100, 0,
                0, 0, 0, 0, 0, 0, 0, 0, "null"));
        consumedCalories.put("Tue Feb 18 00:01:01 GMT+01:00 2020", list);
        consumedCalories.put("Tue Feb 18 00:02:01 GMT+01:00 2020", list);
        consumedCalories.put("Tue Mar 03 09:15:33 GMT+01:00 2020", list);


        dailyDemandCalories = new HashMap<>();
        dailyDemandCalories.put("Tue Feb 18 00:01:01 GMT+01:00 2020", 100d);
        dailyDemandCalories.put("Tue Feb 18 00:01:01 GMT+01:00 2020", 200d);
        dailyDemandCalories.put("Mon Mar 2 00:01:01 GMT+01:00 2020", 300d);

    }



    @Test
    public void dailyByDate() {

        double result = ServiceArrays.getSumCaloriesInGivenDate("Tue Feb 18 00:01:01 GMT+01:00 2020", consumedCalories);
       assertEquals(400d, result, 0.0);
    }

    @Test(expected = NullPointerException.class)
    public void dailyByDateNullArgument() {
        double result = ServiceArrays.getSumCaloriesInGivenDate(null, null);
        assertEquals(400d, result, 0.0);
    }


}