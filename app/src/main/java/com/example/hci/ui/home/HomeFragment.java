package com.example.hci.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.NavigationActivity;
import com.example.hci.R;
import com.example.hci.ui.events.EventsFragment;
import com.example.hci.ui.profile.ProfileFragment;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment implements CalendarPickerController {
    public static Bitmap home_profile;
    public static String homeName, homeSurname;
    public static Integer homeAge;
    public static List<CalendarEvent> eventList ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String newName;

        ImageView profile_pic = root.findViewById(R.id.profile_pic);
        TextView user_summary = root.findViewById(R.id.user_summary);
        AgendaCalendarView mAgendaCalendarView = root.findViewById(R.id.agenda_calendar_view);

        if (home_profile != null) {
            profile_pic.setImageBitmap(home_profile);
        }

        if (homeName != null && homeSurname != null && homeAge != null) {
            if (!homeName.equals("") || !homeSurname.equals("") || !String.valueOf(homeAge).equals("")) {
                newName = homeName + " " + homeSurname + "\n" + homeAge + "Y\nRoommate in house in:\n Via Italia, 157";
            } else {
                newName = "Marco Rossi\n23Y\nRoommate in house in:\nVia Italia, 157";
            }
        } else {
            newName = "Marco Rossi\n23Y\nRoommate in house in:\nVia Italia, 157";
        }

        user_summary.setText(newName);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        if (eventList == null) {
            eventList = new ArrayList<>();
        }

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);

        profile_pic.setOnClickListener(v-> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new ProfileFragment(), "fragment_profile")
                    .addToBackStack(null)
                    .commit();

            NavigationActivity.navigationView.getMenu().getItem(0).setChecked(false);
            NavigationActivity.navigationView.getMenu().getItem(1).setChecked(true);
        });

        user_summary.setOnClickListener(v-> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new ProfileFragment(), "fragment_profile")
                    .addToBackStack(null)
                    .commit();

            NavigationActivity.navigationView.getMenu().getItem(0).setChecked(false);
            NavigationActivity.navigationView.getMenu().getItem(1).setChecked(true);
        });

        return root;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EventsFragment(), "fragment_events")
                .addToBackStack(null)
                .commit();

        NavigationActivity.navigationView.getMenu().getItem(0).setChecked(false);
        NavigationActivity.navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EventsFragment(), "fragment_events")
                .addToBackStack(null)
                .commit();

        NavigationActivity.navigationView.getMenu().getItem(0).setChecked(false);
        NavigationActivity.navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (requireActivity().findViewById(R.id.agenda_bar) != null) {
            ((TextView) requireActivity().findViewById(R.id.agenda_bar)).setText(
                    (Objects.requireNonNull(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())))
                            .toUpperCase());
        }
    }
}
