package com.example.hci.ui.events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.hci.R;
import java.util.ArrayList;
import java.util.Objects;

public class GridViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> days;
    public static String dates = "";

    GridViewAdapter(Activity context, ArrayList<String> days) {
        super(context, R.layout.number_item, days);
        this.context = context;
        this.days = days;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = Objects.requireNonNull(inflater).inflate(R.layout.gridview_cell, null, true);

        TextView day = rowView.findViewById(R.id.textView_day);
        day.setText(days.get(position));

        day.setOnClickListener(v -> {
            day.setBackground(ContextCompat.getDrawable(context, R.drawable.cell_gridview_border));
            dates += day.getText().toString() + " - ";

        });


        return rowView;
    }
}
