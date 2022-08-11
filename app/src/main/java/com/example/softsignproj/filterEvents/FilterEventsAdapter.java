package com.example.softsignproj.filterEvents;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;
import com.example.softsignproj.model.Event;

import java.util.ArrayList;

public class FilterEventsAdapter extends RecyclerView.Adapter<FilterEventsAdapter.MyViewHolder> {

    ArrayList<Event> events;
    Context context;

    public FilterEventsAdapter(Context ct, ArrayList<Event> evnts) {
        context = ct;
        events = evnts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sport.setText(events.get(position).getSport());
        holder.venue.setText(events.get(position).getVenue());
        holder.date.setText(events.get(position).getTime());
        holder.headCount.setText(events.get(position).getHeadCount());
        
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView venue, headCount, date, sport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            venue = itemView.findViewById((R.id.venue));
            headCount = itemView.findViewById((R.id.headCount));
            date = itemView.findViewById((R.id.date));
            sport = itemView.findViewById((R.id.sport));

        }
    }

    public void updateEventsList(ArrayList<Event> evnts) {
        events.clear();

        events.addAll(evnts);

    }
}
