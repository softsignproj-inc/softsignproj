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

    public VenueViewHolder(VenueAdapter venueAdapter, @NonNull View view) {
        super(view);
        this.venueAdapter = venueAdapter;
        venueName = view.findViewById(R.id.venueItem);
        view.setOnClickListener(clickListener);
    }

    public TextView getVenueName() {
        return venueName;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (venueAdapter.getVenueListener() != null) {
                venueAdapter.getVenueListener().onVenueClick(view);
            }
        }
    };
}
