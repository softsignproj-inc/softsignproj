package com.example.softsignproj.viewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;

public class VenueViewHolder extends RecyclerView.ViewHolder{

    public TextView venue_name;


    public VenueViewHolder(@NonNull View View) {
        super(View);

        venue_name = (TextView) itemView.findViewById(R.id.venue_name);
    }

}
