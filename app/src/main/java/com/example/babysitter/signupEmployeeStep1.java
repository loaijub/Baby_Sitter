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
        return view;
    }

    public static String[] getInputFromEmployee()
    { // function puts the information we got from employee in a array and returns it.

        id = view.findViewById(R.id.employeeId);
        firstName = view.findViewById(R.id.employeeFirstName);
        lastName = view.findViewById(R.id.employeeLastName);
        birthdate = view.findViewById(R.id.employeeBirthdate);
        email = view.findViewById(R.id.employeeEmail);
        phoneNumber = view.findViewById(R.id.employeePhoneNumber);

        String[] arr = new String[9];

        arr[0] = id.getText().toString();
        arr[1] = firstName.getText().toString();
        arr[2] = lastName.getText().toString();
        arr[3] = birthdate.getText().toString();
        arr[4] = email.getText().toString();
        arr[5] = phoneNumber.getText().toString();

        return arr;
    }

}
