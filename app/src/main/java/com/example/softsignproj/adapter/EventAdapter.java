package com.example.softsignproj.adapter;

import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.data.model.Event;
import com.example.softsignproj.viewHolder.EventViewHolder;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.getEventName().setText(new String(event.getSport() + " at " + event.getVenue()));
        holder.getStartTime().setText(String.format("%02d:%02d", event.getStartTime().getHour(), event.getStartTime().getMinute()));
        holder.getEndTime().setText(String.format("%02d:%02d", event.getEndTime().getHour(), event.getEndTime().getMinute()));
        holder.getParticipants().setText(new String(event.getCurrCount() + "/" + event.getMaxCount()));
        holder.getDate().setText(String.format("%02d %s, %04d", event.getStartTime().getDayOfMonth(), event.getStartTime().getMonth().getDisplayName(TextStyle.FULL, Locale.CANADA), event.getStartTime().getYear()));
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