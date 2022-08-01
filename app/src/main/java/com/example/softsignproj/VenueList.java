package com.example.softsignproj;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.adapter.VenueAdapter;
import com.example.softsignproj.data.model.Venue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VenueList extends AppCompatActivity {

    ArrayList<Venue> venues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        venues = new ArrayList<>();
        DatabaseReference venueList = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/").getReference("venues");
        venueList.addValueEventListener(eventListener);
    }

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
            if (map != null) {
                for (String key : map.keySet()) {

                    Log.e("info", "" + key);

                    Object obj = map.get(key);
                    HashMap<String, String> venueInfo;
                    if (obj instanceof HashMap) {
                        venueInfo = (HashMap<String, String>) obj;
                    } else {
                        Log.w("error", "Wrong venue info");
                        return;
                    }

                    if (venueInfo.containsKey("sports") && venueInfo.containsKey("events")) {
                        ArrayList<String> sports, events;

                        if ((Object)venueInfo.get("sports") instanceof ArrayList
                                && (Object)venueInfo.get("events") instanceof  ArrayList) {
                            sports = (ArrayList<String>)((Object)venueInfo.get("sports"));
                            events = (ArrayList<String>)((Object)venueInfo.get("events"));

                            Venue v = new Venue(key, sports, events);
                            venues.add(v);
                        }
                    }
                }

                if (venues != null) {

                    RecyclerView recyclerView = findViewById(R.id.venueRecycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(VenueList.this));

                    VenueAdapter venueAdapter = new VenueAdapter(venues, venueListener);
                    recyclerView.setAdapter(venueAdapter);

                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
        }

    };

    VenueAdapter.VenueClickListener venueListener = new VenueAdapter.VenueClickListener() {
        @Override
        public void onVenueClick(View view) {
            Log.e("HELLO", "YAYYYYY");
        }
    };
}