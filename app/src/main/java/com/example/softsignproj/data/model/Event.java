package com.example.softsignproj.data.model;

import java.util.ArrayList;

public class Event {
    private String venue;
    private int maxCount;
    private int currCount;
    private String sport;
    private String startTime;
    private String endTime;

    public Event(String venue) {
        this.venue = venue;
    }

    public Event(String venue, int maxCount, int currCount, String sport, String startTime, String endTime) {
        this.venue = venue;
        this.maxCount = maxCount;
        this.currCount = currCount;
        this.sport = sport;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getCurrCount() {
        return currCount;
    }

    public void setCurrCount(int currCount) {
        this.currCount = currCount;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
