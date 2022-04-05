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

public class MainActivity extends AppCompatActivity {

    int current_step = 1; // for the steps in parent sign in

    // for menu
    BottomNavigationView navigationView;
    Fragment selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    case R.id.logOutIcon:
                        selected = new LogOut();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, selected).commit();
                return true;
            }
        });

    }

    public void nextStepEmployeeSignUp(View view) {
        int stepToGo;
        if(current_step == 3)
            stepToGo = 1;
        else
            stepToGo = current_step + 1;

        showStep(findViewById(getResources().getIdentifier("step" + stepToGo, "id", getPackageName())));

        Intent i = new Intent(MainActivity.this, mainGoogleMap.class);
        startActivity(i);

    }

    public void showStep(View view){

        String s =  getResources().getResourceName(view.getId());
        int step_number = Integer.parseInt(s.substring(s.length()-1));

        //making all the steps default font
        TextView step;
        for (int i=1; i<=3;i++) {
            step = findViewById(getResources().getIdentifier("step" + i, "id", getPackageName()));
            step.setTypeface(Typeface.DEFAULT);
        }

        //making the chosen step bold
        TextView stepToGo = findViewById(view.getId());
        stepToGo.setTypeface(Typeface.DEFAULT_BOLD);

        switch(step_number){
            case 1:
                current_step = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
                break;
            case 2:
                current_step = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep2()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep3()).commit();
                current_step = 3;

        }
    }

}