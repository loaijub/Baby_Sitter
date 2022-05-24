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

        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.currentUser = null;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new login()).commit();
            }
        });


        return view;
    }


    private void viewAllReports() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new ViewAllReports()).addToBackStack(null).commit();
    }

    private void viewAllUsers() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new ViewAllUsers()).addToBackStack(null).commit();
    }

    private void viewAllWorkApplications() {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new ViewAllWorkApplications()).addToBackStack(null).commit();
    }


    private void createNewEmployee() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new adminAddEmployee()).addToBackStack(null).commit();

    }
}
