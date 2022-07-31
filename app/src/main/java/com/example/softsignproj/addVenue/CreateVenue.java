package com.example.softsignproj.addVenue;

import androidx.annotation.NonNull;

import com.example.softsignproj.Database;
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
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();

        map.put("sports", sports);
        map.put("events", new ArrayList<>());

        db.write(venueName, map, onSuccess, onFailure);
    }
}
