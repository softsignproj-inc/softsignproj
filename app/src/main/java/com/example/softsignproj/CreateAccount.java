package com.example.softsignproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softsignproj.venueList.VenueList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private Toast toast;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameField = findViewById(R.id.newUsernameField);
        passwordField = findViewById(R.id.newPasswordField);

        Button createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(clickListener);

        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            usernameField.setError(null);
            passwordField.setError(null);

            String p = passwordField.getText().toString();

            if (p.length() < 6 || p.contains("\\s")) {
                passwordField.setError("Password must be at least 6 characters and must not contain any whitespace");
                return;
            }

            Database db = new Database();
            db.read("customer", successListener, failureListener, false);
        }
    };

    OnSuccessListener<? super Object> successListener = new OnSuccessListener<Object>() {
        @Override
        public void onSuccess(Object retrievedData) {

            HashMap<String, Object> customers;

            String u = usernameField.getText().toString();

            if (retrievedData instanceof HashMap) {
                customers = (HashMap<String, Object>) retrievedData;

                if (customers.containsKey(u)) {
                    usernameField.setError("Username has already been taken");

                } else if (u.matches("\\w{6,}")) {
                    HashMap<String, String> userInfo = new HashMap<>();
                    userInfo.put("password", passwordField.getText().toString());
                    Database db = new Database();
                    db.write("customer/" + u, userInfo, successListener, failureListener);

                    toast.setText("Account creation successful");
                    toast.show();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Current User", u);
                    editor.apply();

                    Intent intent = new Intent(CreateAccount.this, VenueList.class);
                    startActivity(intent);

                } else {
                    usernameField.setError("Username must be at least 6 characters");
                }
            }
        }
    };

    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("Write Data", "Error while writing account data");
        }
    };

}
