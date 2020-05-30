package com.example.hci.ui.documents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hci.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import androidx.appcompat.app.AppCompatActivity;

public class InsertNewDocument extends AppCompatActivity {

    EditText name;
    EditText utility;
    EditText amount;
    NiceSpinner status;
    Button storBtn;
    Button camBtn;
    ImageButton next;
    ImageButton prev;

    String selName;
    String selUtility;
    String selAmount;
    String selStatus;
    Bitmap selImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_insert_document);
        name = findViewById(R.id.nameField);
        utility = findViewById(R.id.utilityField);
        amount = findViewById(R.id.amountField);
        camBtn = findViewById(R.id.cameraBtn);
        storBtn = findViewById(R.id.storageBtn);
        status = findViewById(R.id.status_box);
        status.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(getApplicationContext(), "Please, select a correct status",
                            Toast.LENGTH_SHORT).show();
                    selStatus = null;
                }
                else {
                    selStatus = (String) parent.getItemAtPosition(position);
                }
            }
        });
        next =  findViewById(R.id.nextBtn);
        prev = findViewById(R.id.prevBtn);

        Intent intent = getIntent();
        String selector = intent.getStringExtra("label");
        Objects.requireNonNull(selector);
        if (selector.equals("CONTRACT")){
            ((TextView)findViewById(R.id.titleDocInsert)).setText("Insert new contract");
            utility.setVisibility(View.GONE);
            findViewById(R.id.utilityBox).setVisibility(View.GONE);
            amount.setVisibility(View.GONE);
            findViewById(R.id.amountBox).setVisibility(View.GONE);
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Signed", "To sign", "discussing"));
            status.attachDataSource(dataset);
        }
        if (selector.equals("UTILITY")){
            ((TextView)findViewById(R.id.titleDocInsert)).setText("Insert new bill");
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Paid", "To pay", "expired"));
            status.attachDataSource(dataset);
        }
        if (selector.equals("OTHERS")){
            ((TextView)findViewById(R.id.titleDocInsert)).setText("Insert other document");
            utility.setHint("Insert a label for the document");
            amount.setVisibility(View.GONE);
            findViewById(R.id.amountBox).setVisibility(View.GONE);
            List<String> dataset = new LinkedList<>(Arrays.asList("Select a status",
                    "Valid", "not valid"));
            status.attachDataSource(dataset);
        }

        next.setOnClickListener(v -> {
            Intent intentPers = new Intent(getApplicationContext(), NotifyPerson.class);
            startActivity(intentPers);
        });
    }

}
