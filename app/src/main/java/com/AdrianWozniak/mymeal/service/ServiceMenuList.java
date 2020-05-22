package com.AdrianWozniak.mymeal.service;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.model.FoodItem;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for list menu
 */
public class ServiceMenuList {

    private SwipeMenuListView listView;
    private SwipeMenuCreator swipeMenuCreator;

    private TextView tv_CaloriesSumOutput;
    private ServiceMenuAdapter adapter;

    private Context context;

    private boolean isListVisible = false;

    private double totalCalories = 0;

    private List<FoodItem> foodItemList;


    public ServiceMenuList(Context context, SwipeMenuListView swipeMenuListView, TextView tv_CaloriesSumOutput) throws NullPointerException {
        if (swipeMenuListView instanceof SwipeMenuListView
                && swipeMenuListView != null
                && swipeMenuListView != null
                && tv_CaloriesSumOutput instanceof TextView
                && tv_CaloriesSumOutput != null) {
            this.context = context;
            this.listView = swipeMenuListView;

            //Create footer
            TextView emptyTextView = new TextView(context);
            emptyTextView.setHeight(150);
            listView.addFooterView(emptyTextView);

            this.tv_CaloriesSumOutput = tv_CaloriesSumOutput;

        } else {
            throw new NullPointerException("View swipeMenuListView can't be null or other instance than SwipeMenuListView");
        }
    }


    /**
     * Function show ListView. Visibility set to visible, set on click listener , set menu creator and adapter
     * @param foodItems
     * @throws NullPointerException
     */
    public void show(List<FoodItem> foodItems) throws NullPointerException {

        this.foodItemList = foodItems;

        if (foodItemList.isEmpty() || foodItemList == null) {
            throw new NullPointerException("Food item list can't be empty or null");
        } else {

            adapter = new ServiceMenuAdapter(context, foodItemList);

            listView.setAdapter(adapter);

            tv_CaloriesSumOutput.setText(caloriesToStringFrom(calculateCaloriesIn(foodItemList)));

            listView.setMenuCreator(initializeMenuCreator());

            listView.setOnMenuItemClickListener((position, menu, index) -> { // OnClick listener for delete button
                switch (index) {
                    case 0:
                        System.out.println("i:" + index +" p "+ position);
                        foodItemList.remove(position);
                        tv_CaloriesSumOutput.setText(caloriesToStringFrom(calculateCaloriesIn(foodItemList)));

                        adapter.notifyDataSetChanged();
                        adapter = new ServiceMenuAdapter(context, foodItemList);
                        listView.setAdapter(adapter);
                        break;
                }
                // false : close the menu; true : not close the menu
                return true;
            });



            listView.setVisibility(View.VISIBLE);
            isListVisible = true;
        }
    }

    /**
     * Function initialize swipe menu, add buttons and icon for delete button
     * @return
     */
    private SwipeMenuCreator initializeMenuCreator() {
        swipeMenuCreator = menu -> {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    context.getApplicationContext());
            // set item width
            deleteItem.setWidth(200);
            deleteItem.setBackground(R.color.light);
            // set icon
            deleteItem.setIcon(R.drawable.button_delete_x32);
            menu.addMenuItem(deleteItem);

        };
        return swipeMenuCreator;
    }

    /**
     * Function hide
     */
    public void hide() {
        if (listView == null) {
            throw new NullPointerException("Food item list can't be null");
        } else {
            if(foodItemList != null){
                foodItemList.clear();
                foodItemList = new ArrayList<>();
                adapter.notifyDataSetChanged();
                isListVisible = false;
            }
            tv_CaloriesSumOutput.setText(caloriesToStringFrom(0d));
        }
    }

    /**
     * Function for calculate sum of all calories in list
     * @param foodItemList
     * @return Double
     * @throws NullPointerException
     */
    protected Double calculateCaloriesIn(List<FoodItem> foodItemList) throws NullPointerException{
        if (foodItemList.isEmpty() && foodItemList == null) {
            throw new NullPointerException("Food item list cant be null or empty");
        } else {
            totalCalories = 0;
            foodItemList.stream().forEach(foodItem -> totalCalories += foodItem.getCalories());
            return totalCalories;
        }
    }

    /**
     *  Function build string message to show in TextView, message contains: "double cal"
     * @param calories Double
     * @return String
     */
    private String caloriesToStringFrom(Double calories)  {
        StringBuilder builder = new StringBuilder(" ");
        builder.append(Math.round(calories));
        builder.append(" cal");
        return builder.toString();
    }

    /**
     * Return List Visibility status
     * @return boolean
     */
    public boolean isListVisible() {
        return isListVisible;
    }

    /**
     * Return total calories caluclated from list
     * @return Double
     */
    public double getTotalCalories() {
        return totalCalories;
    }

    /**
     * Set total calories
     * @param input = string
     */
    public void setTotalCalories(String input) {
        totalCalories = Double.parseDouble(input);
        tv_CaloriesSumOutput.setText(caloriesToStringFrom(totalCalories));
    }
}
