package com.example.softsignproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    RecyclerView eventsView;
    ArrayList<Event> data;
    String userID;
    Context context;

    public EventsAdapter(Context ct, ArrayList<Event> data, String id) {
        this.data = data;
        this.userID = id;
        this.context = ct;
        Collections.sort(data, (t0, t1) -> {
            if (t0.getStart().isBefore(t1.getStart())) return 1;
            else if (t0.getStart().isAfter(t1.getStart())) return -1;
            else return 0;
        }
        );
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {

        // Set text for all textViews
        holder.sport.setText(data.get(position).getSport());
        holder.venue.setText(data.get(position).getVenue());
        holder.date.setText(data.get(position).getTime());
        holder.headCount.setText(data.get(position).getHeadCount());

        // Set button accordingly
        if (data.get(position).isFull()) {
            holder.schedule.setText("FULL");
            holder.schedule.setEnabled(false);
        }

        else if (data.get(position).isSignedUp(this.userID)) {
            holder.schedule.setText("Scheduled");
            holder.schedule.setEnabled(false);
        }

        else {
            holder.schedule.setText("Add to schedule?");
            holder.schedule.setEnabled(true);
        }

    }

    public void updateEventsList(ArrayList<Event> events) {
        data.clear();
        data.addAll(events);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sport, venue, date, headCount;
        Button schedule;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sport = itemView.findViewById(R.id.sport);
            this.venue = itemView.findViewById(R.id.venue);
            this.date = itemView.findViewById(R.id.date);
            this.headCount = itemView.findViewById(R.id.headCount);
            this.schedule = itemView.findViewById(R.id.schedule);
        }
    }
}

