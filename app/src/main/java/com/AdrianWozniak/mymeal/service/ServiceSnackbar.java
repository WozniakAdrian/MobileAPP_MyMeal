package com.AdrianWozniak.mymeal.service;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.AdrianWozniak.mymeal.R;
import com.google.android.material.snackbar.Snackbar;

public class ServiceSnackbar {

    /**
     *  Function show snackbar above fab
     * @param message
     * @param activity
     */
    public static void show(String message, Activity activity){
        Snackbar.make(activity
                .findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAnchorView(activity.getCurrentFocus().getRootView().findViewById(R.id.fab))
                .setBackgroundTint(activity.getResources().getColor(R.color.dark))
                .setTextColor(activity.getResources().getColor(R.color.light))
                .show();
    }

    /**
     * Function return snackbar, you can easly add action into it, dont forget about .show()
     * @param message
     * @param activity
     * @return Snackbar
     */
    public static Snackbar set(String message, Activity activity){
       return Snackbar.make(activity
                .findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAnchorView(activity.getCurrentFocus().getRootView().findViewById(R.id.fab))
                .setBackgroundTint(activity.getResources().getColor(R.color.dark))
                .setTextColor(activity.getResources().getColor(R.color.light));
    }
}
