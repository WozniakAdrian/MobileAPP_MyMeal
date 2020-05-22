package com.AdrianWozniak.mymeal.service;

import android.content.Context;

import android.view.View;

import com.AdrianWozniak.mymeal.R;

public class ServiceBackgroundColor {

    /**
     * Set danger color into borders of view
     * @param context
     * @param view
     */
    public static void setDanger(Context context, View view ){
        view.setBackground(context.getDrawable(R.drawable.elevation_shadow_bacground_danger));
    }

    /**
     * Set normal color into borders of view
     * @param context
     * @param view
     */
    public static void setNormal(Context context, View view ){
        view.setBackground(context.getDrawable(R.drawable.elevation_shadow_background_light));
    }

    /**
     * Set wrning color into borders of view
     * @param context
     * @param view
     */
    public static void setWarning(Context context, View view ){
        view.setBackground(context.getDrawable(R.drawable.elevation_shadow_bacground_warning));
    }
}
