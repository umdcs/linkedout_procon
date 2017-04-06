package com.example.christopher.linkedoutapp;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;


import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class StudentDefaultView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, News.OnFragmentInteractionListener, Profile.OnFragmentInteractionListener, QuizStatus.OnFragmentInteractionListener, JobsInYourArea.OnFragmentInteractionListener {

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_default_view);
        prefs = getSharedPreferences(STUDENT_PREFS, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Fragment fragment = null;
        Class fragmentClass = Profile.class;
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_default_view, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = Profile.class;

        if (id == R.id.news) {
           fragmentClass = News.class;
        } else if (id == R.id.profile) {
            fragmentClass = Profile.class;
        } else if (id == R.id.QuizStatus) {
            fragmentClass = QuizStatus.class;
        } else if (id == R.id.Jobs) {
            fragmentClass = JobsInYourArea.class;
        }
        else if (id == R.id.Logout) {
            logout();
        }

        else{
            fragmentClass = Profile.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    public void onClickAQuiz(View view){
        Intent intent = new Intent(this, ArrayQuiz.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.commit();

        startActivity(intent);
    }

}
