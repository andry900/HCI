package com.example.hci.ui.numbers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;
import java.util.Objects;

public class ShowUsefulNumber extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_show_useful_number, container, false);
        TextView show_useful_number = root.findViewById(R.id.textView_show_useful_number);
        EditText editText_insert_name_show_number = root.findViewById(R.id.editText_insert_name_show_number);
        EditText editText_insert_show_number = root.findViewById(R.id.editText_insert_show_number);
        EditText editText_insert_show_comment = root.findViewById(R.id.editText_insert_show_comment);
        ImageButton imageButton_show_useful_number = root.findViewById(R.id.image_show_useful_number);
        Button update_button_show = root.findViewById(R.id.update_button_show_number);
        Button delete_button_show = root.findViewById(R.id.delete_button_show_number);

        //GET VALUES FROM THE PREVIOUS FRAGMENT
        Bundle bundle = getArguments();
        String name_number = Objects.requireNonNull(bundle).getString("name_number");
        String icon_number = bundle.getString("icon_number");
        String number = bundle.getString("number");
        String comment = bundle.getString("comment");
        String position = bundle.getString("position");

        //SET THE INFO INTO THE RELATED EDIT-TEXT
        imageButton_show_useful_number.setImageResource(Integer.parseInt(Objects.requireNonNull(icon_number)));
        editText_insert_name_show_number.setText(name_number);
        editText_insert_show_number.setText(number);
        editText_insert_show_comment.setText(comment);
        show_useful_number.setText(name_number);

        //CLICK ON UPDATE BUTTON
        update_button_show.setOnClickListener(v -> {
            if (editText_insert_name_show_number.getText().toString().equals("") || editText_insert_name_show_number.getText().toString().equals(" "))
                editText_insert_name_show_number.setError("Please enter a name!");
            if (editText_insert_show_number.getText().toString().equals("") || editText_insert_show_number.getText().toString().equals(" "))
                editText_insert_show_number.setError("Please enter a number!");
            if (editText_insert_show_comment.getText().toString().equals("") || editText_insert_show_comment.getText().toString().equals(" "))
                editText_insert_show_comment.setError("Please enter a comment!");
            else if ( editText_insert_name_show_number.getError() == null && editText_insert_show_number.getError() == null
                    && editText_insert_show_comment.getError() == null) {

                //UPDATE THE VALUES
                NumbersFragment.name_numbers.set(Integer.parseInt(Objects.requireNonNull(position)),editText_insert_name_show_number.getText().toString());
                NumbersFragment.numbers.set(Integer.parseInt(position),editText_insert_show_number.getText().toString());
                NumbersFragment.comments.set(Integer.parseInt(position),editText_insert_show_comment.getText().toString());

                //COME BACK TO THE PREVIOUS FRAGMENT
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new NumbersFragment(),"fragment_numbers")
                        .addToBackStack(null)
                        .commit();
            }
        });

        //DELETE BUTTON
        delete_button_show.setOnClickListener(v -> {
            NumbersFragment.icon_numbers.remove(Integer.parseInt(Objects.requireNonNull(position)));
            NumbersFragment.name_numbers.remove(Integer.parseInt(position));
            NumbersFragment.numbers.remove(Integer.parseInt(position));
            NumbersFragment.comments.remove(Integer.parseInt(position));

            //COME BACK TO THE PREVIOUS FRAGMENT
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new NumbersFragment(),"fragment_numbers")
                    .addToBackStack(null)
                    .commit();
        });
        return root;
    }
}
