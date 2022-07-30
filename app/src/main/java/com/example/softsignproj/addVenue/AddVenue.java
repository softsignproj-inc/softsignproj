package com.example.softsignproj.addVenue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softsignproj.R;


public class AddVenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);// commented this out to get rid of error
    }

    public void onEnter(View view){
        if (view.getId() == R.id.addVenueNextButton){
            Intent intent = new Intent(this, AddVenueEnterSports.class);

            this.startActivity(intent);
        }
    }
}