package com.example.hci.ui.documents;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
        sb.append("RECAP: \n -");

        if (Objects.equals(requireArguments().getString("notify_person"), "notify person")) {
            String frequency_chosen = requireArguments().getString("frequency_chosen");
            sb.append("Frequency chosen: ").append(frequency_chosen);
            sb.append("\n -");
            if (Objects.equals(frequency_chosen, "monthly") || Objects.equals(frequency_chosen, "yearly")) {
                sb.append("Days: ").append(GridViewAdapter.dates);
                sb.append("\n -");
            }
            if (Objects.equals(frequency_chosen, "weekly")) {
                sb.append("Days: ").append(InsertNewEvent.days);
                sb.append("\n -");
            }

            sb.append("Starting time: ").append(InsertNewEvent.from_hour_event);
            sb.append("\n -");
            sb.append("Ending time: ").append(InsertNewEvent.to_hour_event);
            sb.append("\n -");
            sb.append("Duration of the event: ").append(InsertNewEvent.duration);
            sb.append("\n -");
            sb.append("Type of event: ").append(InsertNewEvent.type_of_event);
            sb.append("\n -");
            sb.append("Description: ").append(InsertNewEvent.description);

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
                            "FROM"+InsertNewEvent.from_hour_event+
                                    "TO"+InsertNewEvent.to_hour_event,
                            ContextCompat.getColor(requireContext(), R.color.noPeriodicColor),
                            startTime, endTime, false);
                    (HomeFragment.eventList).add(newEvent);
                }
                if (Objects.equals(frequency_chosen, "yearly")) {
                    String[] tmpArr = GridViewAdapter.dates.split(" - ");
                    for (int i=0; i<=10; i++) {
                        for (String s : tmpArr) {
                            Calendar startTime = Calendar.getInstance();
                            startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s));
                            startTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                            startTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                            startTime.add(Calendar.YEAR, i);
                            Calendar endTime = Calendar.getInstance();
                            endTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s));
                            endTime.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                            endTime.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                            endTime.add(Calendar.YEAR, i);
                            BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                                    InsertNewEvent.description,
                                    "FROM" + InsertNewEvent.from_hour_event +
                                            "TO" + InsertNewEvent.to_hour_event,
                                    ContextCompat.getColor(requireContext(), R.color.colorAccent),
                                    startTime, endTime, false);
                            (HomeFragment.eventList).add(newEvent);
                        }
                    }
                }
                if (Objects.equals(frequency_chosen, "monthly")) {
                    String[] tmpArr = GridViewAdapter.dates.split(" - ");
                    for (int i=0; i<=10; i++) {
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
                                    "FROM" + InsertNewEvent.from_hour_event +
                                            "TO" + InsertNewEvent.to_hour_event,
                                    ContextCompat.getColor(requireContext(), R.color.monthlyColor),
                                    startTime, endTime, false);
                            (HomeFragment.eventList).add(newEvent);
                        }
                    }
                }
                if (Objects.equals(frequency_chosen, "daily")) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(EventsFragment.start_date);
                    Calendar endTime = Calendar.getInstance();
                    try {
                        endTime.setTime(Objects.requireNonNull
                                (new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(InsertNewEvent.duration)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endTime.add(Calendar.DAY_OF_WEEK, 1);
                    BaseCalendarEvent newEvent = new BaseCalendarEvent(InsertNewEvent.type_of_event,
                            InsertNewEvent.description,
                            "FROM"+InsertNewEvent.from_hour_event+
                                    "TO"+InsertNewEvent.to_hour_event,
                            ContextCompat.getColor(requireContext(), R.color.dailyColor),
                            startTime, endTime, true);
                    (HomeFragment.eventList).add(newEvent);
                }
                if (Objects.equals(frequency_chosen, "weekly")) {
                    String[] tmpArr = InsertNewEvent.days.split(", ");
                    for (int i=0; i<=30; i++) {
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
                                    "FROM" + InsertNewEvent.from_hour_event +
                                            "TO" + InsertNewEvent.to_hour_event,
                                    ContextCompat.getColor(requireContext(), R.color.weeklyColor),
                                    startTime, endTime, false);
                            (HomeFragment.eventList).add(newEvent);
                        }
                    }
                }

                EventsFragment.defined_events.add(EventsFragment.date_selected);
                InsertNewEvent.button_event_popup.setText(getResources().getString(R.string.enter_days_and_hours));
                InsertNewEvent.from_hour_event = "";
                InsertNewEvent.to_hour_event = "";
                InsertNewEvent.days = "";
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

            sb.append( recapBundle.getString("name"));
            sb.append("\n -");

            if (!Objects.equals(recapBundle.getString("amount"), "")) {
                amount = Float.valueOf((Objects.requireNonNull(recapBundle.getString("amount"))));
                sb.append(amount);
                sb.append("\n -");
            }
            if (!Objects.equals(recapBundle.getString("utility"), "")) {
                sb.append(recapBundle.getString("utility"));
                sb.append("\n -");
            }
            sb.append(recapBundle.getString("status"));

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

        sb.append("\n ");
        sb.append("\n ");
        sb.append("PERSONS TO NOTIFY:");

        ArrayList<String> persNotify = Objects.requireNonNull(recapBundle).getStringArrayList("persons");
        if (Objects.requireNonNull(persNotify).size()> 0) {
            for (int p = 0; p < (persNotify.size()); p++) {
                sb.append("\n -");
                sb.append(persNotify.get(p));

            }
        }

        recapText.setText(sb.toString());

        change.setOnClickListener(v ->{
                        requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().popBackStack();});

        return root;
    }
}
