package com.example.hci.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.hci.NavigationActivity;
import com.example.hci.R;
import com.example.hci.ui.home.HomeFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static Bitmap gallery_image;
    private CircularImageView profile_pic;
    private static final int PICK_IMAGE = 200;
    private static String strName, strSurname, strProfession, strHobbies;
    private static Integer intAge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Profile");

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //LOCAL VARIABLES
        profile_pic = root.findViewById(R.id.profile_pic);
        TextView change_image = root.findViewById(R.id.change_image);
        EditText name = root.findViewById(R.id.editName);
        EditText surname = root.findViewById(R.id.editSurname);
        EditText age = root.findViewById(R.id.editAge);
        EditText profession = root.findViewById(R.id.editProfession);
        EditText hobbies = root.findViewById(R.id.editHobbies);

        Button btnSave = root.findViewById(R.id.btnSave);

        if (gallery_image != null) {
            profile_pic.setImageBitmap(gallery_image);
        }

        if (strName != null) {
            name.setText(strName);
        }

        if (strSurname != null) {
            surname.setText(strSurname);
        }

        if (intAge != null) {
            age.setText(String.valueOf(intAge));
        }

        if (strProfession != null) {
            profession.setText(strProfession);
        }

        if (strHobbies != null) {
            hobbies.setText(strHobbies);
        }

        profile_pic.setOnClickListener(v-> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE);
        });

        change_image.setOnClickListener(v-> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE);
        });

        btnSave.setOnClickListener(v-> {
            strName = name.getText().toString();
            strSurname = surname.getText().toString();

            if (!age.getText().toString().equals("")) {
                intAge = Integer.parseInt(age.getText().toString());
            } else {
                age.setError("Please enter your Age!");
            }

            strProfession = profession.getText().toString();
            strHobbies = hobbies.getText().toString();

            if (strName.equals("") || strSurname.equals("") || age.getError() != null) {
                if (TextUtils.isEmpty(strName)) {
                    name.setError("Please enter your Name!");
                }

                if (TextUtils.isEmpty(strSurname)) {
                    surname.setError("Please enter your Surname!");
                }
            } else {
                HomeFragment.home_profile = gallery_image;
                HomeFragment.homeName = strName;
                HomeFragment.homeSurname = strSurname;
                HomeFragment.homeAge = intAge;

                NavigationActivity.change_nav_info(strName, strSurname);

                requireActivity().onBackPressed();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Uri imageUri = data.getData();

                    if (imageUri != null) {
                        InputStream imageStream = requireActivity()
                                .getContentResolver()
                                .openInputStream(imageUri);

                        gallery_image = BitmapFactory.decodeStream(imageStream);
                        profile_pic.setImageBitmap(gallery_image);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}
