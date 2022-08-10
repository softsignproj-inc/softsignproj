package com.example.softsignproj.venueList;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.venueList.VenueAdapter;

public class VenueViewHolder extends RecyclerView.ViewHolder{
    private final VenueAdapter venueAdapter;
    private final TextView venueName;
    private final TextView sportsList;
    private final Button schedule;

    public VenueViewHolder(VenueAdapter venueAdapter, @NonNull View view) {
        super(view);
        this.venueAdapter = venueAdapter;
        venueName = view.findViewById(R.id.venueItem);
        sportsList = view.findViewById(R.id.sportsList);
        schedule = view.findViewById(R.id.addActivity);
    }

    public TextView getVenueName() {
        return venueName;
    }

    public TextView getSportsList() {
        return sportsList;
    }

    public Button getSchedule() {
        return schedule;
    }
}
