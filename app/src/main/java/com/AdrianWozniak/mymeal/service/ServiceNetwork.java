package com.AdrianWozniak.mymeal.service;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class ServiceNetwork  {

    Activity activity;

    public ServiceNetwork(Activity activity) {
        this.activity = activity;
    }

    /**
     * Function check if there is internet connection
     * @return
     */
    public boolean isInternetConnectionAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
