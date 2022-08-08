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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AdminEventPageActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> display_venues;
    private ArrayList<Event> all_events = new ArrayList<>();
    private ArrayList<Event> temp_events;
    private ArrayList<Event> display_events;
    private MyAdapter myAdapter = new MyAdapter(this, all_events);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_page);

        display_venues = new ArrayList<>();
        display_venues.add("All");

        //all_events = new ArrayList<>();
        temp_events = new ArrayList<>();
        display_events = new ArrayList<>();

        System.out.println("Outside listner");
        System.out.println(all_events.toString());

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, display_venues); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //MyAdapter myAdapter = new MyAdapter(this, all_events);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("End");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("venue");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                display_venues.clear();
                display_venues.add("All");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    display_venues.add(snapshot.getKey());
                }
                System.out.println("In venues");
                //myAdapter.updateEventsList(all_events);
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
                System.out.println("Entered Data change");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("Entered loop");
                    System.out.println(snapshot.getKey());
                    int currCount = Integer.parseInt(String.valueOf(snapshot.child("currCount").getValue()));
                    int maxCount = Integer.parseInt(String.valueOf(snapshot.child("maxCount").getValue()));
                    String sport = (String) snapshot.child("sport").getValue();
                    String venue = (String) snapshot.child("venue").getValue();
                    LocalDateTime startTime = LocalDateTime.parse((String) snapshot.child("startTime").getValue(), formatter);
                    LocalDateTime endTime = LocalDateTime.parse((String) snapshot.child("endTime").getValue(), formatter);

                    //String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue
                    temp_events.add(new Event(snapshot.getKey(), currCount, maxCount, startTime, endTime, sport, venue));
                }

                //System.out.println("Exited loop");
                //System.out.println(all_events.toString());

                //temp_events.addAll(all_events);
                myAdapter.updateEventsList(temp_events);
                myAdapter.notifyDataSetChanged();
                System.out.println(all_events.toString());
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

        //temp.clear();
        for (Event e:temp_events) {
            if (e.venue.equals(text)) {
                temp.add(e);
            }
        }

        myAdapter.updateEventsList(temp);
        myAdapter.notifyDataSetChanged();
        System.out.println("Filter test");
        System.out.println(temp_events.toString());

        //RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //MyAdapter myAdapter = new MyAdapter(this, all_events);

        /*recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
