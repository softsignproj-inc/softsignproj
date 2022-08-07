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

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventsPage extends AppCompatActivity {
    private RecyclerView eventsView;
    private HashMap<String, Event> events = new HashMap<String, Event>();
    private ArrayList<Event> eventsList = new ArrayList<>();
    private ArrayList<Event> temp = new ArrayList<>();
    private EventsAdapter eventsAdapter = new EventsAdapter(this, eventsList, "username1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);
        eventsView = findViewById(R.id.recyclerView);

        System.out.println("Everything initialized");

        eventsView.setAdapter(eventsAdapter);
        eventsView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("event");
        ref.getDatabase();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                temp.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    // Basic information
                    int currCount = Integer.parseInt(String.valueOf(snapshot.child("currCount").getValue()));
                    int maxCount = Integer.parseInt(String.valueOf(snapshot.child("maxCount").getValue()));
                    String sport = (String) snapshot.child("sport").getValue();
                    String venue = (String) snapshot.child("venue").getValue();
                    LocalDateTime startTime = LocalDateTime.parse((String) snapshot.child("startTime").getValue(), formatter);
                    LocalDateTime endTime = LocalDateTime.parse((String) snapshot.child("endTime").getValue(), formatter);
                    HashMap<String, String> participants = new HashMap<String, String>();
                    System.out.println(snapshot.child("participants").getValue());
                    // Get list of participants
                    DatabaseReference usersRef = ref.child(snapshot.getKey()).child("participants");
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                                System.out.println("Child");
                                System.out.println(childSnapshot.getValue());
                                participants.put(childSnapshot.getKey(), (String) childSnapshot.getValue());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // Initialize linked hash map with all fetched events
                    events.put(snapshot.getKey(), new Event(snapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue, participants));
                    // Convert to ArrayList with same order, and push to eventsAdapter
                    temp = new ArrayList<>(events.values());
                    eventsAdapter.updateEventsList(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClick(View view) {

    }
}
