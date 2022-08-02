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

    private ArrayList<Venue> venues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        venues = new ArrayList<>();
        Database db = new Database();
        DatabaseReference venueList = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/").getReference("venue");
        venueList.addValueEventListener(eventListener);
    }

    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Object obj = child.getValue();

                if (obj instanceof HashMap) {
                    HashMap<String, Object> venueInfo = (HashMap<String, Object>)obj;

                    ArrayList<String> sports = new ArrayList<>();
                    ArrayList<String> events = new ArrayList<>();

                    if (venueInfo.containsKey("sports") && venueInfo.get("sports") instanceof ArrayList) {
                        sports = (ArrayList<String>)(venueInfo.get("sports"));
                    }

                    if (venueInfo.containsKey("events") && venueInfo.get("events") instanceof ArrayList) {
                        events = (ArrayList<String>)(venueInfo.get("events"));
                    }

                    Venue v = new Venue(child.getKey(), sports, events);
                    venues.add(v);

                }
            }

            if (venues != null) {

                RecyclerView recyclerView = findViewById(R.id.venueRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(VenueList.this));

                VenueAdapter venueAdapter = new VenueAdapter(venues, venueListener);
                recyclerView.setAdapter(venueAdapter);

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
            Log.e("HELLO", "PUT REDIRECTION HERE");
        }
    };
}