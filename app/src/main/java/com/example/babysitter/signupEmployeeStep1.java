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

    static EditText id
    ,firstName,
    lastName,
    birthdate,
    email,
    phoneNumber,
    specialDemands,
    address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_employee_step1,container,false);

        id = view.findViewById(R.id.employeeId);
        firstName = view.findViewById(R.id.employeeFirstName);
        lastName = view.findViewById(R.id.employeeLastName);
        birthdate = view.findViewById(R.id.employeeBirthdate);
        email = view.findViewById(R.id.employeeEmail);
        phoneNumber = view.findViewById(R.id.employeePhoneNumber);

        loadPreviousStepState();

        return view;
    }

    public static void getInputFromEmployee()
    { // function puts the information we got from employee in a array and returns it.

        signUpEmployee.fields = new String[9];
        for(int i=0;i<signUpEmployee.fields.length;i++)
            signUpEmployee.fields[i] = "";

        signUpEmployee.fields[0] = id.getText().toString();
        signUpEmployee.fields[1] = firstName.getText().toString();
        signUpEmployee.fields[2] = lastName.getText().toString();
        signUpEmployee.fields[3] = birthdate.getText().toString();
        signUpEmployee.fields[4] = email.getText().toString();
        signUpEmployee.fields[5] = phoneNumber.getText().toString();

    }

    private void loadPreviousStepState() {
        id.setText(signUpParent.fields[0]);
        firstName.setText(signUpParent.fields[1]);
        lastName.setText(signUpParent.fields[2]);
        birthdate.setText(signUpParent.fields[3]);
        email.setText(signUpParent.fields[4]);
        phoneNumber.setText(signUpParent.fields[5]);

    }

}
