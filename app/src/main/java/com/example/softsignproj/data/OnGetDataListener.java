package com.example.softsignproj.data;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
