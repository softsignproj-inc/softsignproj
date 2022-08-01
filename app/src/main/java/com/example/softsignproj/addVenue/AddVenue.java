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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


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
                System.out.println("New Venue " + venueName);
                Intent intent = new Intent(this, AddVenueEnterSports.class);

                db.read("venue/" + venueName, new OnSuccessListener<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        if (o == null){
                            CreateVenue.setVenueName(venueName);
                            startActivity(intent);
                        } else {
                            ((TextView)findViewById(R.id.addVenueErrorPlaceholder)).setText(venueName + " already exists.");
                        }
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ((TextView)findViewById(R.id.addVenueErrorPlaceholder)).setText("Error reading from database.");
                    }
                });
            }
        }
    }
}