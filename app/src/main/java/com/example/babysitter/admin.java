package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.Employee;


public class admin extends Fragment {

    LinearLayout addEmployee;
    LinearLayout allWorkApplications;
    LinearLayout allUsers;
    LinearLayout allReports;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_panel, container, false);

        addEmployee = view.findViewById(R.id.createNewEmployee);
        allWorkApplications = view.findViewById(R.id.viewAllWorkApplications);
        allUsers = view.findViewById(R.id.viewAllUsers);
        allReports = view.findViewById(R.id.viewAllReports);


        // setting listeners for the cards
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewEmployee();
            }
        });

        allWorkApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllWorkApplications();
            }
        });

        allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllUsers();
            }
        });

        allReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllReports();
            }
        });


        return view;
    }


    private void viewAllReports() {

        Toast.makeText(getContext(), "viewAllReports function will work", Toast.LENGTH_SHORT).show();
    }

    private void viewAllUsers() {

        Toast.makeText(getContext(), "viewAllUsers function will work", Toast.LENGTH_SHORT).show();
    }

    private void viewAllWorkApplications() {

        Toast.makeText(getContext(), "viewAllWorkApplications function will work", Toast.LENGTH_SHORT).show();
    }


    private void createNewEmployee() {

        String id, fName, lName, phoneNumber, password, email, role, status, rate, specialDemands, experience;
        Date birthDate;
        double workingHoursInMonth;

        // after getting all info from work application
        // replace values from the database

        id = "123";
        fName = "stam";
        lName = "to try";
        phoneNumber = "059812345";
        birthDate = new Date("23", "1", "2020");
        password = "123";
        email = "stam@totest.com";
        specialDemands = "qwewqwqw";
        experience = "true";

        // the automatic values
        role = "1"; // 1 - stands for employee
        status = "0"; // 0 - stands for active
        rate = "0";
        workingHoursInMonth = 0;


        Employee empToAdd = new Employee(id, fName, lName, phoneNumber, birthDate, password, role, email, status, rate, specialDemands, workingHoursInMonth, experience);


        Toast.makeText(getContext(), "createNewEmployee function will work", Toast.LENGTH_SHORT).show();

    }
}