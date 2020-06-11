package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import com.example.hci.R;

public class ChooseTypeofEvent extends Activity {
    Button daily_button;
    Button weekly_button;
    Button monthly_button;
    Button yearly_button;
    Button no_periodic_button;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type_event_activity);

        //TO CREATE THE PANEL
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.6));

        //BUTTONS
        daily_button = findViewById(R.id.daily_event_button);
        weekly_button = findViewById(R.id.weekly_event_button);
        monthly_button = findViewById(R.id.monthly_event_button);
        no_periodic_button = findViewById(R.id.no_periodic_event_button);

        onClickSpecificButton(daily_button);
        onClickSpecificButton(weekly_button);
        onClickSpecificButton(monthly_button);
        onClickSpecificButton(no_periodic_button);

    }

    //PASS THE FREQUENCY CHOSEN BACK TO THE EVENTS FRAGMENT
    private void onClickSpecificButton(Button button) {
        button.setOnClickListener(v -> {
            String frequency = "";
            if (daily_button.equals(button)) {
                frequency = "daily";
            } else if (weekly_button.equals(button)) {
                frequency = "weekly";
            } else if (monthly_button.equals(button)) {
                frequency = "monthly";
            } else if (no_periodic_button.equals(button)) {
                frequency = "no periodic";
            }
            Intent returnIntent = new Intent();
            returnIntent.putExtra("frequency_chosen",frequency);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        });
    }
}
