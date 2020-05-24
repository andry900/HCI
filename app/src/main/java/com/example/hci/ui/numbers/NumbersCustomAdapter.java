package com.example.hci.ui.numbers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hci.R;
import java.util.ArrayList;

public class NumbersCustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> numbers;
    private final ArrayList<String> icon_numbers;

    NumbersCustomAdapter(Activity context, ArrayList<String> numbers, ArrayList<String> icon_numbers){
        super(context, R.layout.number_item,numbers);
        this.context = context;
        this.numbers = numbers;
        this.icon_numbers = icon_numbers;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.number_item,null,true);

        ImageView icon_number= rowView.findViewById(R.id.image_itemListView_number);
        TextView number = rowView.findViewById(R.id.textView_itemListView_number);


        number.setText(numbers.get(position));
        icon_number.setImageResource(Integer.parseInt(icon_numbers.get(position)));

        return rowView;
    }
}
