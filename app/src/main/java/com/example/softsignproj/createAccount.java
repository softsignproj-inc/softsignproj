package com.example.softsignproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.softsignproj.data.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createAccount extends AppCompatActivity {
    private DatabaseReference customers;
    private EditText usernameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        customers = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com").getReference("customers");

        usernameField = findViewById(R.id.newUsernameField);
        passwordField = findViewById(R.id.newPasswordField);

        Button createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            customers.addValueEventListener(eventListener);
        }
    };

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String u = usernameField.getText().toString();
            String p = passwordField.getText().toString();

            if (dataSnapshot.hasChild(u)) {
                Log.e("createAccount.java", "Username has already been taken");
                return;
            }

            if (u.matches("\\w{6,}") && p.length() >= 6 && !p.contains("\\s")) {
                Customer c = new Customer(p);
                Database db = new Database();
                db.write("customers/" + u, c, successListener, failureListener);
            } else {
                Log.e("createAccount.java", "Invalid entry");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
        }
    };

    OnSuccessListener<? super Object> successListener = new OnSuccessListener<Object>() {
        @Override
        public void onSuccess(Object o) {
            Intent intent = new Intent(createAccount.this, HomePage.class);
            startActivity(intent);
        }
    };

    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("Write Data", "Error while writing account data");
        }
    };

}