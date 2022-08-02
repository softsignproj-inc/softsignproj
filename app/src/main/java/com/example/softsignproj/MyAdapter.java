package com.example.softsignproj;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.data.Event;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Event> events;
    Context context;

    public MyAdapter(Context ct, ArrayList<Event> evnts) {
        context = ct;
        events = evnts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        holder.venue.setText("Venue: " + events.get(position).venue);
        holder.curNumPlayers.setText("Current Number of Players: " + String.valueOf(events.get(position).curCount));
        holder.maxNumPlayers.setText("Max Number of Players: " + String.valueOf(events.get(position).maxCount));
        holder.startTime.setText("Start Time: " + events.get(position).start.format(format));
        holder.endTime.setText("End Time: " + events.get(position).end.format(format));
        holder.sport.setText("Sport: " + events.get(position).sport);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView venue, curNumPlayers, maxNumPlayers, startTime, endTime, sport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            venue = itemView.findViewById((R.id.venue));
            curNumPlayers = itemView.findViewById((R.id.cur_num_player));
            maxNumPlayers = itemView.findViewById((R.id.max_num_player));
            startTime = itemView.findViewById((R.id.start_time));
            endTime = itemView.findViewById((R.id.end_time));
            sport = itemView.findViewById((R.id.sport));

        }
    }
}
