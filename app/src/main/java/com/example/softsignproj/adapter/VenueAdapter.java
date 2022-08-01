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

public class VenueAdapter extends RecyclerView.Adapter {

    private ArrayList<Venue> venues;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item, parent, false);

         return new VenueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VenueViewHolder venueholder = (VenueViewHolder)holder;
        Venue venue = venues.get(position);
        venueholder.venue_name.setText(venue.getVenue_name());
    }

    @Override
    public int getItemCount() {
        if (venues != null){
            return venues.size();
        } else {
            return 0;
        }
    }
}
