package com.example.softsignproj.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.softsignproj.data.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Event {
    private String event_name;
    private String venue;
    private int currCount;
    private int maxCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sport;
    private ArrayList<Customer> participants;

    public Event(String event_name, String venue, int currCount, int maxCount, String sport, LocalDateTime startTime, LocalDateTime endTime) {
        this.event_name = event_name;
        this.venue = venue;
        this.currCount = currCount;
        this.maxCount = maxCount;
        this.sport = sport;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getVenue() { return venue; }

    public int getCurrCount() { return currCount; }

    public int getMaxCount() { return maxCount; }

    public String getSport() { return sport; }

    public LocalDateTime getStartTime() { return startTime; }

    public LocalDateTime getEndTime() { return endTime; }

}
