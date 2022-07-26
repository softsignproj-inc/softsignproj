package com.example.softsignproj.eventList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.PageHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.SignIn;
import com.example.softsignproj.model.Event;
import com.example.softsignproj.myEvents.MyJoinedEvents;
import com.example.softsignproj.myEvents.MyScheduledEvents;
import com.example.softsignproj.venueList.VenueList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class EventList extends AppCompatActivity implements PageHandler {
    private final HashMap<String, Event> events = new HashMap<String, Event>();
    private EventListAdapter eventsAdapter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        RecyclerView eventsView = findViewById(R.id.recyclerView);
        SharedPreferences sharedPref = getSharedPreferences("com.example.softsignproj.customer_file", Context.MODE_PRIVATE);
        eventsAdapter = new EventListAdapter(this, sharedPref.getString("Current User", "DEFAULT"));
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

                // Initialize linked hash map with all fetched events
                events.put(dataSnapshot.getKey(), new Event(dataSnapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue, participants));

                DatabaseReference usersRef = ref.child(Objects.requireNonNull(dataSnapshot.getKey())).child("participants");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            participants.put(childSnapshot.getKey(), (String) childSnapshot.getValue());
                        }
                        events.get(dataSnapshot.getKey()).setParticipants(participants);
                        eventsAdapter.addEvent(events.get(dataSnapshot.getKey()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("warning", "Could not read from Realtime DB", databaseError.toException());
                    }
                });
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

                // Initialize linked hash map with all fetched events
                Event event = events.get(dataSnapshot.getKey());
                assert event != null;
                event.setCurCount(currCount);
                event.setMaxCount(maxCount);
                event.setSport(sport);
                event.setVenue(venue);
                event.setStartTime(startTime);
                event.setEndTime(endTime);

                DatabaseReference usersRef = ref.child(Objects.requireNonNull(dataSnapshot.getKey())).child("participants");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            participants.put(childSnapshot.getKey(), (String) childSnapshot.getValue());
                        }
                        event.setParticipants(participants);
                        eventsAdapter.updateEvent(event);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("warning", "Could not read from Realtime DB", databaseError.toException());
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
                // Not relevant, used only if data is fetched using orderByChild or orderByValue
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "Could not read from Realtime DB", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return MenuHandler.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem it = MenuHandler.onOptionsItemSelected(item, findViewById(R.id.drawer_layout), findViewById(R.id.navigationView), (PageHandler)this);
        return super.onOptionsItemSelected(it);
    }

    @Override
    public void openPage(int itemId) {
        if (itemId == R.id.seeVenueListButton) {
            Intent intent = new Intent(this, VenueList.class);
            startActivity(intent);
        }

        if (itemId == R.id.seeJoinedEventsButton) {
            Intent intent = new Intent(this, MyJoinedEvents.class);
            startActivity(intent);
        }

        if (itemId == R.id.seeScheduledEventsButton) {
            Intent intent = new Intent(this, MyScheduledEvents.class);
            startActivity(intent);
        }

        if (itemId == R.id.signOutButton) {
            SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.preference_file_key), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, SignIn.class);
            this.startActivity(intent);

            Toast toast = Toast.makeText(this, "You have been signed out", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}