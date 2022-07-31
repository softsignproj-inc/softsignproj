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

import com.example.softsignproj.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class AddVenueEnterSports extends AppCompatActivity {

    public static AddVenueEnterSports activity;
    private AddVenueListManager<String> selectedSports;
    private RecyclerView sportsRecyclerView;
    private AddVenueRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddVenueEnterSports.activity = this;
        setContentView(R.layout.activity_add_venue_select_sports);
        selectedSports = new AddVenueListManager<String>();
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
        String sport = inputField.getText().toString().trim();
        if (!sport.equals("")){
            selectedSports.add(0, sport);
            adapter.notifyItemInserted(0);
            System.out.println(" sport entered " + sport);
            sportsRecyclerView.scrollToPosition(0);
            inputField.setText("");
            update();
        }
    }

    public void update(){
        ((TextView)findViewById(R.id.addVenueSportsOfferedTitle)).setText("Sports offered: " + selectedSports.getSize());
    }

    public void createVenue(View view){

        System.out.println("firebase stuff");

        ArrayList<String> sportsStringList = new ArrayList<String>();
        for (int i = 0; i < selectedSports.getSize(); i++){
            sportsStringList.add(selectedSports.get(i));
        }

        CreateVenue.setSports(sportsStringList);

        Intent intent = new Intent(this, AddVenue.class);

        CreateVenue.writeToDatabase(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object s) {
                System.out.println("Success writing to db");
                startActivity(intent);
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