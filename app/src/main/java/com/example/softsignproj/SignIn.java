package com.example.softsignproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    private DatabaseReference db;
    private EditText usernameField, passwordField;
    private Button signUpButton;
    private boolean adminMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        db = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com").getReference();

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        Button signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(clickListener1);
        signUpButton.setOnClickListener(clickListener2);

        adminMode = false;
        SwitchMaterial adminToggle = findViewById(R.id.adminToggle);
        adminToggle.setOnCheckedChangeListener(changeListener);
    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db.addValueEventListener(eventListener);
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

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataSnapshot snapshot;

            String u = usernameField.getText().toString();
            String p = passwordField.getText().toString();

            if (adminMode) {
                snapshot = dataSnapshot.child("administrator").child(u);
            } else {
                snapshot = dataSnapshot.child("customer").child(u);
            }

            if (snapshot.exists()) {

                Object retrievedData = snapshot.getValue();
                HashMap<String, Object> userInfo;

                if (retrievedData instanceof HashMap) {
                    userInfo = (HashMap<String, Object>) retrievedData;

                    if (userInfo.containsKey("password") && userInfo.get("password").toString().equals(p)) {
                        Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();

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

            } else {
                Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
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