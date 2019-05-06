package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentMain fm =  FragmentMain.newInstance(0);
        android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, fm);
        fTrans.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_events) {
            FragmentMain fm =  FragmentMain.newInstance(0);
            android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, fm);
            fTrans.commit();

        } else if (id == R.id.nav_lists) {
            FragmentLists fl = new FragmentLists();
            android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, fl);
            fTrans.commit();

        } else if (id == R.id.nav_lost){
            FragmentMain fm = FragmentMain.newInstance(-1);
            android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, fm);
            fTrans.commit();

        } else if (id == R.id.nav_complete){
            FragmentMain fm = FragmentMain.newInstance(-2);
            android.app.FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frgmCont, fm);
            fTrans.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
