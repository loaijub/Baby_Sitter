package com.example.babysitter;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.Deals;
import com.example.babysitter.Classes.ListAdapterForDeals;
import com.example.babysitter.Classes.Report;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    View view;
    public static ListView list;
    String historyOrhistory1 = "history";
    // array of all deals in database
    public static List<Deals> allDeals = new ArrayList<>();

    public static void setListener() {
        Toast.makeText(list.getContext(), "hi", Toast.LENGTH_SHORT).show();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history, container, false);
        login.dbClass.getAllDeals(historyOrhistory1,view.findViewById(R.id.progress));
        list = view.findViewById(R.id.listOfHistory);
        allDeals = new ArrayList<>();

        return view;
    }

    public static void showReport(Deals deal) {
        Dialog dialog = new Dialog(list.getContext());
        dialog.setContentView(R.layout.show_report_to_send);
        dialog.show();
        DatePicker dp = dialog.findViewById(R.id.accident_date);
        EditText accidentDetails = dialog.findViewById(R.id.accident_text);
        dialog.findViewById(R.id.report_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date dateOfAccident = new Date(dp.getDayOfMonth()+"",dp.getMonth()+1+"",dp.getYear()+"");
                Report r = new Report("",deal.getParentId(),deal.getEmployeeId(),null,dateOfAccident,accidentDetails.getText().toString());
                login.dbClass.addReport(r);
                dialog.dismiss();
            }
        });
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


    public void setHistoryOrhistory1(String historyOrhistory1) {
        this.historyOrhistory1 = historyOrhistory1;
    }
}



