package com.example.hci.ui.documents;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.hci.R;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.hci.ui.documents.DocumentsFragment.personsList;

public class NotifyPerson extends Fragment {


    private ArrayList<String> selectedPersons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.notify_person, container, false);

        RecyclerView personRecycler = root.findViewById(R.id.person_list);
        ImageButton next = root.findViewById(R.id.nextBtn);
        ImageButton prev = root.findViewById(R.id.prevBtn);
        
        Bundle recapBundle = getArguments();
        selectedPersons = new ArrayList<String>();

        if(personsList == null) {
            personsList = new ArrayList<Person>();
            personsList.add(new Person("Mario Rossi", "Roomates", null,false));
            personsList.add(new Person("Palma Alessandro", "Roomates", null,false));
            personsList.add(new Person("Bellia Andrea", "Landlord", null,false));
            personsList.add(new Person("Manzara Manuel", "Roomates", null,false));
            personsList.add(new Person("Santinelli Stefano", "Roomates", null,false));
        }
        personRecycler.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        personRecycler.setLayoutManager(lm);
        PersonAdapter mAdapter = new PersonAdapter(personsList);
        personRecycler.setAdapter(mAdapter);

        next.setOnClickListener(v ->{
          assert recapBundle != null;
          recapBundle.putStringArrayList("persons", selectedPersons);
          //start recap frgament
        });

        RecyclerItemClickSupport.addTo(personRecycler).setOnItemClickListener(
                new RecyclerItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String n = personsList.get(position).getName();
                if(selectedPersons.contains(n)){
                    selectedPersons.remove(n);
                    personsList.get(position).setSel(Boolean.FALSE);
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    selectedPersons.add(n);
                    personsList.get(position).setSel(Boolean.TRUE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        prev.setOnClickListener(v->{
          getActivity().onBackPressed();
        });

        return root;
    }

    public static class Person {
        String name;
        String type;
        Bitmap img;
        Boolean sel;

        public Person(String name, String type, Bitmap img, Boolean sel) {
            this.name = name;
            this.type = type;
            this.img = img;
            this.sel = sel;
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
        public void setName(String name) {
            this.name = name;
        }
        public void setType(String type) {
            this.type = type;
        }
        public void setImg(Bitmap img) {
            this.img = img;
        }
        public Boolean getSel() {
            return sel;
        }
        public void setSel(Boolean sel) {
            this.sel = sel;
        }
    }
}
