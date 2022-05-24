package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.babysitter.Classes.ImageProcessClass;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class signUpEmployee extends Fragment {
    public static View view;
    int current_step = 1;
    ProgressDialog progressDialog ;
    public static String[] fields = new String[9];
    public static Bitmap bitmapForCV,bitmapForPD;
    String cvImageData = "cv_image_data" ;
    String pdImageData = "pd_image_data" ;
    public static boolean isCVLoaded = false;
    public static boolean isPoliceDocLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up_employee,container,false);
        for (int i=0; i<fields.length; i++)
            fields[i] = "";


        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStepEmployeeSignUp(v);
            }
        });

        for (int i=1; i<=3;i++) {
            TextView steps = view.findViewById(getResources().getIdentifier("step" + i, "id", getActivity().getPackageName()));
            steps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStep(v);
                }
            });
        }


        return view;
    }

    public void nextStepEmployeeSignUp(View v) {
        if (v.getTag() != null && v.getTag().equals("Finish")) {
            for (int i = 0; i < signUpEmployee.fields.length; i++)
                if (signUpEmployee.fields[i].equals("")) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
            if(isCVLoaded)
                login.dbClass.ImageUploadToServerFunction(cvImageData,pdImageData);
            else
                Toast.makeText(getActivity(), "please choose files", Toast.LENGTH_SHORT).show();

            return;
        }


        int stepToGo;
        if(current_step == 3)
            stepToGo = 1;
        else
            stepToGo = current_step + 1;

        showStep(this.view.findViewById(getResources().getIdentifier("step" + stepToGo, "id", getActivity().getPackageName())));

        /*Intent i = new Intent(getContext(), mainGoogleMap.class);
        startActivity(i);*/

    }


    //the function change step according to view clicked (step 1 or 2 or 3)
    public void showStep(View view){

        String s =  getResources().getResourceName(view.getId());
        int step_number = Integer.parseInt(s.substring(s.length()-1));

        //making all the steps default font
        TextView step;
        for (int i=1; i<=3;i++) {
            step = this.view.findViewById(getResources().getIdentifier("step" + i, "id", getActivity().getPackageName()));
            step.setTypeface(Typeface.DEFAULT);
        }

        //making the chosen step bold
        TextView stepToGo = this.view.findViewById(view.getId());
        stepToGo.setTypeface(Typeface.DEFAULT_BOLD);

        //saving the state of edit texts
        saveStep(current_step);

        switch(step_number){
            case 1:
                current_step = 1;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                this.view.findViewById(R.id.next).setTag(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
                break;
            case 2:
                current_step = 2;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                this.view.findViewById(R.id.next).setTag(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep2()).commit();
                break;
            case 3:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep3()).commit();
                ((Button)this.view.findViewById(R.id.next)).setText("Finish");
                this.view.findViewById(R.id.next).setTag("Finish");
                current_step = 3;

        }
    }

    private void saveStep(int stepNumberToSave){
        if(stepNumberToSave == 1)
        {
            fields[0] = ((EditText)view.findViewById(R.id.employeeId)).getText().toString();
            fields[1] = ((EditText)view.findViewById(R.id.employeeFirstName)).getText().toString();
            fields[2] = ((EditText)view.findViewById(R.id.employeeLastName)).getText().toString();
            fields[3] = ((EditText)view.findViewById(R.id.employeeBirthdate)).getText().toString();
            fields[4] = ((EditText)view.findViewById(R.id.employeeEmail)).getText().toString();
            fields[5] = ((EditText)view.findViewById(R.id.employeePhoneNumber)).getText().toString();
        }
        if (stepNumberToSave == 2)
        {
            fields[6] = ((EditText)view.findViewById(R.id.employeeAddress)).getText().toString();
            fields[7] = ((Switch)view.findViewById(R.id.employeeExperience)).isChecked()?"yes":"no";
            fields[8] = ((EditText)view.findViewById(R.id.employeeSpecialDemands)).getText().toString();

        }


    }





}
