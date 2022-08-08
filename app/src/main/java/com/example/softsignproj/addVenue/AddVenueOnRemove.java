package com.example.softsignproj.addVenue;

import java.util.ArrayList;

public class AddVenueOnRemove<E> {

    private ArrayList<E> list;
    private AddVenueEnterSports mainPage;
    private AddVenueRecyclerAdapter adapter;

    public AddVenueOnRemove(AddVenueEnterSports mainPage, ArrayList list, AddVenueRecyclerAdapter adapter){
        this.mainPage = mainPage;
        this.list = list;
        this.adapter = adapter;
    }

    public void remove(int index){
        list.remove(index);
        mainPage.update();
        adapter.notifyDataSetChanged();
    }
}
