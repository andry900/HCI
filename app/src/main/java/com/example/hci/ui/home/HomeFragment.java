package com.example.hci.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.hci.R;

public class HomeFragment extends Fragment {
    public static Bitmap home_profile;
    public static String homeName, homeSurname;
    public static Integer homeAge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String newName;

        ImageView profile_pic = root.findViewById(R.id.profile_pic);
        TextView user_summary = root.findViewById(R.id.user_summary);

        if (home_profile != null) {
            profile_pic.setImageBitmap(home_profile);
        }

        if (homeName != null && homeSurname != null && homeAge != null) {
            if (!homeName.equals("") || !homeSurname.equals("") || !String.valueOf(homeAge).equals("")) {
                newName = homeName + " " + homeSurname + "\n" + homeAge + "Y\nRoommate in house in:\n Via Italia, 157";
            } else {
                newName = "Marco Rossi\n23Y\nRoommate in house in:\nVia Italia, 157";
            }
        } else {
            newName = "Marco Rossi\n23Y\nRoommate in house in:\nVia Italia, 157";
        }

        user_summary.setText(newName);

        return root;
    }
}
