package com.example.hci.ui.numbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.hci.R;

public class ChooseIconNumber extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_icon_number_activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.4));

        InsertNumberFragment insertNumberFragment = new InsertNumberFragment();

        ImageButton im = insertNumberFragment.getIc_general_image();

        ImageButton imgButton_ic_ambulance = findViewById(R.id.imageButton_ic_ambulance);
        imgButton_ic_ambulance.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_ambulance);
            finish();
        });

        ImageButton imgButton_ic_fire = findViewById(R.id.imageButton_ic_fire);
        imgButton_ic_fire.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_fire);
            finish();
        });

        ImageButton imgButton_ic_police = findViewById(R.id.imageButton_ic_police);
        imgButton_ic_police.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_police);
            finish();
        });

        ImageButton imgButton_ic_neighbour = findViewById(R.id.imageButton_ic_neighbour);
        imgButton_ic_neighbour.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_neighbour);
            finish();
        });

        ImageButton imgButton_ic_water = findViewById(R.id.imageButton_ic_water);
        imgButton_ic_water.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_water);
            finish();
        });

        ImageButton imgButton_ic_landlord = findViewById(R.id.imageButton_ic_landlord);
        imgButton_ic_landlord.setOnClickListener(v -> {
            insertNumberFragment.ic_general_image.setBackgroundResource(R.mipmap.ic_landlord);
            finish();
        });
    }
}
