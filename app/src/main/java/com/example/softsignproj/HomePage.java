package com.example.softsignproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softsignproj.addVenue.*;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void onClick(View view) {
        /*
        if (view.getId().equals(R.id.venueListButton))
        Intent intent = new Intent(this, Venue.class);
        startActivity(intent);
        */
        if (view.getId() == R.id.addVenueButton){
            this.startActivity(new Intent(HomePage.this, AddVenue.class));
        }
    }
}