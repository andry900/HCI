package com.example.hci.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hci.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EventsFragment extends Fragment {
    public CalendarView calendarEvent;
    public String date_selected;
    public boolean event_selected = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_events, container, false);
        calendarEvent = root.findViewById(R.id.calendar_event);
        Button add_event_button = root.findViewById(R.id.add_event_button);
        Button edit_event_button = root.findViewById(R.id.edit_event_button);
        Button delete_event_button = root.findViewById(R.id.delete_event_button);

        calendarEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date_selected = dayOfMonth + "/" + month + "/" + year;
                event_selected = true;
            }
        });

        add_event_button.setOnClickListener(v -> {
            if (!event_selected) {
                date_selected = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            }
            Intent intent = new Intent(getContext(), ChooseTypeofEvent.class);
            startActivityForResult(intent, 1);

        });
        return root;
    }

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
                        .replace(R.id.nav_host_fragment, insertNewEvent,"ShowUsefulNumber")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
