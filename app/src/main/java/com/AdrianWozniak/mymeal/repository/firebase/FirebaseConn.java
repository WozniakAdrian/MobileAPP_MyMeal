package com.AdrianWozniak.mymeal.repository.firebase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.AdrianWozniak.mymeal.model.FoodItem;
import com.AdrianWozniak.mymeal.model.UserData;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;


/**
 * Class represents repository for Firebase realtime db
 */
public class FirebaseConn extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    /**
     * Constructor to initialize instance and reference for Firebase db
     */
    public FirebaseConn() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("MyMealDB");
    }

    /**
     * Function to add new user into database
     *
     * @param user                    FirebaseUser
     * @param iFirebaseUserDataStatus Interface data status with message [str] and isSuccess [bool]
     */
    public void addNewUserData(FirebaseUser user, final IFirebaseUserDataStatus iFirebaseUserDataStatus) {
        databaseReference.child(user.getUid()).setValue(
                new UserData(user.getUid(), user.getEmail()))
                .addOnFailureListener(e -> iFirebaseUserDataStatus.status("User Data creation failure, please try login again. Error: " + e.getMessage(), false))
                .addOnCompleteListener(e -> iFirebaseUserDataStatus.status("The user was created correctly.", true));
    }


    /**
     * Function set onDataChange listener, everytime data change listener push data into interface
     *
     * @param uid             - actual user id
     * @param iUserDataStatus - interface with data
     */
    public void getUserData(final String uid, final IUserDataStatus iUserDataStatus) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.child(uid).getValue(UserData.class);
                if (userData == null) {
                    iUserDataStatus.status("Failure to read data", false, null);
                } else {
                    iUserDataStatus.status("Data read correctly", true, userData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iUserDataStatus.status("Failure to read data. Error: " + databaseError.getMessage(), false, null);
            }
        });
    }


    /**
     * Function to add to database list of FoodItems
     *
     * @param user                        - firebase user
     * @param foodItemList                - food item array list
     * @param iConsumedCaloriesDataStatus - interface with data
     */
    public void addNewFoodItems(FirebaseUser user, List<FoodItem> foodItemList, final IConsumedCaloriesDataStatus iConsumedCaloriesDataStatus) {
        String date = new Date().toString();
        databaseReference.child(user.getUid()).child("consumedCalories").child(date).setValue(foodItemList)
                .addOnFailureListener((e) -> iConsumedCaloriesDataStatus.status("Ups, we can't add your food. Please try again", false, date))
                .addOnSuccessListener((v) -> iConsumedCaloriesDataStatus.status("Food added correctly", true, date));
    }

    /**
     * Function to delete item in given date
     *
     * @param user                                - firebase user
     * @param date                                - given date to delete
     * @param iDeleteCurrentlyFoodItemAddedStatus - interface with data
     */
    public void deleteItemInGivenDate(FirebaseUser user, String date, final IDeleteCurrentlyFoodItemAddedStatus iDeleteCurrentlyFoodItemAddedStatus) {
        databaseReference.child(user.getUid()).child("consumedCalories").child(date).removeValue()
                .addOnSuccessListener((v) -> iDeleteCurrentlyFoodItemAddedStatus.status("Food deleted correctly", true))
                .addOnFailureListener((e) -> iDeleteCurrentlyFoodItemAddedStatus.status("Ups, somethink wrong, yours food is still there", false));
    }


    /**
     * Function to add daily demand calories
     *
     * @param user                   - firebase user
     * @param dailyDemand            - double daily demand
     * @param iDailyDemandDataStatus - interface with data
     */
    public void addNewDailyDemanCalories(FirebaseUser user, double dailyDemand, final IDailyDemandDataStatus iDailyDemandDataStatus) {
        databaseReference.child(user.getUid()).child("dailyDemandCalories").child(new Date().toString()).setValue(dailyDemand)
                .addOnFailureListener((e) -> iDailyDemandDataStatus.status("Ups, we can't add your daily demand. Please try again", false))
                .addOnSuccessListener((v) -> iDailyDemandDataStatus.status("Your calories daily demand updated correctly", true));
    }


    /**
     * Function to change name
     *
     * @param user                  - firebase user
     * @param displayName           - string name
     * @param iChangeNameDataStatus - interface with data
     */
    public void changeDisplayName(FirebaseUser user, String displayName, final IChangeNameDataStatus iChangeNameDataStatus) {

        databaseReference.child(user.getUid()).child("displayName").setValue(displayName)
                .addOnFailureListener((e) -> iChangeNameDataStatus.status("Ups, we can't change yours name. Please try again", false))
                .addOnSuccessListener((v) -> iChangeNameDataStatus.status("Your name changed correctly", true));
    }


    /**
     * Function to change city
     *
     * @param user                  - firebase user
     * @param city                  - string city
     * @param iChangeCityDataStatus - interface with data result
     */
    public void changeCity(FirebaseUser user, String city, final IChangeCityDataStatus iChangeCityDataStatus) {

        databaseReference.child(user.getUid()).child("city").setValue(city)
                .addOnFailureListener((e) -> iChangeCityDataStatus.status("Ups, we can't change yours city. Please try again", false))
                .addOnSuccessListener((v) -> iChangeCityDataStatus.status("Your city changed correctly", true));
    }


    /**
     * Function to save user weight
     *
     * @param user
     * @param weight
     * @param iWeightStatus
     */
    public void addNewUserWeight(FirebaseUser user, double weight, final IWeightStatus iWeightStatus) {

        databaseReference.child(user.getUid()).child("weight").child(new Date().toString()).setValue(weight)
                .addOnFailureListener((e) -> iWeightStatus.status("Ups, we can't add your weight. Please try again", false))
                .addOnSuccessListener((v) -> iWeightStatus.status("Your weight updated correctly", true));
    }


}

