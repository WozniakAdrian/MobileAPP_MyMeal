package com.AdrianWozniak.mymeal.service;

import android.animation.ValueAnimator;
import android.app.Activity;

import android.widget.TextView;


import com.AdrianWozniak.mymeal.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;


/**
 * Class is responsible for represent calories consumed daily in main layout
 *  also contains animation for text view
 */
public class ServiceTextAnimationOutput extends Activity {
    private static int ANIMATION_DURATION = 3000;


    public static void animateText(float from, float to, TextView textView) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(animation -> {

            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            String numberAsString = decimalFormat.format(animation.getAnimatedValue());
            textView.setText(numberAsString);
                });

        animator.start();
    }

    public static void animateTextWithSuffix(float from, float to, TextView textView, String suffix) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(animation -> {

            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            String numberAsString = decimalFormat.format(animation.getAnimatedValue());
            textView.setText(numberAsString+suffix);
        });

        animator.start();
    }
}
