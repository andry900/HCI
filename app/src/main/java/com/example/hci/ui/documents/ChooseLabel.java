package com.example.hci.ui.documents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.example.hci.R;

public class ChooseLabel extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_label_activity);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        findViewById(R.id.contractBtn).setOnClickListener(v -> {
            Intent myIntent = new Intent(ChooseLabel.this, InsertNewDocument.class);
            myIntent.putExtra("label", "CONTRACT");
            ChooseLabel.this.startActivity(myIntent);
        });

        findViewById(R.id.utilityBtn).setOnClickListener(v -> {
            Intent myIntent = new Intent(ChooseLabel.this, InsertNewDocument.class);
            myIntent.putExtra("label", "UTILITY");
            ChooseLabel.this.startActivity(myIntent);
        });

        findViewById(R.id.othersBtn).setOnClickListener(v -> {
            Intent myIntent = new Intent(ChooseLabel.this, InsertNewDocument.class);
            myIntent.putExtra("label", "OTHERS");
            ChooseLabel.this.startActivity(myIntent);
        });

    }
}
