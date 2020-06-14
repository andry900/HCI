package com.example.hci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.util.Objects;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText login_name;
    private EditText login_pwd;
    private ProgressBar bar_login;
    public static String chosenPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button login = findViewById(R.id.login);
        CheckedTextView signIn = findViewById(R.id.sign_in);
        Button google = findViewById(R.id.google_login);
        Button facebook = findViewById(R.id.facebook_login);
        CheckedTextView forgot_password = findViewById(R.id.forgot_password);

        login_name = findViewById(R.id.email);
        login_pwd = findViewById(R.id.password);
        bar_login = findViewById(R.id.progressBar);

        forgot_password.setOnClickListener(v -> {
            chosenPopUp = "ForgotPassword";
            Intent intent = new Intent(getApplicationContext(), PopUpSignIn.class);
            startActivityForResult(intent, 1);

        });

        signIn.setOnClickListener(v -> {
            chosenPopUp = "SignIn";
            Intent intent = new Intent(getApplicationContext(), PopUpSignIn.class);
            startActivityForResult(intent, 1);

        });

        login.setOnClickListener(v -> Login());
        google.setOnClickListener(v -> Login());
        facebook.setOnClickListener(v -> Login());
    }

    public void Login() {
        bar_login.setVisibility(View.VISIBLE);
        Intent navigationActivity = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(navigationActivity);
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Return from registration
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                login_name.setText(Objects.requireNonNull(data).getStringExtra("mail"));
                login_pwd.setText(Objects.requireNonNull(data).getStringExtra("pwd"));
            }
        }

    }
}
