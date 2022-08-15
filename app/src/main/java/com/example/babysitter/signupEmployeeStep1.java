package com.example.babysitter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class signupEmployeeStep1 extends Fragment {

    private static String url = "http://172.16.5.244/babysitter/";
    static View view;
    static Switch experience;
    EditText etBirthDate;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_employee_step1,container,false);

        //adding date picker for birtrhdate edit text`
        etBirthDate =view.findViewById(R.id.employeeBirthdate);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="yyyy/MM/dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                etBirthDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
