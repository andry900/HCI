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

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class InsertNewEvent extends Fragment {
    private DatePickerDialog picker;
    public static String days = "";
    public static String from_hour_event = "";
    public static String to_hour_event = "";
    private Button button_event_popup;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_new_event, container, false);
        AutoCompleteTextView auto_complete_event = root.findViewById(R.id.auto_complete_type_of_event);
        ImageButton calDuration_event = root.findViewById(R.id.calDuration_event);
        EditText editText_duration_event = root.findViewById(R.id.editText_duration_event);
        button_event_popup = root.findViewById(R.id.button_event_popup);
        ImageButton next_button_event = root.findViewById(R.id.nextBtn_event);
        ImageButton prev_button_event = root.findViewById(R.id.prevBtn_event);

        //FILTER RESULT BASED ON FREQUENCY CHOSEN
        Bundle bundle = getArguments();
        String frequency_chosen = Objects.requireNonNull(bundle).getString("frequency_chosen");
        // TODO:data scelta all'inizio vedi se ti serve --> String date_selected = Objects.requireNonNull(bundle).getString("date_selected");


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

            // date picker dialog
            picker = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) ->
                            editText_duration_event.setText(String.format(Locale.ITALY,  "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
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
            /*
            TODO: vai al prossimo fragment
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new EventsFragment(),"ShowUsefulNumber")
                    .addToBackStack(null)
                    .commit();

             */
        });

        //GO TO THE PREVIOUS FRAGMENT
        prev_button_event.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new EventsFragment(),"ShowUsefulNumber")
                    .addToBackStack(null)
                    .commit();
        });

        return root;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
              button_event_popup.setText("days: " + days + "\n" + "from: " + from_hour_event + " - to: " +to_hour_event);
              button_event_popup.setTextSize(10);
            }
        }
    }

}
