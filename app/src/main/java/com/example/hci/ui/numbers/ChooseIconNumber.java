package com.example.hci.ui.numbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageButton;
import com.example.hci.R;


public class ChooseIconNumber extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_icon_number_activity);

        //TO CREATE THE PANEL
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.4));

        //IMAGE BUTTONS
        ImageButton imgButton_ic_ambulance = findViewById(R.id.imageButton_ic_ambulance);
        ImageButton imgButton_ic_fire = findViewById(R.id.imageButton_ic_fire);
        ImageButton imgButton_ic_police = findViewById(R.id.imageButton_ic_police);
        ImageButton imgButton_ic_water = findViewById(R.id.imageButton_ic_water);
        ImageButton imgButton_ic_neighbour = findViewById(R.id.imageButton_ic_neighbour);
        ImageButton imgButton_ic_landlord = findViewById(R.id.imageButton_ic_landlord);

        //CLICK ON AMBULANCE ICON
        imgButton_ic_ambulance.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_ambulance.setTag("imgButton_ic_ambulance");
            returnIntent.putExtra("result",imgButton_ic_ambulance.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        //CLICK ON FIRE ICON
        imgButton_ic_fire.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_fire.setTag("imgButton_ic_fire");
            returnIntent.putExtra("result",imgButton_ic_fire.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        //CLICK ON POLICE ICON
        imgButton_ic_police.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_police.setTag("imgButton_ic_police");
            returnIntent.putExtra("result",imgButton_ic_police.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        //CLICK ON NEIGHBOUR ICON
        imgButton_ic_neighbour.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_neighbour.setTag("imgButton_ic_neighbour");
            returnIntent.putExtra("result",imgButton_ic_neighbour.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        //CLICK ON WATER ICON
        imgButton_ic_water.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_water.setTag("imgButton_ic_water");
            returnIntent.putExtra("result",imgButton_ic_water.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        //CLICK ON LANDLORD ICON
        imgButton_ic_landlord.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            imgButton_ic_landlord.setTag("imgButton_ic_landlord");
            returnIntent.putExtra("result",imgButton_ic_landlord.getTag().toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });
    }
}
