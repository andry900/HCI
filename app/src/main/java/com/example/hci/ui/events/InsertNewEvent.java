package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import com.example.hci.ui.documents.NotifyPerson;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;

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
        if (frequency_chosen.equals("monthly") || frequency_chosen.equals("yearly") ) {
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
                            editText_duration_event.setText(String.format(Locale.ITALY,  "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
            editText_duration_event.setError(null);
            picker.show();
        });

        //CALL THE POPUP WHEN BUTTON IS CLICKED
        button_event_popup.setOnClickListener(v -> {
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
            if (editText_description_event.getText().toString().equals("") || editText_description_event.getText().toString().equals(" ")) {
                editText_description_event.setError("Enter a description");
            }
            if (editText_autocomplete_event.getText().toString().equals("") || editText_autocomplete_event.getText().toString().equals(" ")) {
                editText_autocomplete_event.setError("Enter a type of event");
            }

            if (editText_autocomplete_event.getError() == null && editText_description_event.getError() == null && editText_duration_event.getError() == null) {
                if (!button_event_popup.getText().equals(getResources().getString(R.string.enter_days_and_hours))) {
                    NotifyPerson notifyPerson = new NotifyPerson();
                    Bundle arguments = new Bundle();
                    arguments.putString("insert_new_event", "insert new event");
                    arguments.putString("frequency_chosen",frequency_chosen);
                    notifyPerson.setArguments(arguments);

                    duration = editText_duration_event.getText().toString();
                    type_of_event = editText_autocomplete_event.getText().toString();
                    description = editText_description_event.getText().toString();
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, notifyPerson,"NotifyPerson")
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast toast = Toast.makeText(getContext(), "You must enter days and hours!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

        });

        //GO TO THE PREVIOUS FRAGMENT
        prev_button_event.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EventsFragment(),"EventsFragment")
                .addToBackStack(null)
                .commit());
        
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
                    button_event_popup.setText("days: -" + days + "\n" + "from: " + from_hour_event + " -- to: " +to_hour_event);
                }
                if (frequency_chosen.equals("daily") || frequency_chosen.equals("no periodic") ) {
                    button_event_popup.setText("from: " + from_hour_event + " - to: " +to_hour_event);
                }
                if (frequency_chosen.equals("monthly") || frequency_chosen.equals("yearly") ) {
                    button_event_popup.setText("days: -" + GridViewAdapter.dates + "\n" +"from: " + from_hour_event + " -- to: " +to_hour_event);
                }
              button_event_popup.setTextSize(10);
            }
        }
    }
}
