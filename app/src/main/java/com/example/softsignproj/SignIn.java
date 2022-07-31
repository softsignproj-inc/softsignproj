package com.example.softsignproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softsignproj.data.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private DatabaseReference customers;
    private EditText usernameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        customers = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com").getReference("customers");

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(clickListener1);
        signUpButton.setOnClickListener(clickListener2);
    }

    View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            customers.addValueEventListener(eventListener);
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
        public void onDataChange(DataSnapshot dataSnapshot) {
            DataSnapshot snapshot = dataSnapshot.child(usernameField.getText().toString());

            if (snapshot.exists()) {
                Customer customer = snapshot.getValue(Customer.class);

                if (customer != null && passwordField.getText().toString().equals(customer.getPassword())) {
                    Intent intent = new Intent(SignIn.this, HomePage.class);
                    startActivity(intent);
                } else {
                    Log.e("Sign in", "Incorrect password");
                }
            } else {
                Log.e("Sign in", "Username does not exist");
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
        }
    };
}