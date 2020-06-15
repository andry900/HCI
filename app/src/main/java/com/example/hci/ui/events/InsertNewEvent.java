package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import com.example.hci.ui.documents.NotifyPerson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;
import static androidx.appcompat.app.AlertDialog.*;
import static com.example.hci.ui.events.EventsFragment.date_selected;

public class InsertNewEvent extends Fragment {
    private DatePickerDialog picker;
    public static String days = "";
    public static String from_hour_event = "";
    public static String to_hour_event = "";
    public static String duration = "";
    public static String type_of_event = "";
    public static String description = "";
    public static Button button_event_popup;
    private String frequency_chosen;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_new_event, container, false);
        @SuppressLint("CutPasteId") AutoCompleteTextView auto_complete_event = root.findViewById(R.id.auto_complete_type_of_event);
        ImageButton calDuration_event = root.findViewById(R.id.calDuration_event);

        ImageButton calStart_event = root.findViewById(R.id.calStart_event);
        EditText editText_start_event = root.findViewById(R.id.editText_start_event);

        EditText editText_duration_event = root.findViewById(R.id.editText_duration_event);
        EditText editText_description_event = root.findViewById(R.id.editText_description_event);
        @SuppressLint("CutPasteId") EditText editText_autocomplete_event = root.findViewById(R.id.auto_complete_type_of_event);

        button_event_popup = root.findViewById(R.id.button_event_popup);
        ImageButton next_button_event = root.findViewById(R.id.nextBtn_event);
        ImageButton prev_button_event = root.findViewById(R.id.prevBtn_event);

        //FILTER RESULT BASED ON FREQUENCY CHOSEN
        Bundle bundle = getArguments();
        frequency_chosen = Objects.requireNonNull(bundle).getString("frequency_chosen");

        if (Objects.requireNonNull(frequency_chosen).equals("weekly")) {
            if (!days.equals("") && !from_hour_event.equals("") && !to_hour_event.equals("")) {
                button_event_popup.setText("days: " + days + "\n" + "from: " + from_hour_event + " - to: " + to_hour_event);
                button_event_popup.setTextSize(10);
            }
        }
        if (frequency_chosen.equals("daily") || frequency_chosen.equals("no periodic") ) {
            if (!from_hour_event.equals("") && !to_hour_event.equals("")) {
                button_event_popup.setText("from: " + from_hour_event + " - to: " + to_hour_event);
                button_event_popup.setTextSize(10);
            }
        }
        if (frequency_chosen.equals("monthly")) {
            if (!from_hour_event.equals("") && !to_hour_event.equals("") &&  !GridViewAdapter.dates.equals("")) {
                button_event_popup.setText("days: " + GridViewAdapter.dates + "\n" + "from: " + from_hour_event + " - to: " + to_hour_event);
                button_event_popup.setTextSize(10);
            }
        }

        //SET THE ADAPTER FOR THE TYPE OF EVENT
        String[] possible_events = new String[]{
                "cleaning house","gym hours","emergency","payment deadline"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),android.R.layout.simple_list_item_1, possible_events);
        auto_complete_event.setAdapter(adapter);


        //SET THE DURATION USING PICKER
        calDuration_event.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            //DATE PICKER DIALOG
            picker = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) ->
                            editText_duration_event.setText(String.format(Locale.ITALY,
                                    "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
            editText_duration_event.setError(null);
            picker.show();
        });

        //SET THE START DATE USING PICKER
        calStart_event.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            //DATE PICKER DIALOG
            picker = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) ->
                            editText_start_event.setText(String.format(Locale.ITALY,
                                    "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
            editText_start_event.setError(null);
            picker.show();
        });

        //CALL THE POPUP WHEN BUTTON IS CLICKED
        button_event_popup.setOnClickListener(v -> {
            button_event_popup.setError(null);
            days = "";
            Intent intent = new Intent(getContext(), PopUpFrequencyEvent.class);
            intent.putExtra("frequency_chosen",frequency_chosen);
            startActivityForResult(intent, 1);
        });

        //GO TO THE NEXT FRAGMENT
        next_button_event.setOnClickListener(v -> {
            if (editText_duration_event.getText().toString().equals("") || editText_duration_event.getText().toString().equals(" ")) {
                editText_duration_event.setError("Enter a duration");
            }
            if (editText_start_event.getText().toString().equals("") || editText_start_event.getText().toString().equals(" ")) {
                editText_start_event.setError("Enter a start");
            }
            if (editText_autocomplete_event.getText().toString().equals("") || editText_autocomplete_event.getText().toString().equals(" ")) {
                editText_autocomplete_event.setError("Enter a type of event");
            }
            if (button_event_popup.getText().toString().equals(getResources().getString(R.string.enter_days_and_hours))) {
                button_event_popup.setError("Enter dates and hours");
            }

            if (editText_autocomplete_event.getError() == null && editText_duration_event.getError() == null && button_event_popup.getError() == null) {
                if (!button_event_popup.getText().equals(getResources().getString(R.string.enter_days_and_hours))) {
                    NotifyPerson notifyPerson = new NotifyPerson();
                    Bundle arguments = new Bundle();
                    arguments.putString("insert_new_event", "insert new event");
                    arguments.putString("frequency_chosen",frequency_chosen);
                    notifyPerson.setArguments(arguments);

                    duration = editText_duration_event.getText().toString();
                    date_selected = editText_start_event.getText().toString();
                    @SuppressLint("SimpleDateFormat")
                    Date sdt = null;
                    try {
                        sdt = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(date_selected);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    EventsFragment.start_date = Objects.requireNonNull(sdt).getTime();
                    date_selected.replace("/","-");
                    type_of_event = editText_autocomplete_event.getText().toString();
                    description = editText_description_event.getText().toString();
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, notifyPerson,"NotifyPerson")
                            .addToBackStack(null)
                            .commit();
                }
            }

        });

        //GO TO THE PREVIOUS FRAGMENT
        prev_button_event.setOnClickListener(v -> requireActivity().onBackPressed());
        return root;
    }

    //TAKE RESULT FROM POPUP FREQUENCY EVENT ACTIVITY
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (frequency_chosen.equals("weekly")) {
                    button_event_popup.setText("days: " + days + "\n" + "from: " + from_hour_event + " -- to: " +to_hour_event);
                }
                if (frequency_chosen.equals("daily") || frequency_chosen.equals("no periodic") ) {
                    button_event_popup.setText("from: " + from_hour_event + " - to: " +to_hour_event);
                }
                if (frequency_chosen.equals("monthly")) {
                    button_event_popup.setText("days: " + GridViewAdapter.dates + "\n" +"from: " + from_hour_event + " -- to: " +to_hour_event);
                }
              button_event_popup.setTextSize(10);
            }
        }
    }
}
