package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysitter.Classes.Deals;
import com.example.babysitter.Classes.ListAdapterForJobEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHomePage extends Fragment {
    View view;
    public static ListView list;

    // array of all jobs in database for current user
    public static List<Deals> allJobs = JobRequest.allJobs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.employee_homepage, container, false);

        list = view.findViewById(R.id.jobRequestsList);

        login.dbClass.getAllDeals("job1",view.findViewById(R.id.progress));

        return view;
    }

}