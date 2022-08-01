package com.example.softsignproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softsignproj.addVenue.AddVenue;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void onAddVenue(View view){
        Intent intent = new Intent(this, AddVenue.class);
        startActivity(intent);
    }
}