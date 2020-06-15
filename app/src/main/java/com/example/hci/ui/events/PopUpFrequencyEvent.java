package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hci.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PopUpFrequencyEvent extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_frequency_event);
        Context mContext = this;

        //TO CREATE THE PANEL
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.93), (int) (height * 0.75));

        //INITIALIZE ELEMENTS
        Button ok_button = findViewById(R.id.button_ok_daily_freq);
        Button back_button = findViewById(R.id.button_back_daily_freq);
        LinearLayout layout_mon_tue_wed = findViewById(R.id.layout_mon_tue_wed);
        LinearLayout layout_thu_fri_sat = findViewById(R.id.layout_thu_fri_sat);
        LinearLayout layout_sunday = findViewById(R.id.layout_sunday);
        LinearLayout layout_calendar_freq_popup = findViewById(R.id.layout_calendar_freq_popup);

        CheckBox checkBox_monday = findViewById(R.id.checkbox_monday);
        CheckBox checkBox_tuesday = findViewById(R.id.checkbox_tuesday);
        CheckBox checkBox_wednesday = findViewById(R.id.checkbox_wednesday);
        CheckBox checkBox_thursday = findViewById(R.id.checkbox_thursday);
        CheckBox checkBox_friday = findViewById(R.id.checkbox_friday);
        CheckBox checkBox_saturday = findViewById(R.id.checkbox_saturday);
        CheckBox checkBox_sunday = findViewById(R.id.checkbox_sunday);

        TextView editText_from_hour_event = findViewById(R.id.editText_from_hour_event);
        TextView editText_to_hour_event = findViewById(R.id.editText_to_hour_event);
        TextView textView_popup_frequency_event = findViewById(R.id.textView_popup_frequency_event);

        GridView calendarEvent_freq_popup = findViewById(R.id.calendar_event_freq_popup);
        ImageButton starting_time_btn = findViewById(R.id.img_btn_start_time);
        ImageButton ending_time_btn = findViewById(R.id.img_btn_end_time);
        LinearLayout layout_vertical_from_to = findViewById(R.id.layout_vertical_from_to);

        //GET THE FREQUENCY CHOSEN BY THE USER FROM PREVIOUS FRAGMENT
        Intent intent = getIntent();
        String frequency_chosen = intent.getStringExtra("frequency_chosen");
        textView_popup_frequency_event.setText("Choose the " + frequency_chosen + " frequency");

        GridViewAdapter.dates="";

        //FILTER RESULTS BASED ON THE FREQUENCY CHOSEN
        if (Objects.equals(frequency_chosen, "daily") || Objects.equals(frequency_chosen, "no periodic")  ) {
            getWindow().setLayout((int) (width * 0.93), (int) (height * 0.4));
            textView_popup_frequency_event.setText("Choose the time for the " + frequency_chosen + " event");

            layout_mon_tue_wed.setVisibility(View.INVISIBLE);
            layout_thu_fri_sat.setVisibility(View.INVISIBLE);
            layout_sunday.setVisibility(View.INVISIBLE);
            layout_calendar_freq_popup.setVisibility(View.INVISIBLE);
        }

        else if (Objects.equals(frequency_chosen, "weekly")) {
            getWindow().setLayout((int) (width * 0.93), (int) (height * 0.65));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(200, 780, 0, 0);
            layout_vertical_from_to.setLayoutParams(params);

            layout_calendar_freq_popup.setVisibility(View.INVISIBLE);
            layout_mon_tue_wed.setVisibility(View.VISIBLE);
            layout_thu_fri_sat.setVisibility(View.VISIBLE);
            layout_sunday.setVisibility(View.VISIBLE);

        }

        else {
            getWindow().setLayout((int) (width * 0.93), (int) (height * 0.79));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(200, 1000, 0, 0);
            layout_vertical_from_to.setLayoutParams(params);

            layout_mon_tue_wed.setVisibility(View.INVISIBLE);
            layout_thu_fri_sat.setVisibility(View.INVISIBLE);
            layout_sunday.setVisibility(View.INVISIBLE);
            layout_calendar_freq_popup.setVisibility(View.VISIBLE);

           ArrayList<String> days = new ArrayList<>();

            for (int i = 0; i < 31; i++) {
               days.add(String.valueOf(i + 1));
            }
            GridViewAdapter adapter = new GridViewAdapter(this,days);
            calendarEvent_freq_popup.setAdapter(adapter);
        }

        //CLICK ON IMAGE BUTTON STARTING TIME
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        starting_time_btn.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, (view, hourOfDay, minute1) ->
                    editText_from_hour_event.setText(hourOfDay + ":" + minute1),
                    hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
            timePickerDialog.show();
        });

        //CLICK ON TEXT_VIEW STARTING TIME
        editText_from_hour_event.setOnClickListener(v -> starting_time_btn.callOnClick());

        //CLICK ON TEXT_VIEW ENDING TIME
        editText_to_hour_event.setOnClickListener(v -> ending_time_btn.callOnClick());

        //CLICK ON IMAGE BUTTON ENDING TIME
        ending_time_btn.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, (view, hourOfDay, minute12) ->
                    editText_to_hour_event.setText(hourOfDay + ":" + minute12),
                    hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
            timePickerDialog.show();
        });

        //CLICKING ON BACK BUTTON
        back_button.setOnClickListener(v -> finish());

        //CLICKING OK BUTTON
        ok_button.setOnClickListener(v -> {
            if (Objects.equals(frequency_chosen, "weekly")) {
                if (!checkBox_monday.isChecked() && !checkBox_tuesday.isChecked() && !checkBox_wednesday.isChecked()
                        && !checkBox_thursday.isChecked() && !checkBox_friday.isChecked() && !checkBox_saturday.isChecked() &&
                        !checkBox_sunday.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You must check at least one day", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (editText_from_hour_event.getText().toString().equals("") || editText_from_hour_event.getText().toString().equals(" ")
                        || editText_to_hour_event.getText().toString().equals("") || editText_to_hour_event.getText().toString().equals(" ")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You must fill the starting and ending times", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    InsertNewEvent.from_hour_event = editText_from_hour_event.getText().toString();
                    InsertNewEvent.to_hour_event = editText_to_hour_event.getText().toString();

                    if (checkBox_monday.isChecked()) {
                        InsertNewEvent.days = "Monday, ";
                    }
                    if (checkBox_tuesday.isChecked()) {
                        InsertNewEvent.days += "Tuesday, ";
                    }
                    if (checkBox_wednesday.isChecked()) {
                        InsertNewEvent.days += "Wednesday, ";
                    }
                    if (checkBox_thursday.isChecked()) {
                        InsertNewEvent.days += "Thursday, ";
                    }
                    if (checkBox_friday.isChecked()) {
                        InsertNewEvent.days += "Friday, ";
                    }
                    if (checkBox_saturday.isChecked()) {
                        InsertNewEvent.days += "Saturday, ";
                    }
                    if (checkBox_sunday.isChecked()) {
                        InsertNewEvent.days += "Sunday, ";
                    }

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }

            if (Objects.equals(frequency_chosen, "daily") || Objects.equals(frequency_chosen, "no periodic") || Objects.equals(frequency_chosen, "monthly")) {
                if (editText_from_hour_event.getText().toString().equals("") || editText_from_hour_event.getText().toString().equals(" ")
                        || editText_to_hour_event.getText().toString().equals("") || editText_to_hour_event.getText().toString().equals(" ")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You must fill the starting and ending times", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    for (int i=0; i < GridViewAdapter.dates_selected_deselected.size(); i++){
                        GridViewAdapter.dates += GridViewAdapter.dates_selected_deselected.get(i) + " - ";
                    }
                    InsertNewEvent.from_hour_event = editText_from_hour_event.getText().toString();
                    InsertNewEvent.to_hour_event = editText_to_hour_event.getText().toString();

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }
}
