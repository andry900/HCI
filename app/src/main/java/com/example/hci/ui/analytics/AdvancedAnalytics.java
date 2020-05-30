package com.example.hci.ui.analytics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.util.Objects;

public class AdvancedAnalytics extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced_analytics, container, false);

        Bundle bundle = getArguments();
        String graph_type = Objects.requireNonNull(bundle).getString("graph_type");

        return root;
    }

    static AdvancedAnalytics newInstance(String graph_type) {//, String starting_date, String ending_date) {
        Bundle arguments = new Bundle();
        AdvancedAnalytics advancedAnalytics = new AdvancedAnalytics();

        arguments.putString("graph_type", graph_type);
        advancedAnalytics.setArguments(arguments);

        return advancedAnalytics;
    }
}
