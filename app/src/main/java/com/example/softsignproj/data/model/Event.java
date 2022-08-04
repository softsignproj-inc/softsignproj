package com.example.softsignproj.data.model;

import com.example.softsignproj.data.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String event_name;
    private String venue;
    private int currCount;
    private int maxCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sport;
    private ArrayList<Customer> participants;

    public Event(String event_name, String venue, int currCount, int maxCount, String sport, LocalDateTime startTime, LocalDateTime endTime, ArrayList<Customer> participants) {
        this.event_name = event_name;
        this.venue = venue;
        this.currCount = currCount;
        this.maxCount = maxCount;
        this.sport = sport;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = new ArrayList<>(participants);
    }

    public String getEvent_name() {
        return event_name;
    }
}
