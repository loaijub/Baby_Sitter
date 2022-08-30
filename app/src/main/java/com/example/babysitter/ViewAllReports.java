package com.example.babysitter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.Report;
import com.example.babysitter.Classes.User;
import com.example.babysitter.Classes.dbClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllReports extends Fragment {

    static ListView listView;
    View view;
    public static List<User> allUsers = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.admin_view_all_reports, container,false);
        allUsers = new ArrayList<>();
        listView = view.findViewById(R.id.listview_show_reports);
        login.dbClass.getAllReports();


        return view;
    }

    public static void showListViewForReports(List<Report> allReports,Context context){
        //showing the reports in the list
        for (Report r : allReports)
            allUsers.add(getUserDetails(r.getApplicantId()));
        ListAdapterForReports myAdapter = new ListAdapterForReports(allReports,allUsers,context);
        listView.setAdapter(myAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listView.getItemAtPosition(position);
                showReport((Report)o);
            }
        });




    }

    private static void showReport(Report r){
        Dialog dialog = new Dialog(listView.getContext());
        dialog.setContentView(R.layout.show_report);
        dialog.show();

        ((TextView)dialog.findViewById(R.id.report_details)).setText(r.toString());


    }

    private static User getUserDetails(String userId){
        for (User u : dbClass.users)
            if(userId.equals(u.getId()))
                return u;

        return null;
    }


}

class ListAdapterForReports extends BaseAdapter {
    List<Report> allReports;
    List<User> allUsers;
    Context context;

    public ListAdapterForReports(List<Report> allReports, List<User> allUsers, Context context){
        this.allReports = allReports;
        this.allUsers = allUsers;
        this.context = context;
    }
    @Override
    public int getCount() {
        return allReports.size();
    }

    @Override
    public Object getItem(int position) {
        return allReports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = ly.inflate(R.layout.list_item_all_reports,null);
        if(allUsers.get(position) != null)
            ((TextView)v.findViewById(R.id.testOutPut)).setText("Report from: " + allUsers.get(position).getFirstName() + " " + allUsers.get(position).getLastName());
        else
            Toast.makeText(context, "Error getting info", Toast.LENGTH_SHORT).show();
        return v;
    }
}