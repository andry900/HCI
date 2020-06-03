package com.example.hci.ui.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hci.R;

public class PopUpFrequencyEvent extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_frequency_event);

        //TO CREATE THE PANEL
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.93), (int) (height * 0.6));

        //INITIALIZE ELEMENTS
        Button ok_button = findViewById(R.id.button_ok_daily_freq);
        Button back_button = findViewById(R.id.button_back_daily_freq);
        CheckBox checkBox_monday = findViewById(R.id.checkbox_monday);
        CheckBox checkBox_tuesday = findViewById(R.id.checkbox_tuesday);
        CheckBox checkBox_wednesday = findViewById(R.id.checkbox_wednesday);
        CheckBox checkBox_thursday = findViewById(R.id.checkbox_thursday);
        CheckBox checkBox_friday = findViewById(R.id.checkbox_friday);
        CheckBox checkBox_saturday = findViewById(R.id.checkbox_saturday);
        CheckBox checkBox_sunday = findViewById(R.id.checkbox_sunday);
        EditText editText_from_hour_event = findViewById(R.id.editText_from_hour_event);
        EditText editText_to_hour_event = findViewById(R.id.editText_to_hour_event);
        TextView textView_popup_frequency_event = findViewById(R.id.textView_popup_frequency_event);

        Intent intent = getIntent();
        String frequency_chosen = intent.getStringExtra("frequency_chosen");
        textView_popup_frequency_event.setText(frequency_chosen);

        //CLICKING ON BACK BUTTON
        back_button.setOnClickListener(v -> {
            finish();
        });

        //CLICKING OK BUTTON
        ok_button.setOnClickListener(v -> {
            if(!checkBox_monday.isChecked() && !checkBox_tuesday.isChecked() && !checkBox_wednesday.isChecked()
            && !checkBox_thursday.isChecked() && !checkBox_friday.isChecked() && !checkBox_saturday.isChecked() &&
            !checkBox_sunday.isChecked()){
                Toast toast= Toast.makeText(getApplicationContext(), "You must check at least one day", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else if(editText_from_hour_event.getText().toString().equals("") || editText_from_hour_event.getText().toString().equals(" ")
            || editText_to_hour_event.getText().toString().equals("") || editText_to_hour_event.getText().toString().equals(" ")){
                    Toast toast= Toast.makeText(getApplicationContext(), "You must fill the From and To values", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
            }
            else{
                InsertNewEvent.from_hour_event = editText_from_hour_event.getText().toString();
                InsertNewEvent.to_hour_event = editText_to_hour_event.getText().toString();

                if(checkBox_monday.isChecked()){
                    InsertNewEvent.days = "Monday, ";
                }
                if(checkBox_tuesday.isChecked()){
                    InsertNewEvent.days+="Tuesday, ";
                }
                if(checkBox_wednesday.isChecked()){
                    InsertNewEvent.days+="Wednesday, ";
                }
                if(checkBox_thursday.isChecked()){
                    InsertNewEvent.days+="Thursday, ";
                }
                if(checkBox_friday.isChecked()){
                    InsertNewEvent.days+="Friday, ";
                }
                if(checkBox_saturday.isChecked()){
                    InsertNewEvent.days+="Saturday, ";
                }
                if(checkBox_sunday.isChecked()){
                    InsertNewEvent.days+="Sunday, ";
                }

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }

        });
    }
}
