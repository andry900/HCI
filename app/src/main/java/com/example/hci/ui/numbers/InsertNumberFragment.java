package com.example.hci.ui.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hci.R;

import java.util.ArrayList;

public class InsertNumberFragment extends Fragment {
    public ImageButton ic_general_image;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_number, container, false);
        ImageButton ic_general_image = root.findViewById(R.id.image_insert_new_number);
        EditText editText_insert_name_number = root.findViewById(R.id.editText_insert_name_number);
        EditText editText_insert_number = root.findViewById(R.id.editText_insert_number);
        EditText editText_insert_comment= root.findViewById(R.id.editText_insert_comment);
        Button button_save = root.findViewById(R.id.saveButton_insertNumber);

        ic_general_image.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),ChooseIconNumber.class);
            startActivity(intent);
        });


        button_save.setOnClickListener(v -> {
            if (editText_insert_name_number.getText().toString().equals("") || editText_insert_name_number.getText().toString().equals(" "))
                editText_insert_name_number.setError("Please enter a name!");
            if (editText_insert_number.getText().toString().equals("") || editText_insert_number.getText().toString().equals(" "))
                editText_insert_number.setError("Please enter a number!");
            if (editText_insert_comment.getText().toString().equals("") || editText_insert_comment.getText().toString().equals(" "))
                editText_insert_comment.setError("Please enter a comment!");
            else if( editText_insert_name_number.getError() == null && editText_insert_number.getError() == null
            && editText_insert_comment.getError() == null){
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new NumbersFragment(),"NumbersFragment")
                        .commit();
            }

        });


        return root;


    }
    public ImageButton getIc_general_image(){
        return this.ic_general_image;
    }
}
