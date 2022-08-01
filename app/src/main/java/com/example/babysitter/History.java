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
import com.example.babysitter.Classes.ListAdapterForDeals;
import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    View view;
    public static ListView list;

    // array of all deals in database
    public static List<Deals> allDeals = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history, container, false);
        login.dbClass.getAllDeals("history",view.findViewById(R.id.progress));
        list = view.findViewById(R.id.listOfHistory);
        allDeals = new ArrayList<>();

        return view;
    }

    public static void filterArray()
    {
        List<Deals> res = new ArrayList<>(); // the result (filtered array)
        for (Deals deal : allDeals) {
            // we check if the deal is done, only if it is done, we add it to array.
            if (deal.isHasDone().equals("1"))
                res.add(deal);
        }
        allDeals = res;
    }




}



