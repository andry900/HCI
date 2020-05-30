package com.example.hci.ui.analytics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AnalyticsFragment extends Fragment {
    private DatePickerDialog picker;
    public static String starting_date, ending_date;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_analytics, container, false);

        EditText calendar_start, calendar_end;
        ImageButton calStart, calEnd;
        Button btnAnalytics;

        calendar_start = root.findViewById(R.id.editStart);
        calStart = root.findViewById(R.id.calStart);
        calendar_end = root.findViewById(R.id.editEnd);
        calEnd = root.findViewById(R.id.calEnd);
        btnAnalytics = root.findViewById(R.id.btnAnalytics);

        calendar_start.setText(starting_date);
        calendar_end.setText(ending_date);

        calStart.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            // date picker dialog
            picker = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) ->
                            calendar_start.setText(String.format(Locale.ITALY,  "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
            picker.show();
        });

        calEnd.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            // date picker dialog
            picker = new DatePickerDialog(requireActivity(),
                    (view, year1, monthOfYear, dayOfMonth) ->
                            calendar_end.setText(String.format(Locale.ITALY,  "%d/%d/%d", dayOfMonth, monthOfYear + 1, year1))
                    , year, month, day);
            picker.show();
        });

        btnAnalytics.setOnClickListener(v -> {
            starting_date = calendar_start.getText().toString();
            ending_date = calendar_end.getText().toString();

            if (!starting_date.equals("") && !ending_date.equals("")) {
                try {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(starting_date);
                    Date date2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(ending_date);

                    if (date1 != null) {
                        int result = date1.compareTo(date2);

                        if (result >= 0) {
                            Toast.makeText(getContext(), "Starting date must be before the ending date!", Toast.LENGTH_SHORT).show();
                        } else {
                            requireActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.nav_host_fragment, new HouseGraphs(), "fragment_house_graphs")
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "You must select starting and ending dates!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
