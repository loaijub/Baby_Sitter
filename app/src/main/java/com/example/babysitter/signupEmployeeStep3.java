package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class signupEmployeeStep3 extends Fragment {
    View view;

    public static ImageView PoliceDocThumb, CVThumb;
    public static Button SelectCV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_employee_step3,container,false);
        CVThumb = view.findViewById(R.id.CVThumb);
        SelectCV = view.findViewById(R.id.CVChooseFile);
        SelectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select image file"), 1);

            }
        });

        //when finish clicked
         /*
        Button btnFinish = signUpEmployee.view.findViewById(R.id.next);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ImageUploadToServerFunction();
            }
        });*/
        return view;
    }









}
