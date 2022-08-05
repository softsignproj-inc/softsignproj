package com.example.softsignproj.addVenue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.Sport;

import java.util.ArrayList;

public class AddVenueRecyclerAdapter extends RecyclerView.Adapter<AddVenueRecyclerAdapter.AddVenueViewHolder>{

    private ArrayList<Sport> list;
    private AddVenueOnRemove onRemove;

    public AddVenueRecyclerAdapter(ArrayList<Sport> a){
        this.list = a;
    }

    public void setOnRemove(AddVenueOnRemove onRemove){
        this.onRemove = onRemove;
    }

    public class AddVenueViewHolder extends RecyclerView.ViewHolder{

        private TextView txt;
        private ImageButton remove;
        private AddVenueRecyclerAdapter parent;

        public AddVenueViewHolder(final View view){
            super(view);
            txt = view.findViewById(R.id.sportName);
            remove = view.findViewById(R.id.addVenueSportsRemoveButton);
        }

        public TextView getTxt(){
            return txt;
        }

    }

    @NonNull
    @Override
    public AddVenueRecyclerAdapter.AddVenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_venue_sports, parent, false);
        return new AddVenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddVenueRecyclerAdapter.AddVenueViewHolder holder, int position) {
        Sport sport = list.get(position);
        holder.txt.setText(sport.getName());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemove.remove(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
