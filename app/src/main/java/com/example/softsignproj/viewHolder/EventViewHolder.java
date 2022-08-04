package com.example.softsignproj.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.adapter.EventAdapter;
import com.example.softsignproj.adapter.VenueAdapter;

public class EventViewHolder extends RecyclerView.ViewHolder{
    private final EventAdapter eventAdapter;
    private final TextView eventName;

    public EventViewHolder(EventAdapter eventAdapter, @NonNull View view) {
        super(view);
        this.eventAdapter = eventAdapter;
        eventName = view.findViewById(R.id.venueItem);
    }

    public TextView getEventName() {
        return eventName;
    }

}
