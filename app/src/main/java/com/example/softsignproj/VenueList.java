package com.example.softsignproj;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.model.Venue;
import com.example.softsignproj.viewHolder.VenueViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VenueList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    Database database;
    DatabaseReference venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        venueList = FirebaseDatabase.getInstance("https://softsignproj-default-rtdb.firebaseio.com/").getReference();

        recyclerView = (RecyclerView) findViewById(R.id.venue_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadVenues();
    }

    private void loadVenues() {
        Iterable<DataSnapshot> venues = venueList.child("venue").getChildren();
    }

}