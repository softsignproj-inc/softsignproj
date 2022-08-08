package com.example.softsignproj.data;

import java.time.LocalDateTime;

public class Event {
    public String id, sport, venue;
    public int curCount, maxCount;
    public LocalDateTime start, end;

    public Event(String id, Long cur, Long max, LocalDateTime start, LocalDateTime end, String sport, String venue) {
        this.id = id;
        this.curCount = Math.toIntExact(cur);
        this.maxCount = Math.toIntExact(max);
        this.start = start;
        this.end = end;
        this.sport = sport;
        this.venue = venue;
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
}
