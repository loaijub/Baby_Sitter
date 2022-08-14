package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class signupEmployeeStep2 extends Fragment {

    static View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_employee_step2, container, false);



        loadPreviousStepState();

        return view;
    }

    private void loadPreviousStepState() {

        ((EditText)view.findViewById(R.id.employeeAddressCity)).setText(signUpEmployee.fields[6]);
        ((EditText)view.findViewById(R.id.employeeAddressStreet)).setText(signUpEmployee.fields[7]);
        ((EditText)view.findViewById(R.id.employeeAddressHouse)).setText(signUpEmployee.fields[8]);
        ((Switch)view.findViewById(R.id.employeeExperience)).setChecked(signUpEmployee.fields[9].equals("checked")?true:false);
        ((EditText)view.findViewById(R.id.employeeSpecialDemands)).setText(signUpEmployee.fields[10]);

    }

}
