package com.example.hci.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements CalendarPickerController {
    public static Bitmap home_profile;
    public static String homeName, homeSurname;
    public static Integer homeAge;
    public static List<CalendarEvent> eventList = null;

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

        if(eventList == null){
            eventList = new ArrayList<>();
            mockList(eventList);
        }

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);

        return root;
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Stefano back to spain",
                "DESCRIPTION", "PLACE",
                ContextCompat.getColor(getContext(), R.color.blue_selected),
                startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 =  new BaseCalendarEvent("Manzo clean kitchen",
                "DESCRIPTION", "PLACE",
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                startTime2, endTime2, false);
        eventList.add(event2);

    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getActivity().findViewById(R.id.agenda_bar) != null) {
            ((TextView)getActivity().findViewById(R.id.agenda_bar)).setText(
                    (calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                            .toUpperCase());
        }
    }
}
