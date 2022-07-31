package com.example.softsignproj.addVenue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.softsignproj.Database;
import com.example.softsignproj.R;


public class AddVenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);// commented this out to get rid of error
        Database f = new Database();
        CreateVenue.setDatabase(f);
    }

    public void onEnter(View view){
        if (view.getId() == R.id.addVenueNextButton){

            String venueName = ((EditText)findViewById(R.id.newVenueName)).getText().toString().trim();
            System.out.println("New Venue " + venueName);
            if (!venueName.equals("")){
                System.out.println("New Venue " + venueName);
                CreateVenue.setVenueName(venueName);
                Intent intent = new Intent(this, AddVenueEnterSports.class);

                this.startActivity(intent);
            }
        }
    }
}