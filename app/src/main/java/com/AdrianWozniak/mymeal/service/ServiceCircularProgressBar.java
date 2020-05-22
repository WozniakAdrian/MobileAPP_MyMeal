package com.AdrianWozniak.mymeal.service;

import android.content.Context;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ServiceCircularProgressBar {

    private Context context;
    private CircularProgressBar circularProgressBar;

    public ServiceCircularProgressBar(Context context, CircularProgressBar circularProgressBar) {
        this.context = context;
        this.circularProgressBar = circularProgressBar;
    }

    public void setMax(float max){
        circularProgressBar.setProgressMax(max);
    }

    public void setProgress(float progress){
        circularProgressBar.setProgressWithAnimation(progress, 4000l);
    }
}
