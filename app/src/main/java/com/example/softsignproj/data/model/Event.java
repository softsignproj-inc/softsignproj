package com.example.softsignproj.data.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Event {

    static final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("E, MMM, dd, yyyy, hh:mm a");
    String id, sport, venue, scheduledBy;
    int curCount, maxCount;
    LocalDateTime start, end;
    HashMap<String, String> signedUp;

    public Event(String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue, HashMap<String, String> signedUp) {
        this.id = id;
        this.curCount = cur;
        this.maxCount = max;
        this.start = start;
        this.end = end;
        this.sport = sport;
        this.venue = venue;
        this.scheduledBy = scheduledBy;
        this.signedUp = signedUp;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public String getSport() {
        return this.sport;
    }

    public String getVenue() {
        return this.venue;
    }

    public String getHeadCount() {
        return String.format("%d/%d", this.curCount, this.maxCount);
    }

    public String getTime() {
        return String.format("%s to %s", customFormatter.format(start), customFormatter.format(end));
    }

    public boolean isFull() {
        return this.curCount == this.maxCount;
    }

    public boolean isSignedUp(String username) {
        return this.signedUp.containsValue(username);
    }
}
