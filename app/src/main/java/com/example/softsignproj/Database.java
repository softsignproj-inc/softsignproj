package com.example.softsignproj;

import androidx.annotation.NonNull;

import com.example.softsignproj.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    public void writeEvent(Event event, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure) {

        DatabaseReference pushedEvent = ref.child("temp").push();
        pushedEvent.child("currCount").setValue(event.getCurCount());
        pushedEvent.child("maxCount").setValue(event.getMaxCount());
        pushedEvent.child("sport").setValue(event.getSport());
        pushedEvent.child("venue").setValue(event.getVenue());
        pushedEvent.child("startTime").setValue(event.getStart().toString());
        pushedEvent.child("endTime").setValue(event.getEnd().toString());
        pushedEvent.child("scheduledBy").setValue(event.getScheduledBy());
        DatabaseReference eventParticipants = pushedEvent.child("participants").push();
        eventParticipants.setValue(event.getParticpants().get("1"));

        DatabaseReference fromPath = pushedEvent;
        DatabaseReference toPath = ref.child("event").child(pushedEvent.getKey());

        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            ref.child("temp").removeValue();
                            DatabaseReference eventAddedToVenue = ref.child("venue/" + event.getVenue() + "/events").push();
                            eventAddedToVenue.setValue(pushedEvent.getKey());
                        } else {
                            // Log.d(TAG, "Copy failed!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.d("TAG", firebaseError.getMessage()); //Never ignore potential errors!
            }
        });


    }

    public void read(String path, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure, boolean continuous){
        ref = database.getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child(path);
                Object value = data.getValue();
                retrievedData = value;
                onSuccess.onSuccess(retrievedData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Exception e = databaseError.toException();
                onFailure.onFailure(e);
            }
        };
        if (continuous){
            ref.addValueEventListener(valueEventListener);
        } else {
            ref.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    public void delete(String path, OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure){
        ref.child(path).removeValue().addOnSuccessListener(onSuccess).addOnFailureListener(onFailure);
    }

    public Object getRetrievedData(){
        return retrievedData;
    }

}

