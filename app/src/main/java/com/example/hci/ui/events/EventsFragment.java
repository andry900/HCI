package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import com.example.hci.ui.documents.DocumentsFragment;
import com.example.hci.ui.home.HomeFragment;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;

public class EventsFragment extends Fragment implements CalendarPickerController {
    public static ArrayList<String> defined_events = new ArrayList<>();
    public static String date_selected;
    public boolean event_selected = false;
    public static long start_date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Events");

        View root = inflater.inflate(R.layout.fragment_events, container, false);

        Button add_event_button = root.findViewById(R.id.add_event_button);
        AgendaCalendarView mEventCalendarView = root.findViewById(R.id.calendar_event);
        //Button edit_event_button = root.findViewById(R.id.edit_event_button);
        //Button delete_event_button = root.findViewById(R.id.delete_event_button);
        DocumentsFragment.personsList = null;

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        mEventCalendarView.init(HomeFragment.eventList, minDate, maxDate, Locale.getDefault(), this);

        //CLICK ON ADD BUTTON
        add_event_button.setOnClickListener(v -> {
            if (!event_selected) {
                date_selected = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                start_date = Calendar.getInstance().getTimeInMillis();
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

    @Override
    public void onDaySelected(DayItem dayItem) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd-MM-yyyy");
        date_selected = simpleDate.format(dayItem.getDate());
        event_selected = true;
        Date sdt = null;
        try {
            sdt = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY).parse(date_selected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        start_date = Objects.requireNonNull(sdt).getTime();
        }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (requireActivity().findViewById(R.id.event_agenda_bar) != null) {
            ((TextView) requireActivity().findViewById(R.id.event_agenda_bar)).setText(
                    (Objects.requireNonNull(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())))
                            .toUpperCase());
        }
    }
}
