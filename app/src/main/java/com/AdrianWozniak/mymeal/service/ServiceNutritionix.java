package com.AdrianWozniak.mymeal.service;

import android.content.Context;
import android.util.Log;

import com.AdrianWozniak.mymeal.model.FoodItem;
import com.AdrianWozniak.mymeal.repository.api.APINutritionix;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ServiceNutritionix {

    private Context context;
    private String responseBodyJSON;

    public boolean isArrayEnabled = false;
    boolean isResponseEnabled = false;
    boolean isResponseError = false;

    private List<FoodItem> foodItemArrayList;


    public ServiceNutritionix(Context context) {
        isArrayEnabled = false;
        this.context = context;
    }

    /**
     * This Function is creating two task in FixedThreadPool. At first function call and hit api endpoint, waiting for response then in next
     * task function send response body to 'getFoodListFrom(responseBody)
     *
     * @param query String
     */
    private void getDataFromNutritionixAPI(String query) {
        APINutritionix apiNutritionix = new APINutritionix(context, query);

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Runnable getResponseFromAPITask = () -> {
            while (true) {
                if (apiNutritionix.isResponseEnabled()) {
                    responseBodyJSON = apiNutritionix.getResponseBody();
                    System.out.println(responseBodyJSON);
                    isResponseEnabled = true;
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable getListFromResponseJSONTask = () -> {

            if (isResponseCorrect(responseBodyJSON)) {
                try {
                    getFoodListFrom(responseBodyJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                isResponseError = true;
            }
        };

        executor.submit(getResponseFromAPITask);
        executor.submit(getListFromResponseJSONTask);
        executor.shutdown();

    }

    /**
     * This Function is manipulating JSON response body to get information from it and output into ArrayList;
     *
     * @param JSON response json from Nutritionix API
     * @return ArrayList<FoodItem>
     * @throws JSONException
     */
    private ArrayList<FoodItem> getFoodListFrom(String JSON) throws JSONException {

        foodItemArrayList = new ArrayList<>();

        if (!JSON.isEmpty() && JSON != null) {
            JSONObject jsonObject = new JSONObject(JSON);
            JSONArray foodsArray = jsonObject.getJSONArray("foods");

            for (int i = 0; i < foodsArray.length(); i++) {
                FoodItem item = new FoodItem();

                if (foodsArray.getJSONObject(i).get("food_name").equals(null)) {
                    item.setFood_name("");
                } else {
                    item.setFood_name(foodsArray.getJSONObject(i).getString("food_name"));
                }
                if (foodsArray.getJSONObject(i).get("serving_qty").equals(null)) {
                    item.setServing_quantity(0);
                } else {
                    item.setServing_quantity(foodsArray.getJSONObject(i).getInt("serving_qty"));
                }
                if (foodsArray.getJSONObject(i).get("serving_unit").equals(null)) {
                    item.setServing_unit("");
                } else {
                    item.setServing_unit(foodsArray.getJSONObject(i).getString("serving_unit"));
                }
                if (foodsArray.getJSONObject(i).get("serving_weight_grams").equals(null)) {
                    item.setServing_weight_grams(0d);
                } else {
                    item.setServing_weight_grams(foodsArray.getJSONObject(i).getDouble("serving_weight_grams"));
                }
                if (foodsArray.getJSONObject(i).get("nf_calories").equals(null)) {
                    item.setCalories(0d);
                } else {
                    item.setCalories(foodsArray.getJSONObject(i).getDouble("nf_calories"));
                }
                if (foodsArray.getJSONObject(i).get("nf_total_fat").equals(null)) {
                    item.setTotal_fat(0d);
                } else {
                    item.setTotal_fat(foodsArray.getJSONObject(i).getDouble("nf_total_fat"));
                }
                if (foodsArray.getJSONObject(i).get("nf_saturated_fat").equals(null)) {
                    item.setSaturated_fat(0d);
                } else {
                    item.setSaturated_fat(foodsArray.getJSONObject(i).getDouble("nf_saturated_fat"));
                }
                if (foodsArray.getJSONObject(i).get("nf_cholesterol").equals(null)) {
                    item.setCholesterol(0d);
                } else {
                    item.setCholesterol(foodsArray.getJSONObject(i).getDouble("nf_cholesterol"));
                }
                if (foodsArray.getJSONObject(i).get("nf_sodium").equals(null)) {
                    item.setSodium(0d);
                } else {
                    item.setSodium(foodsArray.getJSONObject(i).getDouble("nf_sodium"));
                }
                if (foodsArray.getJSONObject(i).get("nf_total_carbohydrate").equals(null)) {
                    item.setTotal_carbohydrate(0d);
                } else {
                    item.setTotal_carbohydrate(foodsArray.getJSONObject(i).getDouble("nf_total_carbohydrate"));
                }
                if (foodsArray.getJSONObject(i).get("nf_dietary_fiber").equals(null)) {
                    item.setDietary_fiber(0d);
                } else {
                    item.setDietary_fiber(foodsArray.getJSONObject(i).getDouble("nf_dietary_fiber"));
                }
                if (foodsArray.getJSONObject(i).get("nf_sugars").equals(null)) {
                    item.setSugars(0d);
                } else {
                    item.setSugars(foodsArray.getJSONObject(i).getDouble("nf_sugars"));
                }
                if (foodsArray.getJSONObject(i).get("nf_protein").equals(null)) {
                    item.setProtein(0d);
                } else {
                    item.setProtein(foodsArray.getJSONObject(i).getDouble("nf_protein"));
                }
                if (foodsArray.getJSONObject(i).get("nf_potassium").equals(null)) {
                    item.setPotassium(0d);
                } else {
                    item.setPotassium(foodsArray.getJSONObject(i).getDouble("nf_potassium"));
                }
                if (foodsArray.getJSONObject(i).get("photo").equals(null)) {
                    item.setPhotoUrl("");
                } else {
                    JSONObject photoJSON = foodsArray.getJSONObject(i).getJSONObject("photo");
                    item.setPhotoUrl(photoJSON.getString("thumb"));
                }

                Log.e("Nutrition item: ", item.toString());
                foodItemArrayList.add(item);
            }

            isArrayEnabled = true;
        }
        return null;
    }


    /**
     * Function for call nutritionix api with query
     *
     * @param query
     */
    public void callAPI(String query) throws Exception {
        if (query == null) {
            throw new NullPointerException("Query in Nntritionix API cannot be null");
        } else if (query.isEmpty()) {
            throw new Exception("Query in Nutritionix API cannot be empty");
        } else {
            isArrayEnabled = false;
            getDataFromNutritionixAPI(query);
        }
    }


    /**
     * Returns Array FoodItems
     * if not available return null;
     *
     * @return ArrayList<FoodItem>
     */
    public List<FoodItem> getFoodItemArrayList() {
        return foodItemArrayList;
    }


    /**
     * Function for validate response. If response doesn't contains foods then throw exception
     *
     * @param JSON
     * @return boolean
     */
    private boolean isResponseCorrect(String JSON) {
        if (!JSON.isEmpty() && JSON != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(JSON);
                JSONArray foodsArray = jsonObject.getJSONArray("foods");
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Function for check validation response status
     *
     * @return boolean
     */
    public boolean isResponseError() {
        return isResponseError;
    }
}