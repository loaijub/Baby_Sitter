package com.example.babysitter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int step = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_employee);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();


    }

    public void nextStepEmployeeSignUp(View view) {

        switch(step){
            case 1:
                step = 2;
                ((TextView )findViewById(R.id.step2)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView )findViewById(R.id.step1)).setTypeface(Typeface.DEFAULT);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep2()).commit();
                break;
            case 2:
                step = 3;
                ((TextView )findViewById(R.id.next)).setText("finish");
                ((TextView )findViewById(R.id.step3)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView )findViewById(R.id.step2)).setTypeface(Typeface.DEFAULT);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep3()).commit();
                break;
            case 3:
                step = -1;

        }

    }


    //aaa

    //qqqqq
    //new commit
    //test11
}