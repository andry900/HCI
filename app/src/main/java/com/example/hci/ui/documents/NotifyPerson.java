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
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.hci.ui.documents.DocumentsFragment.personsList;

public class NotifyPerson extends Fragment {
    private static ArrayList<String> selectedPersons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.notify_person, container, false);

        RecyclerView personRecycler = root.findViewById(R.id.person_list);
        ImageButton next = root.findViewById(R.id.nextBtn);
        ImageButton prev = root.findViewById(R.id.prevBtn);
        Bundle recapBundle = getArguments();

        if (personsList == null) {
            selectedPersons = new ArrayList<>();
            personsList = new ArrayList<>();
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
            if (Objects.equals(requireArguments().getString("insert_new_event"), "insert new event")) {
                String frequency_chosen = requireArguments().getString("frequency_chosen");
                CheckRecap checkRecap = new CheckRecap();
                Bundle arguments = new Bundle();
                arguments.putString("notify_person", "notify person");
                arguments.putStringArrayList("persons",selectedPersons);
                arguments.putString("frequency_chosen",frequency_chosen);
                checkRecap.setArguments(arguments);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,checkRecap, "checkRecap")
                        .addToBackStack(null)
                        .commit();
            } else {
                Objects.requireNonNull(recapBundle).putStringArrayList("persons", selectedPersons);
                CheckRecap recapFrag = new CheckRecap();
                recapFrag.setArguments(recapBundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, recapFrag, "checkRecap")
                        .addToBackStack(null)
                        .commit();
            }
        });

        RecyclerItemClickSupport.addTo(personRecycler).setOnItemClickListener(
                (recyclerView, position, v) -> {
                    String n = personsList.get(position).getName();
                    if (selectedPersons.contains(n)) {
                        selectedPersons.remove(n);
                        personsList.get(position).setSel(Boolean.FALSE);
                    }
                    else {
                        selectedPersons.add(n);
                        personsList.get(position).setSel(Boolean.TRUE);
                    }
                    mAdapter.notifyDataSetChanged();
                });

        prev.setOnClickListener(v->  requireActivity().onBackPressed());

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
