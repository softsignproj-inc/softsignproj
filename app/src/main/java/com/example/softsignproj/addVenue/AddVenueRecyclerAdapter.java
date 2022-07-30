package com.example.softsignproj.addVenue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softsignproj.R;

import java.util.ArrayList;

public class AddVenueRecyclerAdapter extends RecyclerView.Adapter<AddVenueRecyclerAdapter.AddVenueViewHolder>{

    private ArrayList<String> list;

    public AddVenueRecyclerAdapter(ArrayList a){
        this.list = a;
    }

    public class AddVenueViewHolder extends RecyclerView.ViewHolder{

        private TextView txt;
        private Button remove;
        private AddVenueRecyclerAdapter parent;

        public AddVenueViewHolder(final View view){
            super(view);
            txt = view.findViewById(R.id.sportName);
            remove = view.findViewById(R.id.addVenueSportsRemoveButton);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Removing " + txt.getText().toString());
                    list.remove(txt.getText().toString());
                    parent.notifyDataSetChanged();
                }
            });
        }

        public TextView getTxt(){
            return txt;
        }

        public void setParent(AddVenueRecyclerAdapter p){
            parent = p;
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
        String name = list.get(position);
        holder.txt.setText(name);
        holder.setParent(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
