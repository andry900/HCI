package com.example.hci.ui.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hci.R;

public class InsertNumberFragment extends Fragment {
    public ImageButton ic_general_image;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_number, container, false);
        ImageButton ic_general_image = root.findViewById(R.id.image_insert_new_number);
        ic_general_image.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),ChooseIconNumber.class);
            startActivity(intent);
        });
        return root;

    }
    public ImageButton getIc_general_image(){
        return this.ic_general_image;
    }
}
