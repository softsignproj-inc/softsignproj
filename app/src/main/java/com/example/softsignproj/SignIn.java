package com.example.softsignproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        Button signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(clickListener1);
        signUpButton.setOnClickListener(clickListener2);

        adminMode = false;
        SwitchMaterial adminToggle = findViewById(R.id.adminToggle);
        adminToggle.setOnCheckedChangeListener(changeListener);

        sharedPref = SignIn.this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Database db = new Database();
            String u = usernameField.getText().toString();

            if (adminMode) {
                db.read("administrator/" + u, successListener, failureListener);
            } else {
                db.read("customer/" + u, successListener, failureListener);
            }
        }
    };

    OnSuccessListener<? super Object> successListener = new OnSuccessListener<Object>() {
        @Override
        public void onSuccess(Object retrievedData) {

            HashMap<String, Object> userInfo;

            String u = usernameField.getText().toString();
            String p = passwordField.getText().toString();

            if (retrievedData instanceof HashMap) {
                userInfo = (HashMap<String, Object>) retrievedData;

                if (userInfo.containsKey("password") && Objects.requireNonNull(userInfo.get("password")).toString().equals(p)) {
                    Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Users", u);
                    editor.apply();

                    Intent intent;
                    if (adminMode) {
                        intent = new Intent(SignIn.this, AdminPage.class);
                    } else {
                        intent = new Intent(SignIn.this, HomePage.class);
                    }
                    startActivity(intent);

                } else {
                    Toast.makeText(SignIn.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener clickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) { 
            Intent intent = new Intent(SignIn.this, createAccount.class);
            startActivity(intent);
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