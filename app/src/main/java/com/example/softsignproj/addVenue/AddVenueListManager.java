package com.example.softsignproj.addVenue;

import java.util.ArrayList;

public class AddVenueListManager<E> {

    private ArrayList<E> list;

    public AddVenueListManager(){
        this.list = new ArrayList<E>();
    }

    public void add(E item){
        list.add(item);
    }

    public void add(int position, E item){
        list.add(position, item);
    }

    public E get(int position){
        return list.get(position);
    }

    public void remove(int position){
        list.remove(position);
    }

    public void remove(E item){
        list.remove(item);
    }

    public int getSize(){
        return list.size();
    }

}
