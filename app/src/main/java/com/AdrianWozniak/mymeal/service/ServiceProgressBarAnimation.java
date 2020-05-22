package com.AdrianWozniak.mymeal.service;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ServiceProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;

    /**
     * Function set animation on progress bar
     *
     * @param progressBar ProgressBar object
     * @param from
     * @param to
     */
    public ServiceProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}




