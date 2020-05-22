package com.AdrianWozniak.mymeal.activity.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.activity.CalculatorActivity;
import com.AdrianWozniak.mymeal.model.UserData;
import com.AdrianWozniak.mymeal.repository.firebase.FirebaseConn;
import com.AdrianWozniak.mymeal.service.ServiceArrays;
import com.AdrianWozniak.mymeal.service.ServiceSnackbar;
import com.AdrianWozniak.mymeal.service.ServiceSoftwareKeyboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment implements View.OnLongClickListener {

    //Firebase User
    private FirebaseUser firebaseUser;

    //Root view
    private View rootView;

    //User data
    private static UserData userData;
    private boolean isDataReady;

    //Fragment views
    private CircleImageView userAvatar;
    private TextView displayName;
    private EditText etDisplayName;

    private TextView userCity;
    private EditText etUserCity;
    private TextView userEmail;
    private EditText etUserEmail;
    private TextView userCaloriesDemand;

    //Edit flags
    private boolean editNameFlag = false;
    private boolean editCityFlag = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getLocalUserData();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        displayName = rootView.findViewById(R.id.profileFrag_displayName);
        etDisplayName = rootView.findViewById(R.id.profileFrag_EtdisplayName);
        userCity = rootView.findViewById(R.id.profileFrag_UserCity);
        etUserCity = rootView.findViewById(R.id.profileFrag_EtUserCity);
        userEmail = rootView.findViewById(R.id.profileFrag_UserEmail);
        etUserEmail = rootView.findViewById(R.id.profileFrag_EtUserEmail);
        userCaloriesDemand = rootView.findViewById(R.id.profileFrag_UserDailyDemandCalories);


        userCity.setOnLongClickListener(this);
        userEmail.setOnLongClickListener(this);
        userCaloriesDemand.setOnLongClickListener(this);
        displayName.setOnLongClickListener(this);


        loadDataFromLocalUserData();




        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        //ON KEY BACK PRESSED
        this.getView().setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK )
            {
                if(editNameFlag){
                    displayName.setVisibility(View.VISIBLE);
                    etDisplayName.setVisibility(View.GONE);
                    editNameFlag=false;
                    return true;
                }
                if(editCityFlag){
                    userCity.setVisibility(View.VISIBLE);
                    etUserCity.setVisibility(View.GONE);
                    editCityFlag=false;
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.profileFrag_displayName:
                saveChangesDisplayName();
                break;
            case R.id.profileFrag_UserCity:
                saveChangesUserCity();
                break;
            case R.id.profileFrag_UserEmail:
                //todo: useremail trzeba zmienic w firebaseUsers
                break;
            case R.id.profileFrag_UserDailyDemandCalories:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new FragmentCalculateBMR()).commit();
                break;
            default:
                break;
        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void saveChangesUserCity() {
        editCityFlag=true;

        userCity.setVisibility(View.GONE);
        etUserCity.setVisibility(View.VISIBLE);

        etUserCity.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etUserCity.getRight() - etUserCity.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    ServiceSoftwareKeyboard.hide(getActivity());

                    if (etUserCity.getText().toString().isEmpty() || etUserCity.getText().toString().equals(null)) {

                    } else if (Pattern.matches("^[a-zA-Z]*$", etUserCity.getText().toString())) {

                        String data = etUserCity.getText().toString();

                        etUserCity.setHint(userCity.getText());
                        userCity.setVisibility(View.VISIBLE);
                        etUserCity.setVisibility(View.GONE);
                        new FirebaseConn().changeCity(firebaseUser, data, (msg, isSuccess) -> {
                            if (isSuccess) {
                                ServiceSnackbar.show(msg, getActivity());
                                getLocalUserData();
                                loadDataFromLocalUserData();
                            }
                        });
                        editCityFlag = false;
                        return true;
                    }

                }
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void saveChangesDisplayName() {


        editNameFlag = true;

        displayName.setVisibility(View.GONE);
        etDisplayName.setVisibility(View.VISIBLE);

        etDisplayName.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ServiceSoftwareKeyboard.hide(getActivity());

                if (event.getRawX() >= (etDisplayName.getRight() - etDisplayName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    if(etDisplayName.getText().toString().isEmpty()){

                    }else{
                        String data = etDisplayName.getText().toString();

                        etDisplayName.setHint(displayName.getText());
                        displayName.setVisibility(View.VISIBLE);
                        etDisplayName.setVisibility(View.GONE);
                        new FirebaseConn().changeDisplayName(firebaseUser, data, (msg, isSuccess) -> {
                            if (isSuccess) {
                                ServiceSnackbar.show(msg, getActivity());
                                getLocalUserData();
                                loadDataFromLocalUserData();
                            }
                        });
                        editNameFlag = false;
                        return true;
                    }

                }
            }
            return false;
        });


    }

    private void getLocalUserData() {
        new FirebaseConn().getUserData(firebaseUser.getUid(), (message, isDataReadCorrectly, userData) -> {
            isDataReady = isDataReadCorrectly;
            FragmentProfile.userData = userData;
        });
    }

    private void loadDataFromLocalUserData() {
        new Thread(() -> {
            while (true) {
                if (isDataReady) {

                    userCaloriesDemand.setText((int)ServiceArrays.sortAndGetLastAddedIndex(userData.getDailyDemandCalories()) + "CAL");
                    displayName.setText(userData.getDisplayName());
                    userCity.setText(userData.getCity());
                    userEmail.setText(userData.getEmail());
                    break;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}

