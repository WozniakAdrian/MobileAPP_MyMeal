package com.AdrianWozniak.mymeal.activity.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.model.UserData;
import com.AdrianWozniak.mymeal.repository.file.WeatherICO;
import com.AdrianWozniak.mymeal.repository.firebase.FirebaseConn;
import com.AdrianWozniak.mymeal.service.ServiceBackgroundColor;
import com.AdrianWozniak.mymeal.service.ServiceCircularProgressBar;
import com.AdrianWozniak.mymeal.service.ServiceProgressBarAnimation;
import com.AdrianWozniak.mymeal.service.ServiceTextAnimationOutput;
import com.AdrianWozniak.mymeal.service.ServiceDateTime;
import com.AdrianWozniak.mymeal.service.ServiceArrays;
import com.AdrianWozniak.mymeal.service.ServiceTimeCounter;
import com.AdrianWozniak.mymeal.service.ServiceWeather;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class
FragmentCaloriesCalculator extends Fragment {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private static Date TODAY = new Date();

    private TextView fragCalCalc_LastMealValue;
    private TextView fragCalCalc_SodiumValue;
    private TextView fragCalCalc_Date;
    private TextView fragCalCalc_City;
    private TextView fragCalCalc_ConsumedCal;
    private TextView fragCalCalc_PotassiumValue;
    private TextView fragCalCalc_CalLeftValue;
    private TextView fragCalCalc_DietaryFiberValue;
    private TextView fragCalCalc_SaturatedFatValue;
    private TextView fragCalCalc_SugarsValue;
    private TextView fragCalCalc_CholesterolValue;

    private ServiceCircularProgressBar circularProgressBar;

    private ProgressBar fragCalCalc_FatProgressbar;
    private TextView fragCalCalc_FatValue;

    private ProgressBar fragCalCalc_CarbProgressBar;
    private TextView fragCalCalc_CarbValue;
    private View fragCalCalc_CarbLayout;

    private ProgressBar fragCalCalc_ProteinProgressbar;
    private TextView fragCalCalc_ProteinValue;

    private TextView fragCalCalc_Temp;
    private ImageView fragCalCalc_WeatherIco;

    private double DAILY_CONSUMED_CALORIES;
    private double DAILY_DEMAND;
    private double WEIGHT;
    private int FAT_DEMAND_GRAMS;
    private int PROTEIN_DEMAND_GRAMS;
    private int CARB_DEMAND_GRAMS;

    private ServiceTimeCounter serviceTimeCounter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calories_calculator, container, false);

        fragCalCalc_LastMealValue = v.findViewById(R.id.fragCalCalc_LastMealValue);
        fragCalCalc_SodiumValue = v.findViewById(R.id.fragCalCalc_SodiumValue);
        fragCalCalc_PotassiumValue = v.findViewById(R.id.fragCalCalc_PotassiumValue);
        fragCalCalc_CholesterolValue = v.findViewById(R.id.fragCalCalc_CholesterolValue);
        fragCalCalc_DietaryFiberValue = v.findViewById(R.id.fragCalCalc_DietaryFiberValue);
        fragCalCalc_SugarsValue = v.findViewById(R.id.fragCalCalc_SugarsValue);
        fragCalCalc_SaturatedFatValue = v.findViewById(R.id.fragCalCalc_SaturatedFatValue);
        fragCalCalc_Date = v.findViewById(R.id.fragCalCalc_Date);
        fragCalCalc_City = v.findViewById(R.id.fragCalCalc_City);
        fragCalCalc_ConsumedCal = v.findViewById(R.id.fragCalCalc_ConsumedCal);
        fragCalCalc_CalLeftValue = v.findViewById(R.id.fragCalCalc_CalLeftValue);
        fragCalCalc_ProteinProgressbar = v.findViewById(R.id.fragCalCalc_ProteinProgressbar);
        fragCalCalc_ProteinValue = v.findViewById(R.id.fragCalCalc_ProteinValue);
        fragCalCalc_FatProgressbar = v.findViewById(R.id.fragCalCalc_FatProgressbar);
        fragCalCalc_FatValue = v.findViewById(R.id.fragCalCalc_FatValue);
        fragCalCalc_CarbProgressBar = v.findViewById(R.id.fragCalCalc_CarbProgressBar);
        fragCalCalc_CarbValue = v.findViewById(R.id.fragCalCalc_CarbValue);
        fragCalCalc_CarbLayout = v.findViewById(R.id.fragCalCalc_CarbLayout);
        fragCalCalc_Temp = v.findViewById(R.id.fragCalCalc_Temp);
        fragCalCalc_WeatherIco = v.findViewById(R.id.fragCalCalc_WeatherIco);

        circularProgressBar = new ServiceCircularProgressBar(getActivity().getApplicationContext(), v.findViewById(R.id.fragCalCalc_ProgressBar));

        serviceTimeCounter = new ServiceTimeCounter(getActivity());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new FirebaseConn().getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid(), (message, isDataReadCorrectly, userData) -> {

            if (isDataReadCorrectly) {

                DAILY_CONSUMED_CALORIES = ServiceArrays.getSumCaloriesInGivenDate(TODAY.toString(), userData.getConsumedCalories());
                DAILY_DEMAND = ServiceArrays.sortAndGetLastAddedIndex(userData.getDailyDemandCalories());
                WEIGHT = ServiceArrays.sortAndGetLastAddedIndex(userData.getWeight());

                fragCalCalc_Date.setText(ServiceDateTime.getNumberOfDayAndName().toUpperCase());
                fragCalCalc_City.setText(userData.getCity());

                consumedCalories();
                ServiceTextAnimationOutput.animateText((float) DAILY_DEMAND, (float) DAILY_DEMAND - (float) DAILY_CONSUMED_CALORIES, fragCalCalc_CalLeftValue);
                consumedProteins(userData);
                consumedFat(userData);
                consumedCarb(userData);
                consumedSaturatedFat(userData);
                consumedSugar(userData);
                consumedDietaryFiber(userData);
                consumedCholesterol(userData);
                consumedPotassium(userData);
                consumedSodium(userData);

                setWeatherThread(userData).start();

                executor.submit(setTimer());
            }
        });
    }

    private void consumedSodium(UserData userData) {
        float CURRENT_SODIUM_VALUE = (float) ServiceArrays.getSumSodiumInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if(CURRENT_SODIUM_VALUE>0){
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_SODIUM_VALUE, fragCalCalc_SodiumValue, "mg");
        }
    }

    /**
     * Function sum potassium and animate it
     * @param userData
     */
    private void consumedPotassium(UserData userData) {
        float CURRENT_POTASSIUM_VALUE =(float) ServiceArrays.getSumPotassiumInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if(CURRENT_POTASSIUM_VALUE>0){
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_POTASSIUM_VALUE, fragCalCalc_PotassiumValue, "mg");
        }
    }

    /**
     * Function sum cholesterol and animate it
     * @param userData
     */
    private void consumedCholesterol(UserData userData) {
        float CURRENT_CHOLESTEROL_VALUE = (float) ServiceArrays.getSumCholesterolInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if (CURRENT_CHOLESTEROL_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_CHOLESTEROL_VALUE, fragCalCalc_CholesterolValue, "mg");
        }
    }

    /**
     * Function sum dietary fiber and animate it
     * @param userData
     */
    private void consumedDietaryFiber(UserData userData) {
        float CURRENT_DIETARY_FIBER_VALUE = (float) ServiceArrays.getSumSugarsInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if (CURRENT_DIETARY_FIBER_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_DIETARY_FIBER_VALUE, fragCalCalc_DietaryFiberValue, "g");
        }
    }

    /**
     * Function sum sugars and animate it
     * @param userData
     */
    private void consumedSugar(UserData userData) {
        float CURRENT_SUGAR_VALUE = (float) ServiceArrays.getSumSugarsInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if (CURRENT_SUGAR_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_SUGAR_VALUE, fragCalCalc_SugarsValue, "g");
        }
    }


    /**
     * Function sum consumed Carb and animate it into progress bar also on TextView
     *
     * @param userData
     */
    @SuppressLint("ResourceAsColor")
    private void consumedCarb(UserData userData) {
        CARB_DEMAND_GRAMS = (int) ((DAILY_DEMAND - (2 * WEIGHT * 4) - (DAILY_DEMAND / 4d)) / 4d);
        fragCalCalc_CarbProgressBar.setMax(CARB_DEMAND_GRAMS);
        float CURRENT_CARB_VALUE = (float) ServiceArrays.getSumConsumedCarbInGivenDate(TODAY.toString(), userData.getConsumedCalories());

        if (CURRENT_CARB_VALUE > CARB_DEMAND_GRAMS) {
            ServiceBackgroundColor.setDanger(getActivity().getApplicationContext(), fragCalCalc_CarbProgressBar);
        }

        ServiceProgressBarAnimation animation = new ServiceProgressBarAnimation(fragCalCalc_CarbProgressBar, 0, CURRENT_CARB_VALUE);
        animation.setDuration(4000);
        fragCalCalc_CarbProgressBar.startAnimation(animation);
        if (CURRENT_CARB_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_CARB_VALUE, fragCalCalc_CarbValue,"g");
        }
    }


    /**
     * Function sum consumed fat and animate it into progress bar also on TextView
     *
     * @param userData
     */
    @SuppressLint("ResourceAsColor")
    private void consumedFat(UserData userData) {
        FAT_DEMAND_GRAMS = (int) ((DAILY_DEMAND * 0.25) / 9);
        fragCalCalc_FatProgressbar.setMax(FAT_DEMAND_GRAMS);
        float CURRENT_FAT_VALUE = (float) ServiceArrays.getSumConsumedFatInGivenDate(TODAY.toString(), userData.getConsumedCalories());

        if (CURRENT_FAT_VALUE > FAT_DEMAND_GRAMS) {
            ServiceBackgroundColor.setDanger(getActivity().getApplicationContext(), fragCalCalc_FatProgressbar);
        }

        ServiceProgressBarAnimation animation = new ServiceProgressBarAnimation(fragCalCalc_FatProgressbar, 0, CURRENT_FAT_VALUE);
        animation.setDuration(4000);
        fragCalCalc_FatProgressbar.startAnimation(animation);
        if (CURRENT_FAT_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_FAT_VALUE, fragCalCalc_FatValue, "g");
        }

    }


    /**
     * Function sum consumed saturated fat and animate it
     * @param userData
     */
    private void consumedSaturatedFat(UserData userData){
        float CURRENT_SATURATED_FAT_VALUE = (float) ServiceArrays.getSumSaturatedFatInGivenDate(TODAY.toString(), userData.getConsumedCalories());
        if (CURRENT_SATURATED_FAT_VALUE > 0) {
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_SATURATED_FAT_VALUE, fragCalCalc_SaturatedFatValue,"g");
        }

    }


    /**
     * Function sum consumed proteins and animate it into progress bar also on TextView
     *
     * @param userData
     */
    private void consumedProteins(UserData userData) {
        PROTEIN_DEMAND_GRAMS = (int) (2d * WEIGHT);
        fragCalCalc_ProteinProgressbar.setMax(PROTEIN_DEMAND_GRAMS);
        float CURRENT_PROTEIN_VALUE = (float) ServiceArrays.getSumConsumedProteinsInGivenDate(TODAY.toString(), userData.getConsumedCalories());

        if (CURRENT_PROTEIN_VALUE > PROTEIN_DEMAND_GRAMS) {
            ServiceBackgroundColor.setDanger(getActivity().getApplicationContext(), fragCalCalc_ProteinProgressbar);
        }

        ServiceProgressBarAnimation fragCalCalc_ProteinProgressbarAnimation = new ServiceProgressBarAnimation(fragCalCalc_ProteinProgressbar, 0, CURRENT_PROTEIN_VALUE);
        fragCalCalc_ProteinProgressbarAnimation.setDuration(4000);
        fragCalCalc_ProteinProgressbar.startAnimation(fragCalCalc_ProteinProgressbarAnimation);
        if(CURRENT_PROTEIN_VALUE > 0){
            ServiceTextAnimationOutput.animateTextWithSuffix(0, CURRENT_PROTEIN_VALUE, fragCalCalc_ProteinValue,"g");
        }
    }


    /**
     * Function sum consumed calories and present it into circular progress bar and text view
     */
    private void consumedCalories() {

        // Output TextView
        if (DAILY_CONSUMED_CALORIES != 0) {
            ServiceTextAnimationOutput.animateText(0, (float) DAILY_CONSUMED_CALORIES, fragCalCalc_ConsumedCal);
        }

        // Progress bar
        circularProgressBar.setMax((float) DAILY_DEMAND);
        if (DAILY_CONSUMED_CALORIES != 0) {
            circularProgressBar.setProgress((float) DAILY_CONSUMED_CALORIES);
        }
    }

    /***
     * Function creates new thread with get weather from api and presents results into views
     * @param userData object
     * @return thread
     */
    private Thread setWeatherThread(UserData userData) {
        return new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    ServiceWeather serviceWeather = new ServiceWeather(getActivity().getApplicationContext(), userData.getCity());
                    while (true) {
                        if (serviceWeather.isWeatherEnabled()) {
                            fragCalCalc_Temp.setText(String.valueOf(serviceWeather.getWeather().getTemp()));
                            fragCalCalc_WeatherIco.setImageResource(WeatherICO.getIcoByKey(serviceWeather.getWeather().getWeatherId()));
                            break;
                        }
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    /**
     * Function show time passed since last added meal
     * if meal was more than 3 hours ago, change color to green
     */
    private Runnable setTimer() {
         return ()->{
             while(true){

                 if(fragCalCalc_LastMealValue!=null){
                     int[] result = serviceTimeCounter.tick();

                     if(result[2]>0){
                         fragCalCalc_LastMealValue.setText(result[2]+":"+result[1]+" h ago");
                     }else{
                         fragCalCalc_LastMealValue.setText(result[1]+":"+result[0]+" min ago");
                     }

                     if(result[2]>3){
                         fragCalCalc_LastMealValue.setTextColor(getResources().getColor(R.color.success));
                     }
                 }

                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }



}
