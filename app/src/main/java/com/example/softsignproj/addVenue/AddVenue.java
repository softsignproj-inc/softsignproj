package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softsignproj.Database;
import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.PageHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.SignIn;
import com.example.softsignproj.filterEvents.FilterEvents;
import com.example.softsignproj.model.Sport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddVenue extends AppCompatActivity implements PageHandler {

    private Database db;
    private ArrayList<Sport> selectedSports;
    private RecyclerView sportsRecyclerView;
    private AddVenueRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        db = new Database();
        CreateVenue.setDatabase(db);
        selectedSports = new ArrayList<Sport>();
        createSportsList();
        Button finishButton = findViewById(R.id.addVenueFinishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinish(view);
            }
        });
        Button enterSportButton = findViewById(R.id.addVenueEnterSportButton);
        enterSportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterSport(view);
            }
        });
    }

    public void onFinish(View view){
        if (view.getId() == R.id.addVenueFinishButton){
            EditText inputField = (EditText)findViewById(R.id.addVenueNameInput);
            String venueName = inputField.getText().toString();
            System.out.println("New Venue " + venueName);
            if (!venueName.equals("")) {

                Pattern venueNamePattern = Pattern.compile("[A-Za-z\\d\\s]+");
                Matcher matcher = venueNamePattern.matcher(venueName);
                if (!matcher.matches()) {
                    inputField.setError("Name may only contain letters, digits and whitespaces");
                } else if (venueName.equalsIgnoreCase("sports") ||
                        venueName.equalsIgnoreCase("events")  ||
                        venueName.equalsIgnoreCase("null")){
                    ((TextView) findViewById(R.id.addVenueNameErrorPlaceholder)).setText("Name cannot be 'null', 'sports' or 'events'");
                } else {
                    System.out.println("New Venue " + venueName);
                    db.read("venue/" + venueName, new OnSuccessListener<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            if (o != null) {
                                ((TextView) findViewById(R.id.addVenueNameErrorPlaceholder)).setText(venueName + " already exists.");
                            } else {
                                System.out.println("Valid venue name");
                                CreateVenue.setVenueName(venueName);
                                onFinishCheckSports();
                            }
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddVenue.this, "There was a problem connecting to the database", Toast.LENGTH_SHORT).show();
                        }
                    }, false);
                }
            }
        }
    }

    private void onFinishCheckSports(){

        System.out.println("firebase stuff");

        if (selectedSports.size() < 1){
            Toast.makeText(getApplicationContext(), "At least one sport must be listed", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<String> sportsStringList = new ArrayList<String>();
        for (int i = 0; i < selectedSports.size(); i++){
            sportsStringList.add(selectedSports.get(i).getName());
        }

        Collections.sort(sportsStringList);
        CreateVenue.setSports(sportsStringList);

        CreateVenue.writeToDatabase(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object s) {
                System.out.println("Success writing to db");
                Toast.makeText(AddVenue.this, "Successfully added venue: " + CreateVenue.getVenueName(), Toast.LENGTH_SHORT).show();
                clear();
                update();
            }

        }, new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddVenue.this, "There was a problem connecting to the database", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private void clear(){
        selectedSports.clear();
        ((EditText)findViewById(R.id.addVenueNameInput)).setText("");
        ((EditText)findViewById(R.id.addVenueEnterSport)).setText("");
        ((TextView) findViewById(R.id.addVenueSportsErrorPlaceholder)).setText("");
        ((TextView) findViewById(R.id.addVenueNameErrorPlaceholder)).setText("");
        CreateVenue.setVenueName(null);
        adapter.notifyDataSetChanged();
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
                ((TextView) findViewById(R.id.addVenueSportsErrorPlaceholder)).setText("Sport names may only contain letters, digits and whitespaces");
                return;
            } else if (sportName.equalsIgnoreCase("null")){
                ((TextView) findViewById(R.id.addVenueSportsErrorPlaceholder)).setText("Sport names cannot be 'null'");
                return;
            }

            if (selectedSports.contains(sport)){
                ((TextView) findViewById(R.id.addVenueSportsErrorPlaceholder)).setText(sport.getName() + " already added");
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
        ((TextView) findViewById(R.id.addVenueSportsErrorPlaceholder)).setText("");
        ((TextView)findViewById(R.id.addVenueNumberOfSports)).setText(String.valueOf(selectedSports.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return MenuHandler.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem it = MenuHandler.onOptionsItemSelected(item, findViewById(R.id.drawer_layout), findViewById(R.id.navigationView), (PageHandler)this);
        return super.onOptionsItemSelected(it);
    }

    @Override
    public void openPage(int itemId) {
        if (itemId == R.id.eventsByVenueButton) {
            Intent intent = new Intent(this, FilterEvents.class);
            startActivity(intent);
        }

        if (itemId == R.id.signOutButton) {
            Intent intent = new Intent(this, SignIn.class);
            this.startActivity(intent);

            Toast toast = Toast.makeText(this, "You have been signed out", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}