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
    private Database db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        db = new Database();
        CreateVenue.setDatabase(db);
        db2 = new Database();

    }

    public void onEnter(View view){
        if (view.getId() == R.id.addVenueNextButton){

            String venueName = ((EditText)findViewById(R.id.addVenueNameInput)).getText().toString();
            System.out.println("New Venue " + venueName);
            if (!venueName.equals("")) {

                Pattern venueNamePattern = Pattern.compile("[A-Za-z\\d\\s]+");
                Matcher matcher = venueNamePattern.matcher(venueName);
                if (!matcher.matches()) {
                    ((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText("Name may only contain letters, digits and whitespaces");
                } else if (venueName.equalsIgnoreCase("sports") || venueName.equalsIgnoreCase("events")){
                    ((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText("Name cannot be 'sports' or 'events'");
                } else {
                    System.out.println("New Venue " + venueName);
                    Intent intent = new Intent(this, AddVenueEnterSports.class);

                    db.read("venue", new OnSuccessListener<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            HashMap<String, Venue> listOfVenues = (HashMap<String, Venue>) o;
                            if (!listOfVenues.containsKey(venueName)) {
                                CreateVenue.setVenueName(venueName);
                                Database db2 = new Database();
                                db2.write("testtesttest", "testo" + venueName, new OnSuccessListener<Object>() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        System.out.println("TESTETSTESTTSETEST");
                                    }
                                }, null);
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
                    }, false);
                }
            }
        }
    }
}