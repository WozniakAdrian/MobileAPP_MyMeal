package com.AdrianWozniak.mymeal.service;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ServiceSoftwareKeyboard {

    /**
     * Function hide software keyboard from given activity
     * @param activity
     */
    public static void hide(Activity activity){
        View view = activity.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
