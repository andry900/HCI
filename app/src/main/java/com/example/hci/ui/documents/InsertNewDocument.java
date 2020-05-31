package com.example.hci.ui.documents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hci.R;
import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InsertNewDocument extends Fragment {

    EditText name;
    EditText utility;
    EditText amount;
    NiceSpinner status;
    Button storBtn;
    Button camBtn;
    ImageButton next;
    ImageButton prev;

    String selName = null;
    String selUtility = null;
    String selAmount = null;
    String selStatus = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_document, container, false);

        name = root.findViewById(R.id.nameField);
        utility = root.findViewById(R.id.utilityField);
        amount = root.findViewById(R.id.amountField);
        camBtn = root.findViewById(R.id.cameraBtn);
        storBtn = root.findViewById(R.id.storageBtn);
        status = root.findViewById(R.id.status_box);
        status.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getContext(), "Please, select a correct status",
                            Toast.LENGTH_SHORT).show();
                    selStatus = null;
                }
                else {
                    selStatus = (String) parent.getItemAtPosition(position);
                }
            }
        });

        next =  root.findViewById(R.id.nextBtn);
        prev = root.findViewById(R.id.prevBtn);
        prev.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        Bundle bundle = getArguments();
        assert bundle != null;
        String selector = bundle.getString("label");
        Objects.requireNonNull(selector);
        if (selector.equals("CONTRACT")){
            ((TextView)root.findViewById(R.id.titleDocInsert)).setText("Insert new contract");
            utility.setVisibility(View.GONE);
            root.findViewById(R.id.utilityBox).setVisibility(View.GONE);
            amount.setVisibility(View.GONE);
            root.findViewById(R.id.amountBox).setVisibility(View.GONE);
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Signed", "To sign", "discussing"));
            status.attachDataSource(dataset);
        }
        if (selector.equals("UTILITY")){
            ((TextView)root.findViewById(R.id.titleDocInsert)).setText("Insert new bill");
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Paid", "To pay", "expired"));
            status.attachDataSource(dataset);
        }
        if (selector.equals("OTHERS")){
            ((TextView)root.findViewById(R.id.titleDocInsert)).setText("Insert other document");
            utility.setHint("Insert a label for the document");
            amount.setVisibility(View.GONE);
            root.findViewById(R.id.amountBox).setVisibility(View.GONE);
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Valid", "not valid"));
            status.attachDataSource(dataset);
        }

        next.setOnClickListener(v -> {
            if (checkAndCollectData()){
                NotifyPerson notifyPerson = new NotifyPerson();
                Bundle b = new Bundle();
                b.putString("name", selName);
                b.putString("amount", selAmount);
                b.putString("status", selStatus);
                b.putString("utility", selUtility);
                notifyPerson.setArguments(b);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, notifyPerson, "notifyPerson")
                        .addToBackStack(null)
                        .commit();
            }
        });


        return root;
    }

    private boolean checkAndCollectData() {
        if (name.getText().toString().equals("") || name.getText().toString().equals(" ")){
            name.setError("Please enter a name!");
            return false;}
        if (utility.getVisibility() == View.VISIBLE) {
            if (utility.getText().toString().equals("") || utility.getText().toString().equals(" ")) {
                utility.setError("Please fill this field!");
                return false;
            }
        }
        if (amount.getVisibility() == View.VISIBLE) {
            if (amount.getText().toString().equals("") || amount.getText().toString().equals(" ")) {
                amount.setError("Please enter an amount!");
                return false;
            }
        }
        if (selStatus == null) {
                amount.setHint("Please enter a valid status!");
                amount.setHintTextColor(Color.RED);
                return false;
        }

        selName = name.getText().toString();
        selStatus = status.getText().toString();
        selUtility = utility.getText().toString();
        selAmount = amount.getText().toString();

        return true;
    }

}
