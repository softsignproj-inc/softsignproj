package com.example.softsignproj;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.softsignproj.adapter.EventAdapter;
import com.example.softsignproj.data.Customer;
import com.example.softsignproj.data.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class MyEvents extends AppCompatActivity {

    private ArrayList<Event> myJoinedEvents;
    private ArrayList<Event> myScheduledEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        myJoinedEvents = new ArrayList<Event>();
        myScheduledEvents = new ArrayList<Event>();
        DatabaseReference joinedEventList = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/").getReference("customer/username1/joinedEvents");
        DatabaseReference scheduledEventList = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/").getReference("customer/username1/scheduledEvents");
        joinedEventList.addValueEventListener(eventListener1);
        scheduledEventList.addValueEventListener(eventListener2);
    }

    ValueEventListener eventListener1 = new ValueEventListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot child : snapshot.getChildren()) {
                Object obj = child.getValue();

                if (obj instanceof HashMap) {
                    HashMap<String, Object> eventInfo = (HashMap<String, Object>)obj;
                    Log.e("testy",obj.toString());
                    String eventName = obj.toString();
                    String venue = "";
                    int currCount = 0;
                    int maxCount = 0;
                    String sport = "";
                    LocalDateTime startTime = null;
                    LocalDateTime endTime = null;
                    ArrayList<Customer> participants = new ArrayList<Customer>();

                    if (eventInfo.containsKey("venue") && eventInfo.get("venue") instanceof String) {
                        venue = (String)eventInfo.get("venue");
                    }
                    if (eventInfo.containsKey("currCount") && eventInfo.get("currCount") instanceof Integer) {
                        currCount = (int)eventInfo.get("currCount");
                    }
                    if (eventInfo.containsKey("currCount") && eventInfo.get("currCount") instanceof Integer) {
                        currCount = (int)eventInfo.get("currCount");
                    }
                    if (eventInfo.containsKey("sport") && eventInfo.get("sport") instanceof String) {
                        sport = (String)eventInfo.get("sport");
                    }
                    if (eventInfo.containsKey("startTime") && eventInfo.get("startTime") instanceof LocalDateTime) {
                        startTime = (LocalDateTime)eventInfo.get("startTime");
                    }
                    if (eventInfo.containsKey("endTime") && eventInfo.get("endTime") instanceof LocalDateTime) {
                        endTime = (LocalDateTime)eventInfo.get("endTime");
                    }
                    if (eventInfo.containsKey("participants") && eventInfo.get("participants") instanceof ArrayList) {
                        participants = (ArrayList<Customer>)(eventInfo.get("participants"));
                    }

                    Event e = new Event(eventName,venue,currCount,maxCount,sport,startTime,endTime,participants);
                    myJoinedEvents.add(e);

                }
            }

            if (myJoinedEvents != null) {

                RecyclerView recyclerView = findViewById(R.id.eventRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyEvents.this));

                EventAdapter eventAdapter = new EventAdapter(myJoinedEvents, joinedEventsListener);
                recyclerView.setAdapter(eventAdapter);

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("firebase", "Error getting data", error.toException());
        }

    };

    ValueEventListener eventListener2 = new ValueEventListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot child : snapshot.getChildren()) {
                Object obj = child.getValue();

                if (obj instanceof HashMap) {
                    HashMap<String, Object> eventInfo = (HashMap<String, Object>)obj;

                    String eventName = obj.toString();
                    Log.e("testy",eventName);
                    String venue = "";
                    int currCount = 0;
                    int maxCount = 0;
                    String sport = "";
                    LocalDateTime startTime = null;
                    LocalDateTime endTime = null;
                    ArrayList<Customer> participants = new ArrayList<Customer>();

                    if (eventInfo.containsKey("venue") && eventInfo.get("venue") instanceof String) {
                        venue = (String)eventInfo.get("venue");
                    }
                    if (eventInfo.containsKey("currCount") && eventInfo.get("currCount") instanceof Integer) {
                        currCount = (int)eventInfo.get("currCount");
                    }
                    if (eventInfo.containsKey("currCount") && eventInfo.get("currCount") instanceof Integer) {
                        currCount = (int)eventInfo.get("currCount");
                    }
                    if (eventInfo.containsKey("sport") && eventInfo.get("sport") instanceof String) {
                        sport = (String)eventInfo.get("sport");
                    }
                    if (eventInfo.containsKey("startTime") && eventInfo.get("startTime") instanceof LocalDateTime) {
                        startTime = (LocalDateTime)eventInfo.get("startTime");
                    }
                    if (eventInfo.containsKey("endTime") && eventInfo.get("endTime") instanceof LocalDateTime) {
                        endTime = (LocalDateTime)eventInfo.get("endTime");
                    }
                    if (eventInfo.containsKey("participants") && eventInfo.get("participants") instanceof ArrayList) {
                        participants = (ArrayList<Customer>)(eventInfo.get("participants"));
                    }

                    Event e = new Event(eventName,venue,currCount,maxCount,sport,startTime,endTime,participants);
                    myScheduledEvents.add(e);

                }
            }

            if (myScheduledEvents != null) {

                RecyclerView recyclerView = findViewById(R.id.eventRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyEvents.this));

                EventAdapter eventAdapter = new EventAdapter(myScheduledEvents, scheduledEventsListener);
                recyclerView.setAdapter(eventAdapter);

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

    EventAdapter.EventClickListener scheduledEventsListener = new EventAdapter.EventClickListener() {

        @Override
        public void onEventClick(View view) {
            Log.e("filler", "filler");
        }

    };
}