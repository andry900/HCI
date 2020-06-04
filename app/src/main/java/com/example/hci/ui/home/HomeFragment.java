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
    private static TextView user_summary;
    private static ImageView profile_pic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        if (user_summary == null) {
            user_summary = root.findViewById(R.id.user_summary);

            String newName = "Marco Rossi\n23Y\nRoommate in house in:\nVia Italia, 157";
            user_summary.setText(newName);
        }

        if (profile_pic == null) {
            profile_pic = root.findViewById(R.id.profile_pic);
        }

        return root;
    }

    public static void change_home_info(Bitmap bitmap, String name, String surname, Integer age) {
        if (bitmap != null)  {
            profile_pic.setImageBitmap(bitmap);
        }

        if (!name.equals("") && !surname.equals("")) {
            String newName = name + " " + surname + "\n" + age + "Y\nRoommate in house in:\n Via Italia, 157";
            user_summary.setText(newName);
        }
    }
}
