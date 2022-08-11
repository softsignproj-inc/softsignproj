package com.example.softsignproj.eventList;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.softsignproj.R;
import com.example.softsignproj.model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("event");
    SortedList<Event> data;
    String userID;
    Context context;

    public EventListAdapter(Context ct, String id) {
        data = new SortedList<>(Event.class, new SortedList.Callback<Event>() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStart().compareTo(o2.getStart());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Event oldItem, Event newItem) {
                return oldItem.areContentsSame(newItem);
            }

            @Override
            public boolean areItemsTheSame(Event item1, Event item2) {
                return item1.getId().equals(item2.getId());
            }
        });
        this.userID = id;
        this.context = ct;
    }

    @NonNull
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.join_events_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view, new EventClickListener() {
            @Override
            public void onClick(int p) {
                DatabaseReference eventRef = db.child(data.get(p).getId());
                DatabaseReference addedParticipant = eventRef.child("participants").push();
                DatabaseReference joinedEventRef = FirebaseDatabase.getInstance().getReference().child("customer/" + userID + "/joinedEvents").push();
                joinedEventRef.setValue(eventRef.getKey());
                eventRef.child("currCount").setValue(data.get(p).getCurCount() + 1);
                addedParticipant.setValue(userID);
                Event event = data.get(p);
                event.addParticipant(addedParticipant.getKey(), userID);
                updateEvent(event);
            }
        });
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.ViewHolder holder, int position) {

        // Set text for all textViews
        holder.sport.setText(data.get(position).getSport());
        holder.venue.setText(data.get(position).getVenue());
        holder.date.setText(data.get(position).getTime());
        holder.headCount.setText(data.get(position).getHeadCount());

        // Set button accordingly
        if (data.get(position).isSignedUp(this.userID)) {
            holder.schedule.setText(R.string.scheduled);
            holder.schedule.setEnabled(false);
        }

        else if (data.get(position).isFull()) {
            holder.schedule.setText(R.string.full);
            holder.schedule.setEnabled(false);
        }

        else {
            holder.schedule.setText(R.string.add_to_schedule);
            holder.schedule.setEnabled(true);
        }
    }

    public void addEvent(Event event) {
        data.add(event);
    }

    public void removeEvent(Event event) {
        data.remove(event);
    }

    public void updateEvent(Event event) {
        data.updateItemAt(data.indexOf(event), event);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EventClickListener listener;
        TextView sport, venue, date, headCount;
        Button schedule;
        public ViewHolder(@NonNull View itemView, EventClickListener listener) {
            super(itemView);
            this.sport = itemView.findViewById(R.id.sport);
            this.venue = itemView.findViewById(R.id.venue);
            this.date = itemView.findViewById(R.id.date);
            this.headCount = itemView.findViewById(R.id.headCount);
            this.schedule = itemView.findViewById(R.id.schedule);
            this.listener = listener;

            schedule.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.schedule) {
                listener.onClick(this.getLayoutPosition());
            }
        }
    }
    public interface EventClickListener {
        void onClick(int p);
    }
}

