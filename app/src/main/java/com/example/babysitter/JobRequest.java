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

import java.util.ArrayList;
import java.util.List;

public class JobRequest extends Fragment {

    View view;
    public static ListView list;

    // array of all deals in database
    public static List<Deals> allJobs;

    public JobRequest() {
        allJobs = new ArrayList<>();
        // we get all the deals from the data base
        login.dbClass.getAllDeals();

        // we filter the deals to show only the ones that don't have an answer yet
        filterList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.job_requests, container, false);

        list = view.findViewById(R.id.listOfJobs);
        allJobs = new ArrayList<>();

        return view;
    }

    /**
     * Function filters the arrayList to contain only the deals with no answer from employee
     */
    private void filterList() {

        List<Deals> res = new ArrayList<>(); // the result (filtered array)
        for (Deals job : allJobs) {
            // we check if the employee answered the job request, only if he didn't we add to the result array.
            if (job.getEmployeeAccepted().equals("2"))
                res.add(job);
        }
        allJobs = res;

    }
}
