package com.example.softsignproj.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Event {

    static final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("E, MMM, dd, yyyy, hh:mm a");
    private String id, sport, venue, scheduledBy;
    int curCount, maxCount;
    private LocalDateTime start, end;
    private HashMap<String, String> signedUp;

    public Event(String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue, HashMap<String, String> signedUp) {
        this.id = id;
        this.curCount = cur;
        this.maxCount = max;
        this.start = start;
        this.end = end;
        this.sport = sport;
        this.venue = venue;
        this.scheduledBy = scheduledBy;
        this.signedUp = new HashMap<String, String>();
        this.signedUp.putAll(signedUp);
    }

    public Event(String id, int cur, int max, LocalDateTime start, LocalDateTime end, String sport, String venue) {
        this.id = id;
        this.curCount = cur;
        this.maxCount = max;
        this.start = start;
        this.end = end;
        this.sport = sport;
        this.venue = venue;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTime() {
        return String.format("%s to %s", customFormatter.format(start), customFormatter.format(end));
    }

    public int getCurCount() {
        return curCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public HashMap<String, String> getParticpants() {
        return signedUp;
    }

    public void setCurCount(int cur) {
        this.curCount = cur;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.start = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.end = endTime;
    }

    public void setParticipants(HashMap<String, String> participants) {
        System.out.println("In set participants");
        System.out.println(participants);
        this.signedUp.clear();
        this.signedUp.putAll(participants);
    }

    public String getId() {
        return this.id;
    }

    public void addParticipant(String key, String val) {
        this.signedUp.put(key, val);
        this.curCount++;
    }

    public boolean isFull() {
        return this.curCount == this.maxCount;
    }

    public boolean isSignedUp(String username) {return this.signedUp.containsValue(username);
    }

    public boolean areContentsSame(Event e) {
        return (id == e.id) &&
                (sport.equals(e.getSport())) &&
                (venue.equals(e.getVenue())) &&
                (curCount == e.getCurCount()) &&
                (maxCount == e.getMaxCount()) &&
                (start.equals(e.getStart())) &&
                (end.equals((e.getEnd()))) &&
                (signedUp.equals(e.getParticpants()));
    }
}
