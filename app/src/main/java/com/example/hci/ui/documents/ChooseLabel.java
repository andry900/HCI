package com.example.hci.ui.documents;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.example.hci.R;

import androidx.fragment.app.FragmentActivity;

public class ChooseLabel extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_label_activity);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.contractBtn).setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("label","CONTRACT");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        findViewById(R.id.utilityBtn).setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("label","UTILITY");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

        findViewById(R.id.othersBtn).setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("label","OTHERS");
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });

    }
}
