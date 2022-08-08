package com.example.softsignproj.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.adapter.VenueAdapter;

public class VenueViewHolder extends RecyclerView.ViewHolder{
    private final VenueAdapter venueAdapter;
    private final TextView venueName;
    private final TextView sportsList;

    public VenueViewHolder(VenueAdapter venueAdapter, @NonNull View view) {
        super(view);
        this.venueAdapter = venueAdapter;
        venueName = view.findViewById(R.id.venueItem);
        sportsList = view.findViewById(R.id.sportsList);
    }

    public TextView getVenueName() {
        return venueName;
    }

    public TextView getSportsList() {
        return sportsList;
    }
}
