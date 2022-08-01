package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.softsignproj.Database;
import com.example.softsignproj.R;
import com.example.softsignproj.data.model.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddVenue extends AppCompatActivity {

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        db = new Database();
        CreateVenue.setDatabase(db);
    }

    public void onEnter(View view){
        if (view.getId() == R.id.addVenueNextButton){

            String venueName = ((EditText)findViewById(R.id.addVenueNameInput)).getText().toString().trim();
            System.out.println("New Venue " + venueName);
            if (!venueName.equals("")){

                Pattern venueNamePattern = Pattern.compile("\\w+");
                Matcher matcher = venueNamePattern.matcher(venueName);
                if (!matcher.matches()){
                    ((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText("Venue names may only contain letters, digits and whitespaces");
                } else {
                    System.out.println("New Venue " + venueName);
                    Intent intent = new Intent(this, AddVenueEnterSports.class);

                    db.read("venue", new OnSuccessListener<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            HashMap<String, Venue> listOfVenues = (HashMap<String, Venue>) o;
                            if (!listOfVenues.containsKey(venueName)) {
                                CreateVenue.setVenueName(venueName);
                                startActivity(intent);
                            } else {
                                ((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText(venueName + " already exists.");
                            }
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText("Error reading from database.");
                        }
                    });
                }
            }
        }
    }
}