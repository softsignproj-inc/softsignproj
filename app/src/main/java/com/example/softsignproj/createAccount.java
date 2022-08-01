package com.example.softsignproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softsignproj.data.Customer;
import com.example.softsignproj.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;

public class createAccount extends AppCompatActivity {
    private DatabaseReference customers;
    private EditText usernameField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        customers = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com").getReference("customer");

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
                Toast.makeText(createAccount.this, "Username has already been taken", Toast.LENGTH_SHORT).show();
            } else {
                if (u.matches("\\w{6,}") && p.length() >= 6 && !p.contains("\\s")) {
                    HashMap<String, String> userInfo = new HashMap<>();
                    userInfo.put("password", p);
                    Database db = new Database();
                    db.write("customer/" + u, userInfo, successListener, failureListener);

                    Toast.makeText(createAccount.this, "Account creation successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(createAccount.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
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