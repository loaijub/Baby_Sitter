package com.example.babysitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new homePage()).commit();

    }

    @Override
    public void onBackPressed()
    {
        getSupportFragmentManager().popBackStack();

    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                signUpEmployee.bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                signupEmployeeStep3.CVThumb.setImageBitmap(signUpEmployee.bitmap);
                signupEmployeeStep3.SelectCV.setAlpha((float) 0.2);
                signupEmployeeStep3.SelectCV.setOnClickListener(null);
                signUpEmployee.isCVLoaded = true;

            } catch (IOException e) {
                Toast.makeText(this, "catch", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                signUpEmployee.isCVLoaded = false;
            }
        }
    }

}