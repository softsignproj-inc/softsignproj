package com.example.softsignproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private Button signUpButton;
    private boolean adminMode;
    private Toast toast;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, createAccount.class);
                startActivity(intent);
            }
        });

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usernameField.setError(null);
                passwordField.setError(null);

                String u = usernameField.getText().toString();
                String p = passwordField.getText().toString();

                if (u.matches("")) {
                    usernameField.setError("Username cannot be empty");
                    //usernameField.setBackgroundResource(R.drawable.invalid_input_border);
                }

                if (p.matches("")) {
                    passwordField.setError("Password cannot be empty");
                }

                if (usernameField.getError() != null || passwordField.getError() != null) {
                    usernameField.clearFocus();
                    passwordField.clearFocus();
                    return;
                }

                Database db = new Database();
                if (adminMode) {
                    db.read("administrator/" + u, successListener, failureListener, false);
                } else {
                    db.read("customer/" + u, successListener, failureListener, false);
                }
            }
        });

        adminMode = false;
        SwitchMaterial adminToggle = findViewById(R.id.adminToggle);
        adminToggle.setOnCheckedChangeListener(changeListener);

        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
    }

    OnSuccessListener<? super Object> successListener = new OnSuccessListener<Object>() {
        @Override
        public void onSuccess(Object retrievedData) {

            HashMap<String, Object> userInfo;

            String u = usernameField.getText().toString();
            String p = passwordField.getText().toString();

            if (retrievedData instanceof HashMap) {
                userInfo = (HashMap<String, Object>) retrievedData;

                if (userInfo.containsKey("password") && Objects.requireNonNull(userInfo.get("password")).toString().equals(p)) {
                    toast.setText("Sign in successful");
                    toast.show();

                    Intent intent;
                    if (adminMode) {
                        intent = new Intent(SignIn.this, AdminPage.class);
                    } else {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Current User", u);
                        editor.apply();

                        intent = new Intent(SignIn.this, HomePage.class);
                    }
                    startActivity(intent);

                } else {
                    passwordField.setError("Incorrect password");
                }
            } else {
                usernameField.setError("User does not exist");
            }
        }
    };

    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            toast.setText("Database error");
            toast.show();
        }
    };

    CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            adminMode = isChecked;
            signUpButton.setEnabled(!isChecked);
        }
    };

}