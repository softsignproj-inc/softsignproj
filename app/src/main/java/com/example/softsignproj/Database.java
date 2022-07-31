package com.example.softsignproj;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Object retrievedData;


    public Database(){
        database = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/");
        ref = database.getReference();
    }

    public void write(String path, Object value, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure){
        ref.child(path).setValue(value).addOnSuccessListener(onSuccess).addOnFailureListener(onFailure);
    }

    public void read(String path, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure){
        DatabaseReference ref = database.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child(path);
                Object value = data.getValue();
                retrievedData = value;
                if (retrievedData != null) {
                    onSuccess.onSuccess(retrievedData);
                } else {
                    Log.e("Database", "Retrieved data is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Exception e = databaseError.toException();
                onFailure.onFailure(e);
            }
        });
    }

    public void delete(String path, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure){
        ref.child(path).removeValue().addOnSuccessListener(onSuccess).addOnFailureListener(onFailure);
    }

    public Object getRetrievedData(){
        return retrievedData;
    }

}

