package com.example.softsignproj;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.adapter.VenueAdapter;
import com.example.softsignproj.data.model.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VenueList extends AppCompatActivity {

    private ArrayList<Venue> venues;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        venues = new ArrayList<>();
        database = new Database();
        database.read("venue", new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                HashMap<String, HashMap> readVenues = (HashMap<String, HashMap>) o;
                for (HashMap v : readVenues.values()) {
                    venues.add(new Venue(v));
                }
                if (venues != null){
                    RecyclerView recycler = findViewById(R.id.venueRecycler);
                    recycler.setLayoutManager(new LinearLayoutManager(VenueList.this));

                    VenueAdapter venueAdapter = new VenueAdapter(venues);
                    recycler.setAdapter(venueAdapter);
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("VenueList", "database reading failure");
            }
        }, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return MenuHandler.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem it = MenuHandler.onOptionsItemSelected(item, this, false);
        return super.onOptionsItemSelected(it);
    }
}
