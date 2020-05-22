package com.AdrianWozniak.mymeal.service;

import android.content.Context;
import android.widget.TextView;

import com.AdrianWozniak.mymeal.model.FoodItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ServiceMenuListTest {




    protected List<FoodItem> foodItemList;
    protected SwipeMenuListView swipeMenuListView ;
    protected TextView tv_CaloriesSumOutput;
    protected Context context;
    protected ServiceMenuList serviceMenuList;

    @Before
    public void init(){
        try {
//            serviceMenuList = new ServiceMenuList(context, swipeMenuListView, tv_CaloriesSumOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        foodItemList = new ArrayList<>();
//        foodItemList.add(new FoodItem("coffe", 1, "cup", 236.98d, 2.37d, "https://d2xdmhkmkbyw75.cloudfront.net/1718_highres.jpg"));
//        foodItemList.add(new FoodItem("bread", 3, "slice", 87d, 231.42d, "https://d2xdmhkmkbyw75.cloudfront.net/8_highres.jpg"));
//        foodItemList.add(new FoodItem("butter", 5, "g", 5d, 35.85d, "https://d2xdmhkmkbyw75.cloudfront.net/328_highres.jpg"));
//        foodItemList.add(new FoodItem("tomato", 1, "medium whole (2-3,5' dia)", 123d, 22.14d, "https://d2xdmhkmkbyw75.cloudfront.net/191_highres.jpg"));
//        foodItemList.add(new FoodItem("ham", 50, "g", 50d, 69.5d, "https://d2xdmhkmkbyw75.cloudfront.net/217_highres.jpg"));
    }



    @Test(expected = Exception.class)
    public void serviceMenuListWithNullParams() throws Exception{
           new ServiceMenuList(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void showWithNullList() throws NullPointerException{
        serviceMenuList.show(null);
    }

    @Test(expected = NullPointerException.class)
    public void showWithEmptyList() throws NullPointerException{
        serviceMenuList.show( new ArrayList<>());
    }

    @Test(expected = NullPointerException.class)
    public void calculateCaloriesWithEmptyList() throws NullPointerException{
        serviceMenuList.calculateCaloriesIn( new ArrayList<>());
    }

    @Test(expected = NullPointerException.class)
    public void calculateCaloriesWithNullList() throws NullPointerException{
        serviceMenuList.calculateCaloriesIn(null);
    }
    @Test
    public void calculateCalories(){
        System.out.println(serviceMenuList.calculateCaloriesIn(foodItemList));

    }

    @Test
    public void getTotalCalories() {
    }
}