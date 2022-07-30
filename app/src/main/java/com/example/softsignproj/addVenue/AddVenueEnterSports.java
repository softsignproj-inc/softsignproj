package com.example.softsignproj.addVenue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.softsignproj.R;

import java.util.ArrayList;

public class AddVenueEnterSports extends AppCompatActivity {

    private int numTotalSports;
    private ArrayList<String> selectedSports;
    RecyclerView sportsRecyclerView;
    AddVenueRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue_select_sports);
        selectedSports = new ArrayList<String>();
        selectedSports.add("BasketBall");
        selectedSports.add("Soccer");
        selectedSports.add("Swimming");

        sportsRecyclerView = findViewById(R.id.addVenueEnterSportsRecyclerView);
        adapter = new AddVenueRecyclerAdapter(selectedSports);
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

        }
    }

    public void removeSport(View view){
        //remove = view.findViewById(R.id.addVenueSportsRemoveButton);
        System.out.println(view.toString());
        adapter.notifyDataSetChanged();
    }
}