package com.AdrianWozniak.mymeal.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

/**
 * Class that calculates time passed from last meal
 */
public class ServiceTimeCounter {

    private SharedPreferences sharedPreferences;

    public ServiceTimeCounter(Activity activity) {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    /**
     * Function should be call when meal was added
     */
    public void setNewStartTime() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LastSavedMeal", String.valueOf(System.currentTimeMillis()));
        editor.commit();
    }


    /**
     * Each call method return array with time passed from last meal
     *
     * @return int[] = {seconds, minutes, hours}
     */
    public int[] tick() {
        int[] result = {0,0,0};

        long start = Long.parseLong(sharedPreferences.getString("LastSavedMeal", ""));

        long passed = (System.currentTimeMillis() - start) / 1000;

        if (passed > 60 && passed < 3600) {
            result[1] = (int) passed / 60;
            result[0] = (int) passed - result[1] * 60;

        } else if (passed > 3600) {
            result[2] = (int) passed / 3600;
            result[1] = (int) (passed - result[2] * 3600) / 60;
            result[0] = (int) passed - (result[2] * 3600) - (result[1] * 60);
        }


        return result;
    }

}
