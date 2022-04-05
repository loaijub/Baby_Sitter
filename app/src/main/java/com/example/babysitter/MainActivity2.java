package com.example.babysitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {


    // for menu
    BottomNavigationView navigationView;
    Fragment selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new Home()).commit();


        // menu code
        navigationView = findViewById(R.id.nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.homeIcon:
                        selected = new Home();
                        break;

                    case R.id.profileIcon:
                        selected = new Profile();
                        break;

                    case R.id.infoIcon:
                        selected = new AboutUs();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, selected).commit();
                return true;
            }
        });

    }



}