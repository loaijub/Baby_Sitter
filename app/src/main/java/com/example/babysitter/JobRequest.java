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
    public static List<Deals> allJobs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.job_requests, container, false);
        // we get all the deals from the data base
        allJobs = new ArrayList<>();
        login.dbClass.getAllDeals("job",view.findViewById(R.id.progress));
        list = view.findViewById(R.id.listOfJobs);
        return view;
    }

    /**
     * Function filters the arrayList to contain only the deals with no answer from employee
     */
    public static void filterList() {

        List<Deals> res = new ArrayList<>(); // the result (filtered array)
        for (Deals job : allJobs) {
            // we check if the employee answered the job request, only if he didn't we add to the result array.
            if (job.isHasDone().equals("0"))
                res.add(job);
        }
        allJobs = res;

    }
}
