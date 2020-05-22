package com.AdrianWozniak.mymeal.activity.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.activity.CalculatorActivity;
import com.AdrianWozniak.mymeal.repository.firebase.FirebaseConn;
import com.AdrianWozniak.mymeal.repository.firebase.IDailyDemandDataStatus;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.Arrays;

public class FragmentCalculateBMRResult extends Fragment implements View.OnClickListener {


    //Views
    private TextView fragBMRResult_bmiWeightStatus;
    private TextView fragBMRResult_bmiDecimal;
    private TextView fragBMRResult_bmiAfterDot;
    private TextView fragBMRResult_bmrResult;
    private TextView fragBMRResult_result;

    private MaterialButton fragBMRResult_btnLoseOFF;
    private MaterialButton fragBMRResult_btnLoseON;
    private MaterialButton fragBMRResult_btnStayON;
    private MaterialButton fragBMRResult_btnStayOFF;
    private MaterialButton fragBMRResult_btnGainOFF;
    private MaterialButton fragBMRResult_btnGainON;

    //Data
    private double BMI;
    private double BMR;
    private double CPM;
    private double WEIGHT;
    private double userCPM;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double[] result = getArguments().getDoubleArray("result");
        BMI = result[0];
        BMR = result[1];
        CPM = result[2];
        WEIGHT = result[3];

        userCPM = CPM;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculate_brm_result, container, false);

        fragBMRResult_bmiWeightStatus = v.findViewById(R.id.fragBMRResult_bmiWeightStatus);
        fragBMRResult_bmiDecimal = v.findViewById(R.id.fragBMRResult_bmiDecimal);
        fragBMRResult_bmiAfterDot = v.findViewById(R.id.fragBMRResult_bmiAfterDot);
        fragBMRResult_bmrResult = v.findViewById(R.id.fragBMRResult_bmrResult);
        fragBMRResult_result = v.findViewById(R.id.fragBMRResult_result);


        fragBMRResult_btnLoseON = v.findViewById(R.id.fragBMRResult_btnLoseON);
        fragBMRResult_btnLoseOFF = v.findViewById(R.id.fragBMRResult_btnLoseOFF);
        fragBMRResult_btnLoseOFF.setOnClickListener(this);
        fragBMRResult_btnStayON = v.findViewById(R.id.fragBMRResult_btnStayON);
        fragBMRResult_btnStayOFF = v.findViewById(R.id.fragBMRResult_btnStayOFF);
        fragBMRResult_btnStayOFF.setOnClickListener(this);
        fragBMRResult_btnGainON = v.findViewById(R.id.fragBMRResult_btnGainON);
        fragBMRResult_btnGainOFF = v.findViewById(R.id.fragBMRResult_btnGainOFF);
        fragBMRResult_btnGainOFF.setOnClickListener(this);

        setBmiResult(BMI);
        fragBMRResult_bmrResult.setText(new DecimalFormat("##").format(Math.floor(BMR)));

        fragBMRResult_result.setText(new DecimalFormat("##").format(Math.floor(CPM)));
        activeBtnStay();



        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragBMRResult_btnLoseOFF:
                userCPM = CPM - 200;
                fragBMRResult_result.setText(new DecimalFormat("##").format(Math.floor(userCPM)));
                activeBtnLose();
                break;
            case R.id.fragBMRResult_btnStayOFF:
                userCPM = CPM;

                fragBMRResult_result.setText(new DecimalFormat("##").format(Math.floor(userCPM)));
                activeBtnStay();
                break;
            case R.id.fragBMRResult_btnGainOFF:
                userCPM = CPM + 200;
                fragBMRResult_result.setText(new DecimalFormat("##").format(Math.floor(userCPM)));
                activeBtnGain();
                break;
            default:
                break;

        }

    }

    private void setBmiResult(double bmi) {

        fragBMRResult_bmiDecimal.setText((new DecimalFormat("##").format(BMI) + "."));
        fragBMRResult_bmiAfterDot.setText((new DecimalFormat("##").format((BMI - Math.floor(BMI)) * 100)));


        if (bmi < 16.0d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiStarvation);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.info));
        }
        if (bmi >= 16d && bmi <= 16.99d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiEmaciation);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.info));
        }
        if (bmi >= 17d && bmi <= 18.49d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiUnderweight);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.warning));
        }
        if (bmi >= 18.5d && bmi <= 24.99d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiIdealWeight);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.success));
        }
        if (bmi >= 25 && bmi <= 29.99d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiOverweight);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.warning));
        }
        if (bmi >= 30d && bmi <= 34.99d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiFirstDegreeObesity);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.danger));
        }
        if (bmi >= 35d && bmi <= 39.99d) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiSecondDegreeObesity);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.danger));
        }
        if (bmi >= 40) {
            fragBMRResult_bmiWeightStatus.setText(R.string.fragBMRResult_bmiExtremeObesity);
            fragBMRResult_bmiWeightStatus.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.danger));
        }
    }


    private void activeBtnLose() {
        fragBMRResult_btnLoseOFF.setVisibility(View.GONE);
        fragBMRResult_btnLoseON.setVisibility(View.VISIBLE);
        fragBMRResult_btnStayON.setVisibility(View.GONE);
        fragBMRResult_btnStayOFF.setVisibility(View.VISIBLE);
        fragBMRResult_btnGainON.setVisibility(View.GONE);
        fragBMRResult_btnGainOFF.setVisibility(View.VISIBLE);
    }

    private void activeBtnStay() {
        fragBMRResult_btnLoseOFF.setVisibility(View.VISIBLE);
        fragBMRResult_btnLoseON.setVisibility(View.GONE);
        fragBMRResult_btnStayON.setVisibility(View.VISIBLE);
        fragBMRResult_btnStayOFF.setVisibility(View.GONE);
        fragBMRResult_btnGainON.setVisibility(View.GONE);
        fragBMRResult_btnGainOFF.setVisibility(View.VISIBLE);
    }

    private void activeBtnGain() {
        fragBMRResult_btnLoseOFF.setVisibility(View.VISIBLE);
        fragBMRResult_btnLoseON.setVisibility(View.GONE);
        fragBMRResult_btnStayON.setVisibility(View.GONE);
        fragBMRResult_btnStayOFF.setVisibility(View.VISIBLE);
        fragBMRResult_btnGainON.setVisibility(View.VISIBLE);
        fragBMRResult_btnGainOFF.setVisibility(View.GONE);
    }


    /**
     *  Function to get daily calories demand (CPM)
     * @return
     */
    public double[] getResult() {
        double[] result = {userCPM, WEIGHT};
        return result;
    }
}






















































