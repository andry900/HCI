package com.example.hci.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hci.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdvancedAnalyticsTest extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced_analytics_test, container, false);

        TextView rentalAnalytics = root.findViewById(R.id.rental_analytics);
        TextView foodAnalytics = root.findViewById(R.id.food_analytics);
        TextView billsAnalytics = root.findViewById(R.id.bills_analytics);

        ImageView histogram = root.findViewById(R.id.histogram);
        ImageView pieChart = root.findViewById(R.id.pieChart);
        ImageView lineGraph = root.findViewById(R.id.line_graph);

        ImageView up_arrow = root.findViewById(R.id.up_arrow);
        ImageView down_arrow = root.findViewById(R.id.down_arrow);

        TextView average_expenses = root.findViewById(R.id.average_expenses);
        TextView average_data = root.findViewById(R.id.average_data);
        TextView median_expenses = root.findViewById(R.id.median_expenses);
        TextView median_data = root.findViewById(R.id.median_data);
        TextView variance_expenses = root.findViewById(R.id.variance_expenses);
        TextView variance_data = root.findViewById(R.id.variance_data);

        TextView plus_12_percentage = root.findViewById(R.id.plus_12_percentage);
        TextView minus_6_percentage = root.findViewById(R.id.minus_6_percentage);
        TextView minus_3_percentage = root.findViewById(R.id.minus_3_percentage);

        RadioGroup radioData = root.findViewById(R.id.radioData);
        RadioGroup radioVisualization = root.findViewById(R.id.radioVisualization);

        Bundle bundle = getArguments();
        String graph_type = Objects.requireNonNull(bundle).getString("graph_type");

        if (graph_type != null) {
            if (graph_type.equals("rentalAnalytics")) {
                rentalAnalytics.setVisibility(View.VISIBLE);
                histogram.setVisibility(View.VISIBLE);
                up_arrow.setVisibility(View.VISIBLE);
                plus_12_percentage.setVisibility(View.VISIBLE);

                foodAnalytics.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                billsAnalytics.setVisibility(View.GONE);
                lineGraph.setVisibility(View.GONE);
                down_arrow.setVisibility(View.GONE);
                minus_6_percentage.setVisibility(View.GONE);
                minus_3_percentage.setVisibility(View.GONE);

                radioVisualization.check(radioVisualization.getChildAt(0).getId());
            } else if (graph_type.equals("foodAnalytics")) {
                foodAnalytics.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.VISIBLE);
                down_arrow.setVisibility(View.VISIBLE);
                minus_6_percentage.setVisibility(View.VISIBLE);

                rentalAnalytics.setVisibility(View.GONE);
                histogram.setVisibility(View.GONE);
                billsAnalytics.setVisibility(View.GONE);
                lineGraph.setVisibility(View.GONE);
                up_arrow.setVisibility(View.GONE);
                plus_12_percentage.setVisibility(View.GONE);
                minus_3_percentage.setVisibility(View.GONE);

                radioVisualization.check(radioVisualization.getChildAt(1).getId());
            } else {    //billsAnalytics
                billsAnalytics.setVisibility(View.VISIBLE);
                lineGraph.setVisibility(View.VISIBLE);
                down_arrow.setVisibility(View.VISIBLE);
                minus_3_percentage.setVisibility(View.VISIBLE);

                foodAnalytics.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                rentalAnalytics.setVisibility(View.GONE);
                histogram.setVisibility(View.GONE);
                up_arrow.setVisibility(View.GONE);
                plus_12_percentage.setVisibility(View.GONE);
                minus_6_percentage.setVisibility(View.GONE);

                radioVisualization.check(radioVisualization.getChildAt(2).getId());
            }
        }

        radioData.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.avg) {    //Average
                average_expenses.setVisibility(View.VISIBLE);
                average_data.setVisibility(View.VISIBLE);

                median_expenses.setVisibility(View.GONE);
                median_data.setVisibility(View.GONE);
                variance_expenses.setVisibility(View.GONE);
                variance_data.setVisibility(View.GONE);
            } else if (checkedId == R.id.med) {     //Median
                median_expenses.setVisibility(View.VISIBLE);
                median_data.setVisibility(View.VISIBLE);

                average_expenses.setVisibility(View.GONE);
                average_data.setVisibility(View.GONE);
                variance_expenses.setVisibility(View.GONE);
                variance_data.setVisibility(View.GONE);
            } else {    //Variance
                variance_expenses.setVisibility(View.VISIBLE);
                variance_data.setVisibility(View.VISIBLE);

                median_expenses.setVisibility(View.GONE);
                median_data.setVisibility(View.GONE);
                average_expenses.setVisibility(View.GONE);
                average_data.setVisibility(View.GONE);
            }
        });

        radioVisualization.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.hist) {    //Histogram
                histogram.setVisibility(View.VISIBLE);

                pieChart.setVisibility(View.GONE);
                lineGraph.setVisibility(View.GONE);
            } else if (checkedId == R.id.pie) {     //Pie Chart
                pieChart.setVisibility(View.VISIBLE);

                histogram.setVisibility(View.GONE);
                lineGraph.setVisibility(View.GONE);
            } else {    //Line Graph
                lineGraph.setVisibility(View.VISIBLE);

                pieChart.setVisibility(View.GONE);
                histogram.setVisibility(View.GONE);
            }
        });

        return root;
    }

    static AdvancedAnalyticsTest newInstance(String graph_type) {
        Bundle arguments = new Bundle();
        AdvancedAnalyticsTest advancedAnalytics = new AdvancedAnalyticsTest();

        arguments.putString("graph_type", graph_type);
        advancedAnalytics.setArguments(arguments);

        return advancedAnalytics;
    }

}
