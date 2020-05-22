package com.AdrianWozniak.mymeal.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentActionCalculator;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentCalculateBMR;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentCalculateBMRResult;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentCaloriesCalculator;
import com.AdrianWozniak.mymeal.activity.fragments.EFragmentFlag;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentProfile;
import com.AdrianWozniak.mymeal.activity.fragments.FragmentSearch;

import com.AdrianWozniak.mymeal.repository.firebase.FirebaseConn;


import com.AdrianWozniak.mymeal.service.ServiceNetwork;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CalculatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // Firebase Auth
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    // Fragment
    private EFragmentFlag eFragmentFlag;
    private FragmentCalculateBMR fragmentCalculateBMR;
    private FragmentCalculateBMRResult fragmentCalculateBMRResult;

    // Bottom nav
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageButton btn_caloriesCalculator;
    private ImageButton btn_actionCalculator;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Bottom nav
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.bar);
        navigationView = findViewById(R.id.navigationView);

        btn_caloriesCalculator = findViewById(R.id.btn_caloriesCalculator);
        btn_actionCalculator = findViewById(R.id.btn_actionCalculator);

        fab = findViewById(R.id.fab);

        //Fragment initialization
        fragmentCalculateBMR = new FragmentCalculateBMR();
        fragmentCalculateBMRResult = new FragmentCalculateBMRResult();

        loadBottomNavigationMenu();
        loadBottomFragmentChangerOnClickListener();

        startFragmentCaloriesCalculator();


        executorService.submit(internetStatus());
    }

    @Override
    protected void onStart() {
        super.onStart();

        new FirebaseConn().getUserData(firebaseUser.getUid(), (message, isExist, userData) -> {
            if (!isExist) { //if user doesn't exist
                new FirebaseConn().addNewUserData(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()), (msg, isSuccess) -> {
                    Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).setAnchorView(fab).show();
                    if (!isSuccess) { //if user data not completed correctly
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuItem_profile:
                startFragmentProfile();
                break;
            case R.id.menuItem_caloriesPlan:
                Toast.makeText(this, "Calories plan coming soon...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuItem_trainingPlan:
                Toast.makeText(this, "Training plan coming soon...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuItem_calculateBMR:
                startFragmentCalculateBMR();
                break;
            case R.id.menuItem_website:
                Toast.makeText(this, "Website Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuItem_logout: //log out user and bring him to login activity
                signOutAndStartLoginActivity();
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                changerFAB(eFragmentFlag);
                break;
            case R.id.btn_actionCalculator:
                if (eFragmentFlag != EFragmentFlag.ActionCalculator) {
                    startFragmentActionCalculator();
                }
                break;
            case R.id.btn_caloriesCalculator:
                if (eFragmentFlag != EFragmentFlag.CaloriesCalculator) {
                    startFragmentCaloriesCalculator();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) { //speech to text code
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    private void changerFAB(EFragmentFlag EFragmentFlag) {

        switch (EFragmentFlag) {
            case ActionCalculator:
            case CaloriesCalculator:
            case Profile:
                startFragmentSearch();
                break;

            case Search:
                FragmentSearch fragment = (FragmentSearch) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (fragment != null) {
                    fragment.saveFood();
                }
                break;

            case CalculateBMR:
                double[] result = fragmentCalculateBMR.calculate();
                if (result[0] != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putDoubleArray("result", result);
                    fragmentCalculateBMRResult.setArguments(bundle);
                    startFragmentCalculateBMRResult();
                }
                break;

            case CalculateBMRResult:
                saveUserBmrData(fragmentCalculateBMRResult.getResult());
                break;

            default:
                break;
        }
    }


    @SuppressLint("RestrictedApi")
    private void loadBottomNavigationMenu() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fab.setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void loadBottomFragmentChangerOnClickListener() {
        btn_caloriesCalculator.setOnClickListener(this);
        btn_actionCalculator.setOnClickListener(this);
    }

    private void startFragmentCaloriesCalculator() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentCaloriesCalculator()).commit();

        eFragmentFlag = EFragmentFlag.CaloriesCalculator;

        btn_caloriesCalculator.setImageResource(R.drawable.ic_hearth_primary_24dp);
        btn_actionCalculator.setImageResource(R.drawable.ic_burn_dark_24dp);
        fab.setImageResource(R.drawable.ic_add_light_24dp);

    }

    private void startFragmentActionCalculator() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentActionCalculator()).commit();

        eFragmentFlag = EFragmentFlag.ActionCalculator;

        btn_caloriesCalculator.setImageResource(R.drawable.ic_hearth_dark_24dp);
        btn_actionCalculator.setImageResource(R.drawable.ic_burn_primary_24dp);
        fab.setImageResource(R.drawable.ic_add_light_24dp);

    }

    private void setBottomNavButtonsToDark() {
        btn_caloriesCalculator.setImageResource(R.drawable.ic_hearth_dark_24dp);
        btn_actionCalculator.setImageResource(R.drawable.ic_burn_dark_24dp);
        fab.setImageResource(R.drawable.ic_add_light_24dp);

    }

    private void startFragmentSearch() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new FragmentSearch()).commit();
        setBottomNavButtonsToDark();
        eFragmentFlag = EFragmentFlag.Search;
        fab.setImageResource(R.drawable.ic_add_light_24dp);

    }

    private void startFragmentProfile() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new FragmentProfile()).addToBackStack("keyBack").commit();
        setBottomNavButtonsToDark();
        eFragmentFlag = EFragmentFlag.Profile;
        fab.setImageResource(R.drawable.ic_add_light_24dp);
    }

    private void startFragmentCalculateBMR() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentCalculateBMR).commit();
        setBottomNavButtonsToDark();
        eFragmentFlag = EFragmentFlag.CalculateBMR;
        fab.setImageResource(R.drawable.ic_hearth_light_24dp);
    }

    private void startFragmentCalculateBMRResult() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentCalculateBMRResult).commit();
        setBottomNavButtonsToDark();
        eFragmentFlag = EFragmentFlag.CalculateBMRResult;
        fab.setImageResource(R.drawable.ic_hearth_light_24dp);
    }

    @SuppressLint("PrivateResource")
    private void saveUserBmrData(double[] userDataTable) {
        new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle("Set new calories daily demand?")
                .setMessage("Are you sure do you want set new daily calories demand?")
                .setNegativeButton("Cancel", (dialog, which) -> startFragmentCaloriesCalculator())
                .setPositiveButton("Accept", (dialog, which) -> {
                    new FirebaseConn().addNewDailyDemanCalories(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()), Math.round(userDataTable[0]),
                            (message, isSuccess) -> {
                        if(isSuccess){
                            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAnchorView(fab).show();
                            startFragmentCaloriesCalculator();
                        }
                    });
                    new FirebaseConn().addNewUserWeight(FirebaseAuth.getInstance().getCurrentUser(), Math.round(userDataTable[1]), (message, isSuccess) -> {
                    });

                })
                .show();
    }

    private void signOutAndStartLoginActivity() {
        firebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private Runnable internetStatus() {
        ServiceNetwork network = new ServiceNetwork(this);

        return () -> {
            while (true) {
                if (!network.isInternetConnectionAvailable()) {
                    signOutAndStartLoginActivity();
                    break;
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
