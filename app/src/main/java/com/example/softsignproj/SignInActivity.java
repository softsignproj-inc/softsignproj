package com.example.softsignproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.softsignproj.data.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    private DatabaseReference customers;
    private EditText usernameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        customers = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com").getReference("customers");
        customers.addValueEventListener(listener);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        Button signInButton = findViewById(R.id.signInButton);
        Button createAccountButton = findViewById(R.id.createAccountButton);


        signInButton.setOnClickListener(view -> customers.addValueEventListener(listener));

        createAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, createAccount.class);
            startActivity(intent);
        });

    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            DataSnapshot snapshot = dataSnapshot.child(usernameField.getText().toString());

            if (snapshot.exists()) {
                Customer customer = snapshot.getValue(Customer.class);

                if (customer != null && passwordField.getText().toString().equals(customer.getPassword())) {
                    Intent intent = new Intent(SignInActivity.this, HomePage.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
        }
    };
}