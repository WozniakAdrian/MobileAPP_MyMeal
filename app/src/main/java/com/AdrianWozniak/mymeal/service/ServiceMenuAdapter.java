package com.AdrianWozniak.mymeal.service;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.AdrianWozniak.mymeal.R;
import com.AdrianWozniak.mymeal.model.FoodItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Class that create own Array Adapter with food item for list menu;
 */
public class ServiceMenuAdapter extends ArrayAdapter<FoodItem> {

    private Context context;

    private List<FoodItem> foodItems;

    public ServiceMenuAdapter(Context c, List<FoodItem> foodItems){
        super(c, R.layout.list_row, R.id.txtTitle, foodItems);
        this.context = c;
        this.foodItems = foodItems;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.list_row, parent, false);

        ImageView imageView = row.findViewById(R.id.imgAvatar);
        TextView txtTitle = row.findViewById(R.id.txtTitle);
        TextView txtServingQuantity = row.findViewById(R.id.txtServingQuantity);
        TextView txtServingUnit = row.findViewById(R.id.txtServingUnit);
        TextView txtCalories = row.findViewById(R.id.txtCalories);
        TextView txtServingGrams = row.findViewById(R.id.txtServingGrams);

        Picasso.get().load(foodItems.get(position).getPhotoUrl()).into(imageView);
        txtTitle.setText(foodItems.get(position).getFood_name().toUpperCase());
        txtServingQuantity.setText(String.valueOf(foodItems.get(position).getServing_quantity()));
        txtServingUnit.setText(String.valueOf(foodItems.get(position).getServing_unit()));
        txtCalories.setText(foodItems.get(position).getCalories() + " CAL");
        txtServingGrams.setText((int)foodItems.get(position).getServing_weight_grams() + " g");

        Log.e("Adapter: ", "position " + position + " inflater " + inflater + " item: " + foodItems.get(position).toString());

        return row;
    }
}

