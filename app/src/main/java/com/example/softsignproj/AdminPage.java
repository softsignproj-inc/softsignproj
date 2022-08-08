package com.example.softsignproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.softsignproj.addVenue.AddVenue;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void onAddVenue(View view){
        Intent intent = new Intent(this, AddVenue.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOutButton) {
            Toast toast = Toast.makeText(getApplicationContext(), "You have been signed out", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}