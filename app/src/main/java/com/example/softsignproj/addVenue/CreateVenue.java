package com.example.softsignproj.addVenue;

import com.example.softsignproj.Database;
import com.example.softsignproj.model.Venue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateVenue {

    private static String venueName;
    private static ArrayList sports;
    private static Database db;

    public static void setSports(ArrayList s){
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
        newVenue.setVenue_name(venueName);
        newVenue.setSports(sports);
        newVenue.setEvents(new HashMap<String, String>());
        System.out.println("Venue name is " + venueName);
        db.write("venue/" + venueName, newVenue, onSuccess, onFailure);

    }

    public static String getVenueName(){
        return venueName;
    }

    public static ArrayList getSports(){
        return sports;
    }
}