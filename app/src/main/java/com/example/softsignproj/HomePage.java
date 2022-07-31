package com.example.softsignproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softsignproj.addVenue.AddVenue;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.addVenueButton){
            Intent intent = new Intent(this, AddVenue.class);
            this.startActivity(intent);
        }
    }
}