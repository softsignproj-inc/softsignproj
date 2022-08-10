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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softsignproj.Database;
import com.example.softsignproj.MenuHandler;
import com.example.softsignproj.R;
import com.example.softsignproj.data.model.Sport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddVenue extends AppCompatActivity {

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
                    //((TextView) findViewById(R.id.addVenueErrorPlaceholder)).setText("Name may only contain letters, digits and whitespaces");
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
                            ((TextView) findViewById(R.id.addVenueNameErrorPlaceholder)).setText("Error reading from database.");
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
                System.out.println("Failure writing to db");
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
        MenuItem it = MenuHandler.onOptionsItemSelected(item, this, true);
        return super.onOptionsItemSelected(it);
    }
}