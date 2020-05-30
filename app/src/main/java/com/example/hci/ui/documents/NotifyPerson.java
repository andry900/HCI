package com.example.hci.ui.documents;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hci.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.hci.ui.documents.DocumentsFragment.personsList;

public class NotifyPerson extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.notify_person, container, false);

        RecyclerView personRecycler = root.findViewById(R.id.person_list);

        if(personsList == null) {
            personsList = new ArrayList<Person>();
            personsList.add(new Person("Mario Rossi", "Roomates", null));
            personsList.add(new Person("Palma Alessandro", "Roomates", null));
            personsList.add(new Person("Bellia Andrea", "Landlord", null));
            personsList.add(new Person("Manzara Manuel", "Roomates", null));
            personsList.add(new Person("Santinelli Stefano", "Roomates", null));
        }
        personRecycler.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        personRecycler.setLayoutManager(lm);
        PersonAdapter mAdapter = new PersonAdapter(personsList);
        personRecycler.setAdapter(mAdapter);

        return root;
    }

    public static class Person {
        String name;
        String type;
        Bitmap img;

        public Person(String name, String type, Bitmap img) {
            this.name = name;
            this.type = type;
            this.img = img;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public Bitmap getImg() {
            return img;
        }
    }
}
