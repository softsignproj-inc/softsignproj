package com.example.softsignproj.data.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Venue {
    private String venue_name;
    private ArrayList<String> sports;
    private HashMap<String, String> events;

    public Venue() {
    }

    public Venue(String venue_name, ArrayList<String> sports, HashMap<String, String> events) {
        this.venue_name = venue_name;
        this.sports = sports;
        this.events = events;
    }

    public Venue(HashMap hashMap) {
        this.venue_name = (String) hashMap.get("venue_name");
        this.sports = (ArrayList<String>) hashMap.get("sports");
        this.events = (HashMap<String, String>) hashMap.get("events");
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public ArrayList<String> getSports() {
        return sports;
    }

    public void setSports(ArrayList<String> sports) {
        this.sports = sports;
    }

    public HashMap<String, String> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, String> events) {
        this.events = events;
    }
}
