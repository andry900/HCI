package com.example.hci.ui.documents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci.R;
import com.example.hci.ui.numbers.InsertNumberFragment;

import java.util.ArrayList;

public class DocumentsFragment extends Fragment {

    private RecyclerView docList;
    private Button newDocBtn;

    private LinearLayoutManager lm;
    private DocumentListAdapter mAdapter;
    static ArrayList<Document> savedDocs;
    static ArrayList<NotifyPerson.Person> personsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_documents, container, false);

        docList = root.findViewById(R.id.docs_list);
        newDocBtn = root.findViewById(R.id.newDoc_btn);

        savedDocs = new ArrayList<>();
        savedDocs.add(new Document("February Energy Bill", "bill","payed",
                null,null,null));
        savedDocs.add(new Document("February telephone Bill", "bill","payed",
                null,null,null));
        savedDocs.add(new Document("telephone contract", "contract","payed",
                null,null,null));

        docList.setHasFixedSize(true);
        lm = new LinearLayoutManager(getContext());
        docList.setLayoutManager(lm);
        mAdapter = new DocumentListAdapter(savedDocs);
        docList.setAdapter(mAdapter);

        newDocBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseLabel.class);
            startActivity(intent);
        });

        return root;
    }

    public static class Document{

        private String name;
        private String label;
        private String status;
        private String img;
        private String utility;
        private Float amount;

        Document(String name, String label, String status, String img, String utility, Float amount) {
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
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
