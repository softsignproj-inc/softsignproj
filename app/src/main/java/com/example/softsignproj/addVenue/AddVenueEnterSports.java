package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softsignproj.R;
import com.example.softsignproj.Sport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVenueEnterSports extends AppCompatActivity {

    private ArrayList<Sport> selectedSports;
    private RecyclerView sportsRecyclerView;
    private AddVenueRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue_select_sports);
        selectedSports = new ArrayList<Sport>();
        createSportsList();
    }

    private void createSportsList(){
        sportsRecyclerView = findViewById(R.id.addVenueEnterSportsRecyclerView);
        adapter = new AddVenueRecyclerAdapter(selectedSports);
        adapter.setOnRemove(new AddVenueOnRemove(this, selectedSports, adapter));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        sportsRecyclerView.setLayoutManager(layoutManager);
        sportsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sportsRecyclerView.setAdapter(adapter);
    }

    public void enterSport(View view){
        EditText inputField = (EditText)findViewById(R.id.addVenueEnterSport);
        String sportName = inputField.getText().toString().trim();
        Pattern venueNamePattern = Pattern.compile("[\\w|\\s]+");
        Matcher matcher = venueNamePattern.matcher(sportName);

        Sport sport = new Sport(sportName);
        if (!sportName.equals("")){
            if (!matcher.matches()){
                ((TextView) findViewById(R.id.addVenueErrorPlaceholder2)).setText("Sport names may only contain letters, digits and whitespaces");
                return;
            }

            if (selectedSports.contains(sport)){
                ((TextView) findViewById(R.id.addVenueErrorPlaceholder2)).setText(sport.getName() + " already added");
                return;
            }
            selectedSports.add(0, sport);
            adapter.notifyItemInserted(0);
            System.out.println(" sport entered " + sport);
            sportsRecyclerView.scrollToPosition(0);
            inputField.setText("");
            update();
        }
    }

    public void update(){
        ((TextView) findViewById(R.id.addVenueErrorPlaceholder2)).setText("");
        ((TextView)findViewById(R.id.addVenueNumberOfSports)).setText(String.valueOf(selectedSports.size()));
    }

    public void createVenue(View view){

        System.out.println("firebase stuff");

        ArrayList<String> sportsStringList = new ArrayList<String>();
        for (int i = 0; i < selectedSports.size(); i++){
            sportsStringList.add(selectedSports.get(i).getName());
        }

        Collections.sort(sportsStringList);
        CreateVenue.setSports(sportsStringList);

        Intent intent = new Intent(this, AddVenue.class);

        CreateVenue.writeToDatabase(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object s) {
                System.out.println("Success writing to db");
                startActivity(intent);
                Toast.makeText(AddVenueEnterSports.this, "Successfully added venue: " + CreateVenue.getVenueName(), Toast.LENGTH_SHORT).show();
            }

        }, new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure writing to db");
                e.printStackTrace();
            }
        });
    }
}