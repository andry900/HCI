package com.example.hci;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PopUpSignIn extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.chosenPopUp.equals("ForgotPassword")) {
            setContentView(R.layout.forgot_password);

            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //avoid external touch to close the registration dialog
            this.setFinishOnTouchOutside(false);

            EditText popup_password = findViewById(R.id.popup_edPassword);
            EditText popup_passwordCheck = findViewById(R.id.popup_edPasswordCheck);
            Button abort = findViewById(R.id.btn_abort);
            Button save_changes = findViewById(R.id.btn_saveChanges);

            abort.setOnClickListener(v -> onBackPressed());

            save_changes.setOnClickListener(v -> {
                String txtPassword = popup_password.getText().toString();
                String txtPasswordCheck = popup_passwordCheck.getText().toString();

                if ((TextUtils.isEmpty(txtPassword) || txtPassword.length() < 6) ||
                        !txtPassword.equals(txtPasswordCheck) || TextUtils.isEmpty(txtPasswordCheck)) {

                    if (TextUtils.isEmpty(txtPassword) || txtPassword.length() < 6) {
                        popup_password.setError("Please enter a password of at least 6 characters!");
                    }

                    if (TextUtils.isEmpty(txtPasswordCheck)) {
                        popup_passwordCheck.setError("Please enter the password to check!");
                    }

                    if (!(txtPassword.equals(txtPasswordCheck))) {
                        popup_password.setError("Password are different!");
                        popup_passwordCheck.setError("Password are different!");
                    }

                } else {
                    finish();
                }
            });

        } else {
            setContentView(R.layout.sign_in);

            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //avoid external touch to close the registration dialog
            this.setFinishOnTouchOutside(false);

            Button sign_in = findViewById(R.id.popup_btnSignIn);
            EditText popup_name = findViewById(R.id.popup_edName);
            EditText popup_surname = findViewById(R.id.popup_edSurname);
            EditText popup_email = findViewById(R.id.popup_edEmail);
            EditText popup_password = findViewById(R.id.popup_edPassword);
            EditText popup_passwordCheck = findViewById(R.id.popup_edPasswordCheck);
            Button abort = findViewById(R.id.btn_abort);
            RadioGroup userRadioGroup = findViewById(R.id.radioGroup);
            RadioButton landlord = findViewById(R.id.landlord_radio);
            CheckBox privacy = findViewById(R.id.privacy_check);
            TextView GDPR = findViewById(R.id.GDPR);

            privacy.setOnClickListener(v-> {
                if (privacy.getError() != null) {
                    privacy.setError(null);
                }
            });

            GDPR.setOnClickListener(v -> {
                String url = "https://www.gdprprivacypolicy.net/live.php?token=MUWvXQkGkKhzxrJ7hNPUXdcasNi617l2";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            });

            userRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (landlord.getError() != null) {
                    landlord.setError(null);
                }
            });

            abort.setOnClickListener(v -> onBackPressed());

            sign_in.setOnClickListener(v -> {
                String txtName = popup_name.getText().toString();
                String txtSurname = popup_surname.getText().toString();
                String txtEmail = popup_email.getText().toString();
                String txtPassword = popup_password.getText().toString();
                String txtPasswordCheck = popup_passwordCheck.getText().toString();
                String userType = "tenant";

                if ( TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtSurname) ||
                        (TextUtils.isEmpty(txtEmail) || !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) ||
                        (TextUtils.isEmpty(txtPassword) || txtPassword.length() < 6) ||
                        !txtPassword.equals(txtPasswordCheck) || TextUtils.isEmpty(txtPasswordCheck) ||
                        userRadioGroup.getCheckedRadioButtonId() == -1 || !privacy.isChecked() ) {

                    if (TextUtils.isEmpty(txtName)) {
                        popup_name.setError("Please enter your Name!");
                    }

                    if (TextUtils.isEmpty(txtSurname)) {
                        popup_surname.setError("Please enter your Surname!");
                    }

                    if (TextUtils.isEmpty(txtEmail) || !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                        popup_email.setError("Please enter a valid Email!");
                    }

                    if (TextUtils.isEmpty(txtPassword) || txtPassword.length() < 6) {
                        popup_password.setError("Please enter a password of at least 6 characters!");
                    }

                    if (TextUtils.isEmpty(txtPasswordCheck)) {
                        popup_passwordCheck.setError("Please enter the password to check!");
                    }

                    if (!(txtPassword.equals(txtPasswordCheck))) {
                        popup_password.setError("Password are different!");
                        popup_passwordCheck.setError("Password are different!");
                    }

                    if (userRadioGroup.getCheckedRadioButtonId() == -1) {
                        landlord.setError("Please select one user type!");
                    }

                    if (!privacy.isChecked()) {
                        privacy.setError("You need to accept privacy policy");
                    }


                } else {
                    if (landlord.isChecked()) {
                        userType = "landlord";
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("mail",txtEmail);
                    returnIntent.putExtra("pwd",txtPassword);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });
        }
    }
}
