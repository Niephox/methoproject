package com.example.niephox.methophotos.ViewControllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.niephox.methophotos.R;

public class NavigationItemListener implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;


    public NavigationItemListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.album:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.metadata:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.settings:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.manageAcc:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.userLogout:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.about:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.feedback:
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            case R.id.version:
                item.setChecked(true);
                drawerLayout.closeDrawers();
            default:
                return false;
        }
    }

}
