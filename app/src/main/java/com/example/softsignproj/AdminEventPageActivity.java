package com.example.softsignproj;

import android.app.Activity;
//import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.Event;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//import com.example.softsignproj.data.Administrator;
//import com.example.softsignproj.data.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class AdminEventPageActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> display_venues;
    private ArrayList<Event> all_events;
    private ArrayList<Event> display_events;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_page);

        display_venues = new ArrayList<>();
        display_venues.add("All");

        all_events = new ArrayList<>();
        display_events = new ArrayList<>();


        //String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue
       /* display_events.add(new Event("123", 0, 4, now, now, "Test Sport", "Test Venue"));
        display_events.add(new Event("1234", 1, 4, now, now, "Test Sport 2", "Test Venue 2"));

        display_venues.add("Test Venue");
        display_venues.add("Test Venue 2");*/

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("venue");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                display_venues.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    display_venues.add(snapshot.getKey());
                }
                System.out.println("In venues");
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
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm");
                //all_events.clear();
                System.out.println("Entered Data change");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("Entered loop");
                    System.out.println(snapshot.getKey());
                    //int curCount = (int) snapshot.child("curCount").getValue();
                    //int maxCount = (int) snapshot.child("maxCount").getValue();
                    //String sport = (String) snapshot.child("sport").getValue();
                    //String venue = (String) snapshot.child("venue").getValue();
                    //LocalDateTime startTime = LocalDateTime.parse((String) snapshot.child("startTime").getValue(), formatter);
                    //LocalDateTime endTime = LocalDateTime.parse((String) snapshot.child("endTime").getValue(), formatter);

                    //System.out.println(curCount + " " + maxCount + " " + sport + " " + venue);
                    LocalDateTime now = LocalDateTime.now();
                    //String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue
                    //all_events.add(new Event(snapshot.getKey(), curCount, maxCount, startTime, endTime, sport, venue));
                    //all_events.add(new Event(snapshot.getKey(), (Integer) snapshot.child("currCount").getValue(), (Integer) snapshot.child("maxCount").getValue(),
                    //        now, now, "test", "test"));
                    all_events.add(new Event("1", 1, 2, now, now, "test", "test"));
                }
                System.out.println("Exited loop");
                System.out.println(all_events.toString());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Outside listner");
        all_events.add(new Event("1", 1, 1, now, now, "tes1t", "test1"));
        System.out.println(all_events.toString());

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, display_venues); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(this, all_events);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("End");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(this, all_events);
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
