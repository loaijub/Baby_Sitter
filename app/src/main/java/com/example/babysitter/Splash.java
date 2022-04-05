package com.example.babysitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            public void run(){

                try{
                    sleep(1000);
                }catch (Exception e){

                }finally {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();


    }
}