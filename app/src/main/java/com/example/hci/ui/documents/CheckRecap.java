package com.example.hci.ui.documents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hci.R;
import com.example.hci.ui.events.EventsFragment;
import com.example.hci.ui.events.GridViewAdapter;
import com.example.hci.ui.events.InsertNewEvent;
import com.example.hci.ui.home.HomeFragment;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.hci.ui.documents.DocumentsFragment.personsList;
import static com.example.hci.ui.documents.DocumentsFragment.savedDocs;

public class CheckRecap extends Fragment {
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recap_fragment, container, false);
        Bundle recapBundle = getArguments();
        TextView recapText = root.findViewById(R.id.recapMultiLine);
        Button confirm = root.findViewById(R.id.confirm_btn);
        Button change = root.findViewById(R.id.change_btn);
        StringBuilder sb = new StringBuilder();
        sb.append("<b>RECAP:</b>");

        if (Objects.equals(requireArguments().getString("notify_person"), "notify person")) {
            String frequency_chosen = requireArguments().getString("frequency_chosen");
            sb.append(" <br /> &nbsp  - <b><i>Frequency chosen: </i></b>").append(frequency_chosen);
            if (Objects.equals(frequency_chosen, "monthly") || Objects.equals(frequency_chosen, "yearly")) {
                sb.append("<br /> &nbsp - <b><i>Days: </i></b>").append(GridViewAdapter.dates);
            }
            if (Objects.equals(frequency_chosen, "weekly")) {
                sb.append("<br /> &nbsp - <b><i>Days: </i></b>").append(InsertNewEvent.days);
            }

            sb.append("<br /> &nbsp -<b><i> Starting time: </i></b>").append(InsertNewEvent.from_hour_event);
            sb.append("<br /> &nbsp -<b><i> Ending time: </i></b>").append(InsertNewEvent.to_hour_event);
            sb.append("<br /> &nbsp -<b><i> Duration of the event: </i></b>").append(InsertNewEvent.duration);
            sb.append("<br /> &nbsp -<b><i> Type of event: </i></b>").append(InsertNewEvent.type_of_event);
            sb.append("<br /> &nbsp -<b><i> Description: </i></b> ").append(InsertNewEvent.description);

            confirm.setOnClickListener(v -> {

                LayoutInflater toastInfl = getLayoutInflater();
                View layout = toastInfl.inflate(R.layout.insertion_toast_layout,
                        requireView().findViewById(R.id.custom_toast_container));

                Toast t = new Toast(getContext());
                TextView tv = layout.findViewById(R.id.txtvw);
                tv.setText("Event has been correctly inserted and persons notified!!");
                t.setDuration(LENGTH_LONG);
                t.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                t.setView(layout);
                t.show();

                if (Objects.equals(frequency_chosen, "no periodic")) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(EventsFragment.start_date);
                    Calendar endTime = Calendar.getInstance();
                    endTime.setTimeInMillis(EventsFragment.start_date);
                    BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                            InsertNewEvent.description,
                            "FROM "+InsertNewEvent.from_hour_event+
                                    "  TO "+InsertNewEvent.to_hour_event,
                            ContextCompat.getColor(requireContext(), R.color.noPeriodicColor),
                            startTime, endTime, false);
                    (HomeFragment.eventList).add(newEvent);
                }

                if (Objects.equals(frequency_chosen, "monthly")) {
                    String[] tmpArr = GridViewAdapter.dates.split(" - ");
                    int endMonth;
                    int startMonth;
                    try {
                        Calendar myCal = new GregorianCalendar();
                        myCal.setTime(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY)
                                .parse(EventsFragment.date_selected)));
                        startMonth = myCal.get(Calendar.MONTH);
                        Calendar myCal2 = new GregorianCalendar();
                        myCal2.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                                .parse(InsertNewEvent.duration)));
                        endMonth = myCal2.get(Calendar.MONTH);

                        for (int i=0; i <= (endMonth-startMonth); i++) {
                            for (String s : tmpArr) {
                                Calendar startTime = Calendar.getInstance();
                                startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s));
                                startTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                                startTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                startTime.add(Calendar.MONTH, i);
                                Calendar endTime = Calendar.getInstance();
                                endTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s));
                                endTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                                endTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                endTime.add(Calendar.MONTH, i);

                                BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                                        InsertNewEvent.description,
                                        "FROM " + InsertNewEvent.from_hour_event +
                                                "  TO " + InsertNewEvent.to_hour_event,
                                        ContextCompat.getColor(requireContext(), R.color.monthlyColor),
                                        startTime, endTime, false);
                                (HomeFragment.eventList).add(newEvent);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (Objects.equals(frequency_chosen, "daily")) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(EventsFragment.start_date);
                    Calendar endTime = Calendar.getInstance();

                    try {
                        endTime.setTime(Objects.requireNonNull
                                (new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                                        .parse(InsertNewEvent.duration)));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endTime.add(Calendar.DAY_OF_WEEK, 1);
                    BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                            InsertNewEvent.description,
                            "FROM "+InsertNewEvent.from_hour_event+
                                    "  TO "+InsertNewEvent.to_hour_event,
                            ContextCompat.getColor(requireContext(), R.color.dailyColor),
                            startTime, endTime, true);
                    (HomeFragment.eventList).add(newEvent);
                }

                if (Objects.equals(frequency_chosen, "weekly")) {
                    String[] tmpArr = InsertNewEvent.days.split(", ");

                    int startWeek, startMonth;
                    int endWeek, endMonth;
                    try {
                        Calendar myCal = new GregorianCalendar();
                        myCal.setTime(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY)
                                .parse(EventsFragment.date_selected)));
                        startMonth = myCal.get(Calendar.MONTH);
                        startWeek = myCal.get(Calendar.WEEK_OF_MONTH);
                        Calendar myCal2 = new GregorianCalendar();
                        myCal2.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                                .parse(InsertNewEvent.duration)));
                        endMonth = myCal2.get(Calendar.MONTH);
                        endWeek = myCal2.get(Calendar.WEEK_OF_MONTH);

                        for (int i=0; i<= ((endMonth-startMonth)*4)+(endWeek-startWeek); i++) {
                            for (String s : tmpArr) {
                                int day = 0;
                                if (s.equals("Sunday"))
                                    day = Calendar.SUNDAY;
                                if (s.equals("Monday"))
                                    day = Calendar.MONDAY;
                                if (s.equals("Tuesday"))
                                    day = Calendar.TUESDAY;
                                if (s.equals("Wednesday"))
                                    day = Calendar.WEDNESDAY;
                                if (s.equals("Thursday"))
                                    day = Calendar.THURSDAY;
                                if (s.equals("Friday"))
                                    day = Calendar.FRIDAY;
                                if (s.equals("Saturday"))
                                    day = Calendar.SATURDAY;

                                Calendar startTime = Calendar.getInstance();
                                startTime.set(Calendar.DAY_OF_WEEK, day);
                                startTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                                startTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                startTime.add(Calendar.WEEK_OF_MONTH, i);
                                Calendar endTime = Calendar.getInstance();
                                endTime.set(Calendar.DAY_OF_WEEK, day);
                                endTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                                endTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                                endTime.add(Calendar.WEEK_OF_MONTH, i);

                                BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                                        InsertNewEvent.description,
                                        "FROM " + InsertNewEvent.from_hour_event +
                                                "  TO " + InsertNewEvent.to_hour_event,
                                        ContextCompat.getColor(requireContext(), R.color.weeklyColor),
                                        startTime, endTime, false);
                                (HomeFragment.eventList).add(newEvent);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                EventsFragment.defined_events.add(EventsFragment.date_selected);
                InsertNewEvent.button_event_popup.setText(getResources().getString(R.string.enter_days_and_hours));
                InsertNewEvent.from_hour_event = "";
                InsertNewEvent.to_hour_event = "";
                InsertNewEvent.days = "";
                GridViewAdapter.dates_selected_deselected.clear();
                personsList = null;
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new EventsFragment(),"fragment_events")
                        .addToBackStack(null)
                        .commit();
            });
        }
        else {
            Float amount = null;
            String utility = Objects.requireNonNull(recapBundle).getString("utility");
            if (Objects.equals(utility, "")) {
                utility = null;
            }

            sb.append("<br />&nbsp - <b><i> Name: </i></b>").append(recapBundle.getString("name"));

            if (!Objects.equals(recapBundle.getString("amount"), " Euro")) {
                amount = Float.parseFloat(
                        Objects.requireNonNull(recapBundle.getString("amount")).replace("Euro", ""));
                sb.append("<br />&nbsp - <i><b>Amount: </i></b>");
                sb.append(recapBundle.getString("amount"));
            }
            if (!Objects.equals(recapBundle.getString("utility"), "")) {
                sb.append("<br />&nbsp - <i><b>Utility: </i></b>");
                sb.append(recapBundle.getString("utility"));
            }
            if (!Objects.equals(recapBundle.getString("status"), "")) {
                sb.append("<br />&nbsp - <i><b>Status: </i></b>");
                sb.append(recapBundle.getString("status"));
            }

            Float finalAmount = amount;
            String finalUtility = utility;
            confirm.setOnClickListener(v -> {
                DocumentsFragment.Document newDoc = new DocumentsFragment.Document(
                        recapBundle.getString("name"), recapBundle.getString("label"),
                        recapBundle.getString("status"),recapBundle.getParcelable("image"),
                        finalUtility, finalAmount);

                savedDocs.add(newDoc);
                LayoutInflater toastInfl = getLayoutInflater();
                View layout = toastInfl.inflate(R.layout.insertion_toast_layout,
                        requireView().findViewById(R.id.custom_toast_container));

                Toast t = new Toast(getContext());
                TextView tv = layout.findViewById(R.id.txtvw);
                tv.setText("Document has been correctly inserted and persons notified!!");
                t.setDuration(LENGTH_LONG);
                t.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                t.setView(layout);
                t.show();
                personsList = null;
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new DocumentsFragment(),"fragment_documents")
                        .addToBackStack(null)
                        .commit();
            });
        }

        sb.append("<br />");
        sb.append("<br />");
        sb.append("<b>PERSONS TO NOTIFY:</b>");

        ArrayList<String> persNotify = Objects.requireNonNull(recapBundle).getStringArrayList("persons");
        if (Objects.requireNonNull(persNotify).size()> 0) {
            for (int p = 0; p < (persNotify.size()); p++) {
                sb.append("<br /> &nbsp - ");
                sb.append(persNotify.get(p));

            }
        }

        recapText.setText(Html.fromHtml(sb.toString()));

        change.setOnClickListener(v ->{
                        requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().popBackStack();});

        return root;
    }
}
