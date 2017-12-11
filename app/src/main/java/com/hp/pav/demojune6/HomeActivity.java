package com.hp.pav.demojune6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by Pav on 6/7/2017.
 */

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        ImageView headerimage;


        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigationview);

//        headerimage = (ImageView)findViewById(R.id.headerimage);
//        headerimage.setImageResource(R.drawable.drawerheader);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Drawer App");

        drawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view, new IntroductionFragment());
        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.intro:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.view, new IntroductionFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Introduction");
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.sliding_tab:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.view, new SlidingTabFragment());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("Sliding tab");
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.home:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.view, new IntroductionFragment());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle("Drawer app");
                        drawerLayout.closeDrawers();

                        break;

                        //                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.view, new HomeActivity());
//                        fragmentTransaction.commit();



                    case R.id.drawer_map:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.view, new DrawerMapFragmet());
                        fragmentTransaction.commit();

                        getSupportActionBar().setTitle(" Map ");
                        drawerLayout.closeDrawers();

                    break;

                }
                return false;
            }
        });


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) { // for hamburger icon -> menu icon
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
