package com.example.hci.ui.numbers;

import android.app.Activity;
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
    static ArrayList<String> icon_numbers;
    static ArrayList<String> name_numbers;
    static ArrayList<String> comments;
    static ArrayList<String> numbers;
    static ListView numbers_listView;
    static NumbersCustomAdapter numbersCustomAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_numbers, container, false);
        numbers_listView = root.findViewById(R.id.numbers_listView);
        Button insertNumber_button = root.findViewById(R.id.insertNumber_button);

        //TO FILL THE LISTVIEW THE FIRST TIME
        if (name_numbers == null && icon_numbers == null) {
            fill_listViewNumbers();
            numbersCustomAdapter = new NumbersCustomAdapter((Activity) getContext(), name_numbers, icon_numbers);
        }

        //SET THE ADAPTER FOR THE LISTVIEW
        numbers_listView.setAdapter(numbersCustomAdapter);

        //CLICK ON ISTVIEW'S ITEMS
        numbers_listView.setOnItemClickListener((parent, view, position, id) -> {
            String name_number = numbers_listView.getItemAtPosition(position).toString();
            String icon_number = icon_numbers.get(position);
            String number = numbers.get(position);
            String comment = comments.get(position);

            //PASS INFO TO THE NEXT FRAGMENT
            ShowUsefulNumber showUsefulNumber = new ShowUsefulNumber();
            Bundle bundle = new Bundle();
            bundle.putString("name_number",name_number);
            bundle.putString("icon_number",icon_number);
            bundle.putString("number",number);
            bundle.putString("comment",comment);
            bundle.putString("position",String.valueOf(position));
            showUsefulNumber.setArguments(bundle);

            //GO TO THE NEXT FRAGMENT
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, showUsefulNumber,"ShowUsefulNumber")
                    .addToBackStack(null)
                    .commit();
        });

        //CLICK ON INSERT BUTTON
        insertNumber_button.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new InsertNumberFragment(),"InsertNumberFragment")
                .addToBackStack(null)
                .commit());

        return root;
    }

    //TO FILL THE LISTVIEW
    private static void fill_listViewNumbers() {
        icon_numbers = new ArrayList<>();
        name_numbers = new ArrayList<>();
        comments = new ArrayList<>();
        numbers = new ArrayList<>();

        icon_numbers.add(String.valueOf(R.mipmap.ic_ambulance));
        name_numbers.add("Ambulance");
        comments.add("it is relative to the closest hospistal");
        numbers.add("118");

        icon_numbers.add(String.valueOf(R.mipmap.ic_police));
        name_numbers.add("Police");
        comments.add("it is relative to the closest police station");
        numbers.add("113");
    }
}
