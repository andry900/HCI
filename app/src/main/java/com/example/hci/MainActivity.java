package com.example.hci;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button login = findViewById(R.id.login);
        Button signIn = findViewById(R.id.sign_in);
        Button google = findViewById(R.id.google_login);
        Button facebook = findViewById(R.id.facebook_login);

        login.setOnClickListener(v -> {
            Login();
        });
        signIn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PopUpSignIn.class);
            startActivity(intent);
        });
        google.setOnClickListener(v -> {
            Login();
        });
        facebook.setOnClickListener(v -> {
            Login();
        });
    }

    public void Login() {
        Intent navigationActivity = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(navigationActivity);
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
