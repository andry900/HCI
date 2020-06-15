package com.example.hci.ui.documents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hci.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DocumentVisualization extends Fragment {
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.document_review, container, false);

        DocumentsFragment.Document doc = DocumentsFragment.savedDocs.get(
                 requireArguments().getInt("item"));

        ((TextView)root.findViewById(R.id.doc_name)).setText(doc.getName());
        ((TextView) root.findViewById(R.id.doc_status)).setText(doc.getStatus());
        if (doc.getImg() != null) {
            ((ImageView) root.findViewById(R.id.doc_img)).setImageBitmap(doc.getImg());
        }
        if (doc.getUtility() != null) {
            ((TextView) root.findViewById(R.id.doc_utility)).setText(doc.getUtility());
        }
        else {
            (root.findViewById(R.id.doc_utility_box)).setVisibility(View.GONE);
        }
        if (doc.getAmount() != null) {
            ((TextView) root.findViewById(R.id.doc_amount)).setText(doc.getAmount().toString() + "Euro");
        }
        else {
            (root.findViewById(R.id.doc_amount_box)).setVisibility(View.GONE);
        }
        return root;
    }
}
