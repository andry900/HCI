package com.example.hci.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HouseGraphs extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_house_graphs, container, false);

        TextView frame_days = root.findViewById(R.id.frame_days);
        ImageButton rentalAnalytics = root.findViewById(R.id.histogram);
        ImageButton foodAnalytics = root.findViewById(R.id.pieChart);
        ImageButton billsAnalytics = root.findViewById(R.id.line_graph);

        TextView lab_rental = root.findViewById(R.id.rental_analytics);
        TextView lab_food = root.findViewById(R.id.food_analytics);
        TextView lab_bills = root.findViewById(R.id.bills_analytics);

        String format = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ITALY);

        try {
            Date dateObj1 = sdf.parse(AnalyticsFragment.starting_date);
            Date dateObj2 = sdf.parse(AnalyticsFragment.ending_date);

            if (dateObj1 != null && dateObj2 != null) {
                long diff = dateObj2.getTime() - dateObj1.getTime();
                int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                String text = diffDays + " Days";
                frame_days.setText(text);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        rentalAnalytics.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("rentalAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });

        lab_rental.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("rentalAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });

        foodAnalytics.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("foodAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });

        lab_food.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("foodAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });

        billsAnalytics.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("billsAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });

        lab_bills.setOnClickListener(v -> {
            AdvancedAnalytics analyticsFragment = AdvancedAnalytics
                    .newInstance("billsAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragment,"fragment_advanced_analytics")
                    .commit();
        });


        //TEST PART
        Button testBtn = root.findViewById(R.id.buttonTest);
        testBtn.setOnClickListener(v-> {
            AdvancedAnalyticsTest analyticsFragmentTest = AdvancedAnalyticsTest
                    .newInstance("billsAnalytics");

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, analyticsFragmentTest,"fragment_advanced_analytics_test")
                    .commit();
        });


        return root;
    }
}
