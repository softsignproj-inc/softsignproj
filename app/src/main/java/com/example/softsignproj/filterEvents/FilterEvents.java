package com.example.softsignproj.filterEvents;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.PageHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.SignIn;
import com.example.softsignproj.addVenue.AddVenue;
import com.example.softsignproj.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FilterEvents extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PageHandler {
    private ArrayList<String> display_venues;
    private ArrayList<Event> all_events = new ArrayList<>();
    private ArrayList<Event> temp_events;
    private ArrayList<Event> display_events;
    private FilterEventsAdapter filterEventsAdapter = new FilterEventsAdapter(this, all_events);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_events);

        display_venues = new ArrayList<>();
        display_venues.add("All");

        temp_events = new ArrayList<>();
        display_events = new ArrayList<>();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, display_venues); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(filterEventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("venue");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                display_venues.clear();
                display_venues.add("All");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    display_venues.add(snapshot.getKey());
                }
                
                spinnerArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("event");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                temp_events.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String sCurrCount = String.valueOf(snapshot.child("currCount").getValue());
                    String sMaxCount = String.valueOf(snapshot.child("maxCount").getValue());
                    String sport = (String) snapshot.child("sport").getValue();
                    String venue = (String) snapshot.child("venue").getValue();
                    String sStartTime = (String) snapshot.child("startTime").getValue();
                    String sEndTime = (String) snapshot.child("endTime").getValue();

                    if (sCurrCount==null || sMaxCount==null || sport==null|| venue==null ||
                            sStartTime==null || sEndTime==null) {
                        continue;
                    }

                    int currCount = Integer.parseInt(sCurrCount);
                    int maxCount = Integer.parseInt(sMaxCount);

                    LocalDateTime startTime = LocalDateTime.parse(sStartTime, formatter);
                    LocalDateTime endTime = LocalDateTime.parse(sEndTime, formatter);

                    temp_events.add(new Event(snapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue));
                }

                filterEventsAdapter.updateEventsList(temp_events);
                filterEventsAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

        ArrayList<Event> temp = new ArrayList<>();

        if (text.equals("All")) {
            temp.addAll(temp_events);
        }

        for (Event e:temp_events) {
            if (e.getVenue().equals(text)) {
                temp.add(e);
            }
        }

        filterEventsAdapter.updateEventsList(temp);
        filterEventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        if (itemId == R.id.addNewVenueButton) {
            Intent intent = new Intent(this, AddVenue.class);
            startActivity(intent);
        }

        if (itemId == R.id.signOutButton) {
            Intent intent = new Intent(this, SignIn.class);
            this.startActivity(intent);

            Toast toast = Toast.makeText(this, "You have been signed out", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
