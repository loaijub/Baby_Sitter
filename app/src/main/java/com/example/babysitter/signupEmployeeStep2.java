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
        return view;
    }

    public static String[] getInputFromEmployee2()
    { // gets the other information from step2 about employee and adds it to the array of information

        signupEmployeeStep1.address = view.findViewById(R.id.employeeAddress);
        signupEmployeeStep1.experience = view.findViewById(R.id.employeeExperience);
        signupEmployeeStep1.specialDemands = view.findViewById(R.id.employeeSpecialDemands);



        String[] res = signupEmployeeStep1.getInputFromEmployee();
        res[6] = signupEmployeeStep1.address.getText().toString();
        res[7] = signupEmployeeStep1.experience.isEnabled() ? "true":"false";
        res[8] = signupEmployeeStep1.specialDemands.getText().toString();


        return res;
    }

}
