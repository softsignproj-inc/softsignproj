package com.example.softsignproj.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Event event, Event t1) {
        return(event.getStart().compareTo(t1.getStart()));
    }
}
