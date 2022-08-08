package com.example.softsignproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button seeAllVenues = findViewById(R.id.venueListButton);
        seeAllVenues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, VenueList.class);
                startActivity(intent);
            }
        });

        Button myEvents = findViewById(R.id.myEventsButton);
        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MyEvents.class);
                startActivity(intent);
            }
        });
    }
}