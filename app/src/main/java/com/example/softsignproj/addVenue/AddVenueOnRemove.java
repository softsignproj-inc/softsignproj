package com.example.softsignproj.addVenue;

import java.util.ArrayList;

public class AddVenueOnRemove<E> {

    private final ArrayList<E> list;
    private final AddVenue mainPage;
    private final AddVenueRecyclerAdapter adapter;

    public AddVenueOnRemove(AddVenue mainPage, ArrayList list, AddVenueRecyclerAdapter adapter){
        this.mainPage = mainPage;
        this.list = list;
        this.adapter = adapter;
    }

    public void remove(int index){
        list.remove(index);
        mainPage.update();
        adapter.notifyItemRemoved(index);
    }
}