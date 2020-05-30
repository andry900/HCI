package com.example.hci.ui.documents;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.example.hci.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.hci.ui.documents.DocumentsFragment.personsList;

public class NotifyPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_person);

        RecyclerView personRecycler = findViewById(R.id.person_list);

        personsList.add(new Person("Mario Rossi","Roomates",null));
        personsList.add(new Person("Palma Alessandro","Roomates",null));
        personsList.add(new Person("Bellia Andrea","Landlord",null));
        personsList.add(new Person("Manzara Manuel","Roomates",null));
        personsList.add(new Person("Santinelli Stefano","Roomates",null));

        personRecycler.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(NotifyPerson.this);
        personRecycler.setLayoutManager(lm);
        PersonAdapter mAdapter = new PersonAdapter(personsList);
        personRecycler.setAdapter(mAdapter);

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
