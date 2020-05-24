package com.example.hci.ui.numbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hci.R;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_numbers, container, false);
        ListView numbers_listView = root.findViewById(R.id.numbers_listView);
        Button insertNumber_button = root.findViewById(R.id.insertNumber_button);
        insertNumber_button.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new InsertNumberFragment(),"InsertNumberFragment")
                    .commit();
        });
        fill_listViewNumbers(numbers_listView);

        return root;
    }


    private void fill_listViewNumbers(ListView numbers_listView){
        ArrayList<String> icon_numbers = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        icon_numbers.add(String.valueOf(R.mipmap.ic_ambulance));
        icon_numbers.add(String.valueOf(R.mipmap.ic_landlord));
        icon_numbers.add(String.valueOf(R.mipmap.ic_water));
        icon_numbers.add(String.valueOf(R.mipmap.ic_police));
        icon_numbers.add(String.valueOf(R.mipmap.ic_fire));
        icon_numbers.add(String.valueOf(R.mipmap.ic_neighbour));
        numbers.add("Ambulance");
        numbers.add("Landlord");
        numbers.add("Plumber");
        numbers.add("Police");
        numbers.add("Fireman");
        numbers.add("Neighbour");
        NumbersCustomAdapter numbersCustomAdapter = new NumbersCustomAdapter((Activity)getContext(),numbers,icon_numbers);
        numbers_listView.setAdapter(numbersCustomAdapter);
    }
}
