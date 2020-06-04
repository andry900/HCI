package com.example.hci.ui.documents;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hci.R;
import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class InsertNewDocument extends Fragment {

    EditText name;
    EditText utility;
    EditText amount;
    NiceSpinner status;
    Button storBtn;
    Button camBtn;
    ImageView docImg;
    ImageButton next;
    ImageButton prev;

    String selName = null;
    String selUtility = null;
    String selAmount = null;
    String selStatus = null;
    Bitmap selImg;

    private static final int PICK_IMAGE = 200;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_document, container, false);
        //retrieve passed data
        Bundle bundle = getArguments();
        assert bundle != null;
        String selector = bundle.getString("label");
        //initialize layout items and responsiveness
        name = root.findViewById(R.id.nameField);
        utility = root.findViewById(R.id.utilityField);
        amount = root.findViewById(R.id.amountField);
        docImg = root.findViewById(R.id.upld_img);
        if (selImg != null) {
            docImg.setImageBitmap(selImg);
        }
        camBtn = root.findViewById(R.id.cameraBtn);
        camBtn.setOnClickListener(v->{
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        storBtn = root.findViewById(R.id.storageBtn);
        storBtn.setOnClickListener(v->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE);
        });

        status = root.findViewById(R.id.status_box);
        //set item click listener on the spinner item
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
                    status.setError(null);
                }
            }
        });

        next =  root.findViewById(R.id.nextBtn);

        next.setOnClickListener(v -> {
            if (checkAndCollectData()){
                NotifyPerson notifyPerson = new NotifyPerson();
                //prepare data to pass on
                Bundle b = new Bundle();
                b.putString("name", selName);
                b.putString("amount", selAmount);
                b.putString("status", selStatus);
                b.putParcelable("image", selImg);
                if (selector.equals("OTHERS")){
                    b.putString("utility", "");
                    b.putString("label", selUtility.toUpperCase());
                }
                else{
                    b.putString("utility", selUtility);
                    b.putString("label", selector);

                }
                notifyPerson.setArguments(b);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, notifyPerson, "notifyPerson")
                        .addToBackStack(null)
                        .commit();
            }
        });

        prev = root.findViewById(R.id.prevBtn);
        prev.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        //hide and shows layout items wrt selected label
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

        return root;
    }


    private boolean checkAndCollectData() {
        boolean resp = true;
        if (name.getText().toString().equals("") || name.getText().toString().equals(" ")){
            name.setError("Please enter a name!");
            resp = false;}
        if (utility.getVisibility() == View.VISIBLE) {
            if (utility.getText().toString().equals("") || utility.getText().toString().equals(" ")) {
                utility.setError("Please fill this field!");
                resp = false;
            }
        }
        if (amount.getVisibility() == View.VISIBLE) {
            if (amount.getText().toString().equals("") || amount.getText().toString().equals(" ")) {
                amount.setError("Please enter an amount!");
                resp = false;
            }
        }
        if (selStatus == null) {
           status.setError("Please enter a valid status!");
                resp = false;
        }

        if(resp) {
            selName = name.getText().toString();
            selStatus = status.getText().toString();
            selUtility = utility.getText().toString();
            selAmount = amount.getText().toString();
        }
        return resp;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            selImg = (Bitmap) data.getExtras().get("data");
            docImg.setImageBitmap(selImg);
        }
        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver()
                        .openInputStream(imageUri);
                selImg= BitmapFactory.decodeStream(imageStream);
                docImg.setImageBitmap(selImg);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

}
