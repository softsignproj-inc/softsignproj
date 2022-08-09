package com.example.softsignproj.data.model;

import java.util.ArrayList;

public class Events {
    private int currcount;
    private String endTime;
    private String startTime;
    private int maxCount;
    private ArrayList<String> participants;
    private String sport;
    private String venue;

    public Events(int currcount) {
        this.currcount = currcount;
    }

    public Events(int currcount, java.lang.String endTime, java.lang.String startTime, int maxCount, ArrayList<java.lang.String> participants, java.lang.String sport, java.lang.String venue) {
        this.currcount = currcount;
        this.endTime = endTime;
        this.startTime = startTime;
        this.maxCount = maxCount;
        this.participants = participants;
        this.sport = sport;
        this.venue = venue;
    }

    @Override
    public java.lang.String toString() {
        return "Event{" +
                "currcount=" + currcount +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", maxCount=" + maxCount +
                ", participants=" + participants +
                ", sport='" + sport + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }

    public int getCurrcount() {
        return currcount;
    }

    public void setCurrcount(int currcount) {
        this.currcount = currcount;
    }

    public java.lang.String getEndTime() {
        return endTime;
    }

    public void setEndTime(java.lang.String endTime) {
        this.endTime = endTime;
    }

    public java.lang.String getStartTime() {
        return startTime;
    }

    public void setStartTime(java.lang.String startTime) {
        this.startTime = startTime;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public ArrayList<java.lang.String> getParticipants() {
        return participants;
    }

    public void setString(ArrayList<java.lang.String> participants) {
        this.participants = participants;
    }

    public java.lang.String getSport() {
        return sport;
    }

    public void setSport(java.lang.String sport) {
        this.sport = sport;
    }

    public java.lang.String getVenue() {
        return venue;
    }

    public void setVenue(java.lang.String venue) {
        this.venue = venue;
    }
}

