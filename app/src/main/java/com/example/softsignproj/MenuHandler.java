package com.example.softsignproj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuHandler {

    public static boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public static MenuItem onOptionsItemSelected(MenuItem item, Context context, boolean isAdmin) {
        int id = item.getItemId();

        if (id == R.id.signOutButton) {
            Toast toast = Toast.makeText(context, "You have been signed out", Toast.LENGTH_SHORT);
            toast.show();

            if (!isAdmin) {
                SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
            }

            Intent intent = new Intent(context, SignIn.class);
            context.startActivity(intent);
        }
        return item;
    }
}
