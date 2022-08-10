package com.example.softsignproj.myEvents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softsignproj.eventList.EventList;
import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.PageHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.SignIn;
import com.example.softsignproj.model.Event;
import com.example.softsignproj.model.EventComparator;
import com.example.softsignproj.venueList.VenueList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class MyJoinedEvents extends AppCompatActivity implements PageHandler {

    private ArrayList<Event> myJoinedEvents;
    private ArrayList<Event> allEvents;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference joinedEventsRef;
    DatabaseReference eventsRef;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_joined_events);

        Button scheduledEventsButton = findViewById(R.id.scheduledEventsButton);
        scheduledEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyJoinedEvents.this, MyScheduledEvents.class);
                startActivity(intent);
            }
        });

        SharedPreferences shared = MyJoinedEvents.this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String user = shared.getString("Current User", "");

        myJoinedEvents = new ArrayList<Event>();
        allEvents = new ArrayList<Event>();

        firebaseDatabase = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/");
        eventsRef = eventsRef = firebaseDatabase.getReference("event");
        eventsRef.addValueEventListener(eventListener2);
        joinedEventsRef = firebaseDatabase.getReference("customer").child(user).child("joinedEvents");
        joinedEventsRef.addValueEventListener(eventListener);

    }

    ValueEventListener eventListener2 = new ValueEventListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            allEvents.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                int currCount = Integer.parseInt(String.valueOf(snapshot.child("currCount").getValue()));
                int maxCount = Integer.parseInt(String.valueOf(snapshot.child("maxCount").getValue()));
                String sport = (String) snapshot.child("sport").getValue();
                String venue = (String) snapshot.child("venue").getValue();
                LocalDateTime startTime = LocalDateTime.parse((String) snapshot.child("startTime").getValue(), formatter);
                LocalDateTime endTime = LocalDateTime.parse((String) snapshot.child("endTime").getValue(), formatter);

                allEvents.add(new Event(snapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("firebase", "Error getting data", error.toException());
        }
    };

    ValueEventListener eventListener = new ValueEventListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            myJoinedEvents.clear();

            for (DataSnapshot child : snapshot.getChildren()) {
                if(child.exists()&&!String.valueOf(child.getValue()).equals("")) {
                    String eventNum = String.valueOf(child.getValue());

                    for (int i = 0; i < allEvents.size(); i++) {
                        if (allEvents.get(i).getId().equals(eventNum)) {
                            myJoinedEvents.add(allEvents.get(i));
                            break;
                        }
                    }
                }
            }

            if (!myJoinedEvents.isEmpty() && myJoinedEvents.get(0) != null) {

                Collections.sort(myJoinedEvents, new EventComparator());

                RecyclerView recyclerView = findViewById(R.id.eventRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyJoinedEvents.this));

                EventAdapter eventAdapter = new EventAdapter(myJoinedEvents, joinedEventsListener);
                recyclerView.setAdapter(eventAdapter);

                TextView noEvent = findViewById(R.id.noEvent);

                recyclerView.setVisibility(View.VISIBLE);
                noEvent.setVisibility(View.GONE);

            }
            else {

                Event e = new Event("", 0, 0, LocalDateTime.of(2000,1,1,1,0),
                        LocalDateTime.of(2000,1,1,1,1), "", "");

                myJoinedEvents.add(e);

                RecyclerView recyclerView = findViewById(R.id.eventRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyJoinedEvents.this));

                EventAdapter eventAdapter = new EventAdapter(myJoinedEvents, joinedEventsListener);
                recyclerView.setAdapter(eventAdapter);

                TextView noEvent = findViewById(R.id.noEvent);

                recyclerView.setVisibility(View.GONE);
                noEvent.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("firebase", "Error getting data", error.toException());
        }

    };

    EventAdapter.EventClickListener joinedEventsListener = new EventAdapter.EventClickListener() {

        @Override
        public void onEventClick(View view) {
            Log.e("filler", "filler");
        }

    };

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

        if (itemId == R.id.seeEventListButton) {
            Intent intent = new Intent(this, EventList.class);
            startActivity(intent);
        }

        if (itemId == R.id.seeScheduledEventsButton) {
            Intent intent = new Intent(this, MyScheduledEvents.class);
            startActivity(intent);
        }

        if (itemId == R.id.signOutButton) {
            SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.preference_file_key), this.MODE_PRIVATE);
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