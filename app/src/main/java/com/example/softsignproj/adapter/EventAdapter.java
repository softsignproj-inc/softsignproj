package com.example.softsignproj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.data.model.Event;
import com.example.softsignproj.data.model.Venue;
import com.example.softsignproj.viewHolder.EventViewHolder;
import com.example.softsignproj.viewHolder.VenueViewHolder;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder>{
    private final ArrayList<Event> events;
    private final EventAdapter.EventClickListener eventListener;

    public EventAdapter(ArrayList<Event> events, EventAdapter.EventClickListener eventListener) {
        this.events = events;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.getEventName().setText(event.getEvent_name());
//        String sports = "";
//        for (String a: sportsList){
//            sports = sports + a + '\n';
//        }
//        holder.getSportsList().setText(sports);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface EventClickListener {
        void onEventClick(View view);
    }

    public EventClickListener getEventListener() {
        return eventListener;
    }
}
