package com.example.softsignproj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.data.model.Venue;
import com.example.softsignproj.viewHolder.VenueViewHolder;

import java.util.ArrayList;

public class VenueAdapter extends RecyclerView.Adapter<VenueViewHolder> {
    private final ArrayList<Venue> venues;
    private final VenueClickListener venueListener;

    public VenueAdapter(ArrayList<Venue> venues, VenueClickListener venueListener) {
        this.venues = venues;
        this.venueListener = venueListener;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.venue_item, parent, false);
        return new VenueViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        Venue venue = venues.get(position);
        holder.getVenueName().setText(venue.getVenue_name());
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public interface VenueClickListener {
        void onVenueClick(View view);
    }

    public VenueClickListener getVenueListener() {
        return venueListener;
    }
}
