package com.example.softsignproj.myEvents;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;

public class EventViewHolder extends RecyclerView.ViewHolder{
    private final TextView eventName;
    private final TextView startTime;
    private final TextView endTime;
    private final TextView participants;
    private final TextView date;

    public EventViewHolder(EventAdapter eventAdapter, @NonNull View view) {
        super(view);
        eventName = view.findViewById(R.id.eventItem);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        participants = view.findViewById(R.id.numParticipants);
        date = view.findViewById(R.id.dateText);

    }

    public TextView getEventName() {
        return eventName;
    }

    public TextView getStartTime() { return startTime; }

    public TextView getEndTime() { return endTime; }

    public TextView getParticipants() { return participants; }

    public TextView getDate() { return date; }

}
