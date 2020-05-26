package com.example.hci.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HouseGraphs extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_house_graphs, container, false);

        String starting_date, ending_date;
        TextView frame_days = root.findViewById(R.id.frame_days);

        Bundle bundle = getArguments();
        starting_date = Objects.requireNonNull(bundle).getString("starting_date");
        ending_date = Objects.requireNonNull(bundle).getString("ending_date");

        String format = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ITALY);

        try {
            assert starting_date != null;
            assert ending_date != null;
            Date dateObj1 = sdf.parse(starting_date);
            Date dateObj2 = sdf.parse(ending_date);

            assert dateObj2 != null;
            assert dateObj1 != null;
            long diff = dateObj2.getTime() - dateObj1.getTime();
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            String text = diffDays + " Days";
            frame_days.setText(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }

    static HouseGraphs newInstance(String starting_date, String ending_date) {
        Bundle arguments = new Bundle();
        HouseGraphs houseGraphs = new HouseGraphs();

        arguments.putString("starting_date", starting_date);
        arguments.putString("ending_date", ending_date);
        houseGraphs.setArguments(arguments);

        return houseGraphs;
    }
}
