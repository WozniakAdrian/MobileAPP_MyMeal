package com.AdrianWozniak.mymeal.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.model.FoodItem;
import com.AdrianWozniak.mymeal.repository.firebase.FirebaseConn;
import com.AdrianWozniak.mymeal.service.ServiceMenuList;
import com.AdrianWozniak.mymeal.service.ServiceNutritionix;
import com.AdrianWozniak.mymeal.service.ServiceSnackbar;
import com.AdrianWozniak.mymeal.service.ServiceSoftwareKeyboard;
import com.AdrianWozniak.mymeal.service.ServiceSpeechToText;
import com.AdrianWozniak.mymeal.service.ServiceTimeCounter;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FragmentSearch extends Fragment implements View.OnClickListener {

    //FAB
    private FloatingActionButton fab;

    //Nutrition API
    private ServiceNutritionix serviceNutritionix;

    //List
    private ServiceMenuList serviceMenuList;
    private List<FoodItem> foodItemList;
    private SwipeMenuListView swipeMenu;

    //Fragment Views:
    private ImageButton speechToTextBTN;
    private ImageButton searchBTN;
    private ImageButton cameraBTN;
    private ImageButton clearListBTN;
    private EditText queryET;
    private TextView resultTV;

    //FragmentFlags
    private EFragmentFlag EFragmentFlag;
    private ImageButton caloriesBTN;
    private ImageButton activityBTN;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceNutritionix = new ServiceNutritionix(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_search, container, false);

        fab = RootView.findViewById(R.id.fab);

        swipeMenu = RootView.findViewById(R.id.searchFrag_NutrionixRecivedDataLV);
        resultTV = RootView.findViewById(R.id.searchFrag_summaryResultTV);
        speechToTextBTN = RootView.findViewById(R.id.searchFrag_speachToTextIBTN);
        searchBTN = RootView.findViewById(R.id.searchFrag_searchIBTN);
        cameraBTN = RootView.findViewById(R.id.searchFrag_cameraIBTN);

        queryET = RootView.findViewById(R.id.searchFrag_queryET);



        speechToTextBTN.setOnClickListener(this);
        searchBTN.setOnClickListener(this);
        cameraBTN.setOnClickListener(this);


        EFragmentFlag = com.AdrianWozniak.mymeal.activity.fragments.EFragmentFlag.CaloriesCalculator;

        try {
            serviceMenuList = new ServiceMenuList(getActivity(), swipeMenu, resultTV);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchFrag_searchIBTN:
                searchData("");
                ServiceSoftwareKeyboard.hide(getActivity());
                break;
            case R.id.searchFrag_cameraIBTN: //todo hard coded string for testing app
                queryET.setText("rice, 100g ice cream, 2 slice bread, 1 piece cake, 300g sausage, 500ml beer, 5 big tomato, 2 lemons, 330ml cocacola");
                ServiceSnackbar.show("Barcode scan coming soon.", getActivity());
                break;
            case R.id.searchFrag_speachToTextIBTN:
                ServiceSpeechToText.startSpeechToTextActivityForResult(getActivity(), getActivity());
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10: //speech to text response
                if (resultCode == -1 && data != null) {
                    ArrayList<String> str = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    queryET.setText(str.get(0));
                    searchData(str.get(0));
                }
                break;
        }
    }

    /**
     * Function gets query from edit text and decide what operation should be done.
     */
    public void searchData(String query) {
        if(query.isEmpty()){
            query = queryET.getText().toString();
        }

        if (query.isEmpty() || query == null) {
            ServiceSnackbar.show("Type amount of calories or what you eat", getActivity());
        } else {
            queryManagement(query);
        }
    }

    /**
     * Function decide if user pass through edit text number of calories or query for API
     * @param query
     */
    private void queryManagement(String query){
        if (query.matches("\\d+")) {  //if query contains only digits
            if(foodItemList ==null){
                foodItemList = new ArrayList<>();
            }
            if(!foodItemList.isEmpty()){
                foodItemList.clear();
            }
            foodItemList.add(new FoodItem( Double.parseDouble(query)));
            this.resultTV.setText(query + "CAL");
        } else {
            searchInNutritionApiBy(query);
        }
    }

    /**
     * Function call api and indicate item list
     * @param query
     */
    public void searchInNutritionApiBy(String query) {
        try {
            serviceNutritionix.callAPI(query);
            new Thread(() -> {
                while (true) {
                    if (serviceNutritionix.isArrayEnabled) {
                        serviceMenuList.hide();
                        foodItemList = serviceNutritionix.getFoodItemArrayList();
                        break;
                    }
                    if (serviceNutritionix.isResponseError()) {
                        ServiceSnackbar.show("We couldn't match any of your foods", getActivity());
                        break;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                serviceMenuList.show(foodItemList);
            }).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * Function save food into firebase
     */
    public void saveFood() {
        ServiceTimeCounter timer = new ServiceTimeCounter(getActivity());

        if (foodItemList == null) {
            ServiceSnackbar.show("You must have at least one meal in list", getActivity());
        } else {
            new FirebaseConn().addNewFoodItems(FirebaseAuth.getInstance().getCurrentUser(), foodItemList, (msg, isCompleted, date) -> {
                if (isCompleted) {
                    serviceMenuList.hide();
                    foodItemList.clear();
                    timer.setNewStartTime();
                    ServiceSnackbar.set(msg, getActivity()).setAction("UNDO", v -> {
                        new FirebaseConn().deleteItemInGivenDate(FirebaseAuth.getInstance().getCurrentUser(), date, (message, isSuccess) -> {
                            ServiceSnackbar.show(message, getActivity());
                        });
                    }).show();

                }
            });
        }
    }



}
