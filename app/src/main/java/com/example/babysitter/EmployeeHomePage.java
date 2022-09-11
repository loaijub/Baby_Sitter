package com.example.babysitter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.babysitter.Classes.Deals;
import com.example.babysitter.Classes.ListAdapterForJobEmployee;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHomePage extends Fragment {
    View view;

    public static BottomNavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.employee_homepage, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMainFragment, new JobRequestList()).commit();


        // menu code
        navigationView = view.findViewById(R.id.nav1);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.homeIcon:
                        view.findViewById(R.id.title).setVisibility(View.VISIBLE);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMainFragment, new JobRequestList()).commit();
                        break;

                    case R.id.profileIcon:

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMainFragment, new Profile()).commit();
                        view.findViewById(R.id.title).setVisibility(View.GONE);
                        break;

                    case R.id.infoIcon:

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMainFragment, new informationPage()).commit();
                        view.findViewById(R.id.title).setVisibility(View.GONE);
                        break;

                    case R.id.historyIcon:
                        History h = new History();
                        h.setHistoryOrhistory1("history1");
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMainFragment, h).commit();
                        view.findViewById(R.id.title).setVisibility(View.GONE);
                        break;

                }

                return true;
            }
        });




        return view;
    }

}