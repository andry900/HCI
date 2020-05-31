package com.example.hci.ui.documents;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hci.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.hci.ui.documents.DocumentsFragment.savedDocs;

public class CheckRecap extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recap_fragment, container, false);
        Bundle recapBundle = getArguments();
        Float amount = null;

        TextView recapText = root.findViewById(R.id.recapMultiLine);
        Button confirm = root.findViewById(R.id.confirm_btn);
        Button change = root.findViewById(R.id.change_btn);

        StringBuilder sb = new StringBuilder();
        sb.append("RECAP: \n -");
        sb.append( recapBundle.getString("name"));
        sb.append("\n -");
        if(!recapBundle.getString("amount").equals("")) {
            amount = Float.valueOf((recapBundle.getString("amount")));
            sb.append(amount);
            sb.append("\n -");
        }
        if(recapBundle.getString("utility").equals("")) {
            sb.append(recapBundle.getString("utility"));
            sb.append("\n -");
        }
        sb.append(recapBundle.getString("status"));
        sb.append("\n ");
        sb.append("\n ");
        sb.append("PERSONS TO NOTIFY:");
        ArrayList<String> persNotify = recapBundle.getStringArrayList("persons");
        if(persNotify.size()> 0) {
            for (int p = 0; p < (persNotify.size()); p++) {
                sb.append("\n -");
                sb.append(persNotify.get(p));

            }
        }
        recapText.setText(sb.toString());

        change.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().popBackStack();

        });

        Float finalAmount = amount;
        confirm.setOnClickListener(v -> {
            DocumentsFragment.Document newDoc = new DocumentsFragment.Document(
                    recapBundle.getString("name"), recapBundle.getString("label"),
                    recapBundle.getString("status"),recapBundle.getParcelable("image"),
                    recapBundle.getString("utility"),
                    finalAmount);
            savedDocs.add(newDoc);
            Toast.makeText(getContext(), "Document has been correctly inserted",
                    Toast.LENGTH_LONG).show();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new DocumentsFragment(),"fragment_documents")
                    .addToBackStack(null)
                    .commit();
        });

        return root;
    }
}
