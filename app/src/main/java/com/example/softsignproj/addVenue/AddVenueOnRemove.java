package com.example.softsignproj.addVenue;

public class AddVenueOnRemove<E> {

    private AddVenueListManager<E> list;
    private AddVenueEnterSports mainPage;
    private AddVenueRecyclerAdapter adapter;

    public AddVenueOnRemove(AddVenueEnterSports mainPage, AddVenueListManager list, AddVenueRecyclerAdapter adapter){
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
