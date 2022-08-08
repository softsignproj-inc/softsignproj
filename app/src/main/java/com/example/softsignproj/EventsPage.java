package com.example.softsignproj;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.model.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class EventsPage extends AppCompatActivity {
    private RecyclerView eventsView;
    private HashMap<String, Event> events = new HashMap<String, Event>();
    private EventsAdapter eventsAdapter = new EventsAdapter(this, "username1");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);
        eventsView = findViewById(R.id.recyclerView);

        System.out.println("Everything initialized");

        eventsView.setAdapter(eventsAdapter);
        eventsView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("event");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int currCount = Integer.parseInt(String.valueOf(dataSnapshot.child("currCount").getValue()));
                int maxCount = Integer.parseInt(String.valueOf(dataSnapshot.child("maxCount").getValue()));
                String sport = (String) dataSnapshot.child("sport").getValue();
                String venue = (String) dataSnapshot.child("venue").getValue();
                LocalDateTime startTime = LocalDateTime.parse((String) dataSnapshot.child("startTime").getValue(), formatter);
                LocalDateTime endTime = LocalDateTime.parse((String) dataSnapshot.child("endTime").getValue(), formatter);
                HashMap<String, String> participants = new HashMap<String, String>();
                System.out.println(dataSnapshot.child("participants").getValue());

                // Initialize linked hash map with all fetched events
                events.put(dataSnapshot.getKey(), new Event(dataSnapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue, participants));

                DatabaseReference usersRef = ref.child(dataSnapshot.getKey()).child("participants");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            // System.out.println("Child");
                            System.out.println("In on child added: " + childSnapshot.getValue());
                            participants.put(childSnapshot.getKey(), (String) childSnapshot.getValue());
                        }
                        events.get(dataSnapshot.getKey()).setParticipants(participants);
                        eventsAdapter.addEvent(events.get(dataSnapshot.getKey()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                System.out.println("In on child added:" + participants);
                // Convert to ArrayList with same order, and push to eventsAdapter
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int currCount = Integer.parseInt(String.valueOf(dataSnapshot.child("currCount").getValue()));
                int maxCount = Integer.parseInt(String.valueOf(dataSnapshot.child("maxCount").getValue()));
                String sport = (String) dataSnapshot.child("sport").getValue();
                String venue = (String) dataSnapshot.child("venue").getValue();
                LocalDateTime startTime = LocalDateTime.parse((String) dataSnapshot.child("startTime").getValue(), formatter);
                LocalDateTime endTime = LocalDateTime.parse((String) dataSnapshot.child("endTime").getValue(), formatter);
                HashMap<String, String> participants = new HashMap<String, String>();
                System.out.println(dataSnapshot.child("participants").getValue());

                // Initialize linked hash map with all fetched events
                Event event = events.get(dataSnapshot.getKey());
                event.setCurCount(currCount);
                event.setMaxCount(maxCount);
                event.setSport(sport);
                event.setVenue(venue);
                event.setStartTime(startTime);
                event.setEndTime(endTime);

                DatabaseReference usersRef = ref.child(dataSnapshot.getKey()).child("participants");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            // System.out.println("Child");
                            System.out.println("In onDataChange/onChildChanged " + childSnapshot.getValue());
                            participants.put(childSnapshot.getKey(), (String) childSnapshot.getValue());
                        }
                        event.setParticipants(participants);
                        eventsAdapter.updateEvent(event);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                eventsAdapter.removeEvent(events.get(dataSnapshot.getKey()));
                events.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickEvent(View view) {

    }
}
