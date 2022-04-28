package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class signupEmployeeStep1 extends Fragment {

    private static String url = "http://172.16.5.244/babysitter/";
    static View view;
    static Switch experience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_employee_step1,container,false);



        loadPreviousStepState();

        return view;
    }


    private void loadPreviousStepState() {
        ((EditText)view.findViewById(R.id.employeeId)).setText(signUpEmployee.fields[0]);
        ((EditText)view.findViewById(R.id.employeeFirstName)).setText(signUpEmployee.fields[1]);
        ((EditText)view.findViewById(R.id.employeeLastName)).setText(signUpEmployee.fields[2]);
        ((EditText)view.findViewById(R.id.employeeBirthdate)).setText(signUpEmployee.fields[3]);
        ((EditText)view.findViewById(R.id.employeeEmail)).setText(signUpEmployee.fields[4]);
        ((EditText)view.findViewById(R.id.employeePhoneNumber)).setText(signUpEmployee.fields[5]);

    }

}
