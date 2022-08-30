package com.example.babysitter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.babysitter.Classes.Deals;

import java.util.ArrayList;
import java.util.List;

public class JobRequest extends Fragment {

    View view;
    public static ListView list;
    // array of all deals in database
    public static List<Deals> allJobs;
    public static FragmentActivity fg;

    public static void cancelRequest(Deals deal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(list.getContext());

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                login.dbClass.removeDeal(deal.getDealId(),fg);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.job_requests, container, false);
        // we get all the deals from the data base
        allJobs = new ArrayList<>();
        login.dbClass.getAllDeals("job",view.findViewById(R.id.progress));
        list = view.findViewById(R.id.listOfJobs);
        fg = getActivity();
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
