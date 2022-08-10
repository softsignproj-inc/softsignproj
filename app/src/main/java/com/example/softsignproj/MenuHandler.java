package com.example.softsignproj;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MenuHandler {

    public static boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public static MenuItem onOptionsItemSelected(MenuItem item, DrawerLayout navDrawer, NavigationView nav, PageHandler pages) {
        int id = item.getItemId();

        if (id == R.id.openNavButton) {

            if(!navDrawer.isDrawerOpen(GravityCompat.END)) {
                navDrawer.openDrawer(GravityCompat.END);

                nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        pages.openPage(item.getItemId());
                        navDrawer.closeDrawers();
                        return true;
                    }
                });

            } else {
                navDrawer.closeDrawer(GravityCompat.END);
            }
        }
        return item;
    }

}
