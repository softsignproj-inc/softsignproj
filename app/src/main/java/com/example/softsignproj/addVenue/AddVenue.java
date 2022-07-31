package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softsignproj.FireBase;
import com.example.softsignproj.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class AddVenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);// commented this out to get rid of error
        FireBase f = new FireBase();
    }

    public void onEnter(View view){
        if (view.getId() == R.id.addVenueNextButton){
            Intent intent = new Intent(this, AddVenueEnterSports.class);

            this.startActivity(intent);
        }
    }
}