package com.example.babysitter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int current_step = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_employee);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();


    }

    public void nextStepEmployeeSignUp(View view) {

        showStep(view.getId());

    }

    public void showStep(int step_id){
        TextView step;

        String step_number =getResources().getResourceName(step_id);
        Toast.makeText(this, step_number.substring(step_number.length()-1), Toast.LENGTH_SHORT).show();
        //making all the steps default font
        for (int i=1; i<=3;i++) {
            step = findViewById(getResources().getIdentifier("step" + i, "id", getPackageName()));
            step.setTypeface(Typeface.DEFAULT);
        }

        //making the chosen step bold
        TextView stepToGo = findViewById(step_id);
        stepToGo.setTypeface(Typeface.DEFAULT_BOLD);





/*
        switch(step_number){
            case 1:
                current_step = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
                break;
            case 2:
                current_step = 2;
                ((TextView )findViewById(R.id.next)).setText("finish");
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep2()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep3()).commit();
                current_step = 3;

        }
        */

    }





    //aaa

    //qqqqq
    //new commit
    //test11
}