package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;

import com.example.softsignproj.Database;
import com.example.softsignproj.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class CreateVenue {

    private static String venueName;
    private static List<String> sports;
    private static Database db;

    public static void setSports(List<String> s){
        sports = s;
    }

    public static void setVenueName(String name){
        venueName = name;
    }

    public static void setDatabase(Database d){
        db = d;
    }

    public static void writeToDatabase(OnSuccessListener<? super Object> onSuccess, OnFailureListener onFailure){

        Venue newVenue = new Venue();
        newVenue.name = venueName;
        newVenue.sports = sports;
        newVenue.events = new ArrayList<>();

        db.write("venue/" + venueName, newVenue, onSuccess, onFailure);

    }
}
