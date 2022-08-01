package com.example.softsignproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.softsignproj.data.Admin;
import com.example.softsignproj.data.Customer;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

            if (adminMode) {
                snapshot = dataSnapshot.child("administrators").child(usernameField.getText().toString());
            } else {
                snapshot = dataSnapshot.child("customers").child(usernameField.getText().toString());
            }

            if (snapshot.exists()) {

                if (adminMode) {
                    Admin admin = snapshot.getValue(Admin.class);

                    if (admin != null && passwordField.getText().toString().equals(admin.getPassword())) {
                        Intent intent = new Intent(SignIn.this, AdminPage.class);
                        startActivity(intent);
                    } else {
                        Log.e("Sign in", "Incorrect password");
                    }

                } else {
                    Customer customer = snapshot.getValue(Customer.class);

                    if (customer != null && passwordField.getText().toString().equals(customer.getPassword())) {
                        Intent intent = new Intent(SignIn.this, HomePage.class);
                        startActivity(intent);
                    } else {
                        Log.e("Sign in", "Incorrect password");
                    }
                }

            } else {
                Log.e("Sign in", "User does not exist");
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