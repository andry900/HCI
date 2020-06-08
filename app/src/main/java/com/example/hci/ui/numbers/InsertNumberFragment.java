package com.example.hci.ui.numbers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;

public class InsertNumberFragment extends Fragment {
    public ImageButton ic_general_image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_insert_number, container, false);
        ic_general_image = root.findViewById(R.id.image_insert_new_number);
        EditText editText_insert_name_number = root.findViewById(R.id.editText_insert_name_number);
        EditText editText_insert_number = root.findViewById(R.id.editText_insert_number);
        EditText editText_insert_comment= root.findViewById(R.id.editText_insert_comment);
        Button button_save = root.findViewById(R.id.saveButton_insertNumber);

        //TO CHOOSE THE ICON OF THE NUMBER
        ic_general_image.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),ChooseIconNumber.class);
            startActivityForResult(intent, 1);
        });

        //CLICK ON SAVE BUTTON
        button_save.setOnClickListener(v -> {
            if (editText_insert_name_number.getText().toString().equals("") || editText_insert_name_number.getText().toString().equals(" "))
                editText_insert_name_number.setError("Please enter a name!");
            if (editText_insert_number.getText().toString().equals("") || editText_insert_number.getText().toString().equals(" "))
                editText_insert_number.setError("Please enter a number!");
            if (ic_general_image.getTag() == null)
                Toast.makeText(getContext(), "Please choose an icon", Toast.LENGTH_SHORT).show();
            else if ( editText_insert_name_number.getError() == null && editText_insert_number.getError() == null
            && ic_general_image.getTag() != null) {

                String image_path = "";
                switch (ic_general_image.getTag().toString()) {
                    case "imgButton_ic_ambulance":
                        image_path = String.valueOf(R.mipmap.ic_ambulance);
                        break;
                    case "imgButton_ic_fire":
                        image_path = String.valueOf(R.mipmap.ic_fire);
                        break;
                    case "imgButton_ic_police":
                        image_path = String.valueOf(R.mipmap.ic_police);
                        break;
                    case "imgButton_ic_neighbour":
                        image_path = String.valueOf(R.mipmap.ic_neighbour);
                        break;
                    case "imgButton_ic_water":
                        image_path = String.valueOf(R.mipmap.ic_water);
                        break;
                    case "imgButton_ic_landlord":
                        image_path = String.valueOf(R.mipmap.ic_landlord);
                        break;
                }

                //ADD THE INFO TO THE ARRAY IN ORDER TO FILL THE LISTVIEW
                NumbersFragment.icon_numbers.add(image_path);
                NumbersFragment.name_numbers.add(editText_insert_name_number.getText().toString());
                NumbersFragment.numbers.add(editText_insert_number.getText().toString());
                NumbersFragment.comments.add(editText_insert_comment.getText().toString());

                //NOTIFY THE ADAPTER ABOUT THE CHANGES ON THE LISTIVIEW
                ((BaseAdapter)NumbersFragment.numbers_listView.getAdapter()).notifyDataSetChanged();

                //COME BACK TO THE PREVIOUS FRAGMENT
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new NumbersFragment(),"fragment_numbers")
                        .commit();
            }
        });
        return root;
    }

    //TO TAKE THE VALUE OF THE ICON CHOSEN BY THE USER
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String imgTag= Objects.requireNonNull(data).getStringExtra("result");

                switch (Objects.requireNonNull(imgTag)) {
                    case "imgButton_ic_ambulance":
                        ic_general_image.setImageResource(R.mipmap.ic_ambulance);
                        ic_general_image.setTag(imgTag);
                        break;
                    case "imgButton_ic_fire":
                        ic_general_image.setImageResource(R.mipmap.ic_fire);
                        ic_general_image.setTag(imgTag);
                        break;
                    case "imgButton_ic_police":
                        ic_general_image.setImageResource(R.mipmap.ic_police);
                        ic_general_image.setTag(imgTag);
                        break;
                    case "imgButton_ic_neighbour":
                        ic_general_image.setImageResource(R.mipmap.ic_neighbour);
                        ic_general_image.setTag(imgTag);
                        break;
                    case "imgButton_ic_water":
                        ic_general_image.setImageResource(R.mipmap.ic_water);
                        ic_general_image.setTag(imgTag);
                        break;
                    case "imgButton_ic_landlord":
                        ic_general_image.setImageResource(R.mipmap.ic_landlord);
                        ic_general_image.setTag(imgTag);
                        break;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.w("InsertNumberFragment", "wrong result in StartActivity for result");
            }
        }
    }
}
