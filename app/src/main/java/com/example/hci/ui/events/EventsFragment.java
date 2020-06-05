package com.example.hci.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EventsFragment extends Fragment {
    public static ArrayList<String> defined_events = new ArrayList<>();
    public static String date_selected;
    public boolean event_selected = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_events, container, false);
        CalendarView calendarEvent = root.findViewById(R.id.calendar_event);
        Button add_event_button = root.findViewById(R.id.add_event_button);
        //Button edit_event_button = root.findViewById(R.id.edit_event_button);
        //Button delete_event_button = root.findViewById(R.id.delete_event_button);

        //TAKE THE DATE SELECTED BY THE USER
        calendarEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date_selected = dayOfMonth + "-" + month + "-" + year;
                event_selected = true;
            }
        });

        if(defined_events.size()!=0){
            for(int i=0;i<defined_events.size();i++){
                String[] result = defined_events.get(i).split("-");
                int day = Integer.parseInt(result[0]);
                int month = Integer.parseInt(result[1]);
                int year = Integer.parseInt(result[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                long milliTime = calendar.getTimeInMillis();
                calendarEvent.setDate(milliTime,true,true);
            }
        }

        //CLICK ON ADD BUTTON
        add_event_button.setOnClickListener(v -> {
            if (!event_selected) {
                date_selected = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            }
            Intent intent = new Intent(getContext(), ChooseTypeofEvent.class);
            startActivityForResult(intent, 1);

        });
        return root;
    }

    //TAKE RESULTS FROM CHOOSE TYPE OF EVENT ACTIVITY AND CALL THE INSERT NEW EVENT FRAGMENT
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String frequency_chosen = Objects.requireNonNull(data).getStringExtra("frequency_chosen");

                InsertNewEvent insertNewEvent = new InsertNewEvent();
                Bundle bundle = new Bundle();
                bundle.putString("frequency_chosen", frequency_chosen);
                bundle.putString("date_selected",date_selected);
                insertNewEvent.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, insertNewEvent,"InsertNewEvent")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
