package com.example.softsignproj.venueList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.Database;
import com.example.softsignproj.eventList.EventList;
import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.myEvents.MyJoinedEvents;
import com.example.softsignproj.PageHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.myEvents.MyScheduledEvents;
import com.example.softsignproj.SignIn;
import com.example.softsignproj.model.Venue;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VenueList extends AppCompatActivity implements PageHandler {

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
        MenuItem it = MenuHandler.onOptionsItemSelected(item, findViewById(R.id.drawer_layout), findViewById(R.id.navigationView), (PageHandler)this);
        return super.onOptionsItemSelected(it);
    }

    @Override
    public void openPage(int itemId) {
        if (itemId == R.id.seeEventListButton) {
            Intent intent = new Intent(this, EventList.class);
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
