package com.example.softsignproj;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.model.Event;

import java.util.ArrayList;

public class EventsPage extends AppCompatActivity {
    RecyclerView eventsView;
    ArrayList<Event> events = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);
        eventsView = findViewById(R.id.recyclerView);

    }



    public void onClick(View view) {

    }
}
