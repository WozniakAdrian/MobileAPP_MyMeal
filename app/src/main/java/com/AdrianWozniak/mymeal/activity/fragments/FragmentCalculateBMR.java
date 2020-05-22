package com.AdrianWozniak.mymeal.activity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.service.ServiceBackgroundColor;
import com.google.android.material.slider.Slider;

import java.util.Objects;

public class FragmentCalculateBMR extends Fragment {

    private View rootView;

    private String[] exerciseRating = new String[]{
            "Little/no exercise",//: BMR * 1.2
            "Light exercise",//: BMR * 1.375
            "Moderate exercise (3-5 days/wk)",//: BMR * 1.55
            "Very active (6-7 days/wk)",//: BMR * 1.725
            "Extra active (very active & physical job)"}; // BMR * 1.9
    private double[] exerciseRatingValues = new double[]{1.2, 1.375, 1.55, 1.725, 1.9};
    private int exerciseRatingPosition = -1;


    private TextView tvAge;
    private int age = 22;

    private TextView tvWeight;
    private int weight = 66;

    private Button btnFemaleON, btnFemaleOFF, btnMaleON, btnMaleOFF;
    private int sex = -1; // 0:female 1:male

    private TextView tvHeight;
    private int height = 170;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calculate_bmr, container, false);


        tvAge = rootView.findViewById(R.id.fragCalcBRM_Age);
        setTextAge();
        ImageButton btnAgePLUS = rootView.findViewById(R.id.fragCalcBRM_PlusAge);
        btnAgePLUS.setOnClickListener(v -> {
            ++age;
            setTextAge();
        });
        ImageButton btnAgeMINUS = rootView.findViewById(R.id.fragCalcBRM_MinusAge);
        btnAgeMINUS.setOnClickListener(v -> {
            --age;
            setTextAge();
        });

        tvWeight = rootView.findViewById(R.id.fragCalcBRM_Weight);
        setTextWeight();
        ImageButton btnWeightPLUS = rootView.findViewById(R.id.fragCalcBRM_PlusWeight);
        btnWeightPLUS.setOnClickListener(v -> {
            ++weight;
            setTextWeight();
        });
        ImageButton btnWeightMINUS = rootView.findViewById(R.id.fragCalcBRM_MinusWeight);
        btnWeightMINUS.setOnClickListener(v -> {
            --weight;
            setTextWeight();
        });

        btnFemaleON = rootView.findViewById(R.id.fragCalcBRM_FemaleON);
        btnFemaleOFF = rootView.findViewById(R.id.fragCalcBRM_FemaleOFF);
        btnMaleON = rootView.findViewById(R.id.fragCalcBRM_MaleON);
        btnMaleOFF = rootView.findViewById(R.id.fragCalcBRM_MaleOFF);
        btnMaleOFF.setOnClickListener(v -> {
            sex = 1;
            buttonSex();
        });
        btnFemaleOFF.setOnClickListener(v -> {
            sex = 0;
            buttonSex();
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.dropdown_menu_popup_item, exerciseRating);
        AutoCompleteTextView menuDropdown = rootView.findViewById(R.id.fragCalcBRM_dropdown);
        menuDropdown.setAdapter(adapter);
        menuDropdown.setOnItemClickListener((parent, view, position, id) -> {
            exerciseRatingPosition = position;
            ServiceBackgroundColor.setNormal(getContext(), rootView.findViewById(R.id.fragCalcBMR_ActivityRatingLayout));

        });


        tvHeight = rootView.findViewById(R.id.fragCalcBRM_Height);
        setTextHeight();
        Slider sliderHeight = rootView.findViewById(R.id.fragCalcBRM_HeightSlider);
        sliderHeight.setStepSize(1);
        sliderHeight.setValue(height);
        sliderHeight.setOnChangeListener((slider, value) -> {
            height = (int) value;
            setTextHeight();
        });

        return rootView;
    }


    private void setTextAge() {
        tvAge.setText(Integer.toString(age));
    }

    private void setTextWeight() {
        tvWeight.setText(weight + "kg");
    }

    private void setTextHeight() {
        tvHeight.setText(height + "cm");
    }

    private void buttonSex() {
        if (sex == 1) {
            ServiceBackgroundColor.setNormal(getContext(), rootView.findViewById(R.id.fragCalcBMR_SexLayout));
            btnMaleON.setVisibility(View.VISIBLE);
            btnMaleOFF.setVisibility(View.GONE);
            btnFemaleOFF.setVisibility(View.VISIBLE);
            btnFemaleON.setVisibility(View.GONE);
        } else if (sex == 0) {
            ServiceBackgroundColor.setNormal(getContext(), rootView.findViewById(R.id.fragCalcBMR_SexLayout));
            btnMaleON.setVisibility(View.GONE);
            btnMaleOFF.setVisibility(View.VISIBLE);
            btnFemaleOFF.setVisibility(View.GONE);
            btnFemaleON.setVisibility(View.VISIBLE);
        }
    }

    private double calculateBMR(int weight, int height, int age, int sex) {
        if (sex == 1) {
            return (9.99 * weight) + (6.25 * height) - (4.92 * age) + 5;
        } else if (sex == 0) {
            return (9.99 * weight) + (6.25 * height) - (4.92 * age) - 161;
        }
        return 0;
    }

    private double calculateBMI(int weight, int height) {
        return (weight / Math.pow(height, 2))*10000;
    }

    /**
     * Function to calculate BMR
     * if [0] == -1 : data incomplete
     * if [0] => 0  : data completed
     * @return [0]: BMI; [1]: BMR; [2]: CPM;
     */
    public double[] calculate() {

        double[] result = {-1,-1,-1,-1};

        if (sex == -1) {
            ServiceBackgroundColor.setDanger(getContext(), rootView.findViewById(R.id.fragCalcBMR_SexLayout));
        }
        if (exerciseRatingPosition == -1) {
            ServiceBackgroundColor.setDanger(getContext(), rootView.findViewById(R.id.fragCalcBMR_ActivityRatingLayout));
        }
        if (sex != -1 && exerciseRatingPosition != -1) {
            result[0] = calculateBMI(weight, height);
            result[1] = calculateBMR(weight, height, age, sex);
            result[2] = calculateBMR(weight, height, age, sex) * exerciseRatingValues[exerciseRatingPosition];
            result[3] = weight;
        }
        return result;
    }
}
