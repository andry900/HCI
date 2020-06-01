package com.example.hci.ui.documents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci.R;
import com.example.hci.ui.numbers.InsertNumberFragment;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class DocumentsFragment extends Fragment {

    static ArrayList<Document> savedDocs;
    static ArrayList<NotifyPerson.Person> personsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        RecyclerView docList = root.findViewById(R.id.docs_list);
        Button newDocBtn = root.findViewById(R.id.newDoc_btn);
        if (savedDocs == null) {
            savedDocs = new ArrayList<>();
            savedDocs.add(new Document("February Energy Bill", "UTILITY", "payed",
                    null, "Energy", Float.valueOf("256.98")));
            savedDocs.add(new Document("February telephone Bill", "UTILITY", "payed",
                    null, "Gas", Float.valueOf("123.68")));
            savedDocs.add(new Document("telephone contract", "CONTRACT", "payed",
                    null, null, null));
        }
        docList.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        docList.setLayoutManager(lm);
        DocumentListAdapter mAdapter = new DocumentListAdapter(savedDocs);
        docList.setAdapter(mAdapter);

        newDocBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseLabel.class);
            startActivityForResult(intent,2);
        });

        RecyclerItemClickSupport.addTo(docList).setOnItemClickListener(
                new RecyclerItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        DocumentVisualization docFrag = new DocumentVisualization();
                        Bundle b = new Bundle();
                        b.putInt("item", position);
                        docFrag.setArguments(b);
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, docFrag,
                                        "visualizeDoc")
                                .addToBackStack(null)
                                .commit();
                    }
                });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                String selector = Objects.requireNonNull(data).getStringExtra("label");

                InsertNewDocument newDocFragment = new InsertNewDocument();
                Bundle bundle = new Bundle();
                bundle.putString("label", selector);
                newDocFragment.setArguments(bundle);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, newDocFragment, "newDocFragment")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    public static class Document{

        private String name;
        private String label;
        private String status;
        private Bitmap img;
        private String utility;
        private Float amount;

        Document(String name, String label, String status, Bitmap img, String utility, Float amount) {
            this.name = name;
            this.label = label;
            this.status = status;
            this.img = img;
            this.utility = utility;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Bitmap getImg() {
            return img;
        }

        public void setImg(Bitmap img) {
            this.img = img;
        }

        public String getUtility() {
            return utility;
        }

        public void setUtility(String utility) {
            this.utility = utility;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }
}
