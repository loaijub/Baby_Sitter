package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllReports extends Fragment {

    ProgressDialog dialogLoading;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.admin_view_all_reports, container,false);

        getAllReports();


        return view;
    }

    public void showListView(List<Report> allReports){
        //showing the reports in the list
        ListView listView = view.findViewById(R.id.listview_show_reports);
        

        ListAdapterForReports myAdapter = new ListAdapterForReports(allReports,getContext());
        
        
        listView.setAdapter(myAdapter);

    }

    private void getAllReports() {
        List<Report> allReports = new ArrayList<>();

        dialogLoading = ProgressDialog.show(getContext(), "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, login.url + "?action=getAllReports", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray all_reports = new JSONArray(response);


                    for (int i = 0; i < all_reports.length(); i++) {
                        JSONObject report = all_reports.getJSONObject(i);
                        String[] dateOfSubAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfSub = new Date(dateOfSubAsString[0], dateOfSubAsString[1], dateOfSubAsString[2]);

                        String[] dateOfAccidentAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfAccident = new Date(dateOfAccidentAsString[0], dateOfAccidentAsString[1], dateOfAccidentAsString[2]);

                        allReports.add(new Report("","","",dateOfSub,dateOfAccident,""));
                    }

                    showListView(allReports);

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}

class ListAdapterForReports extends BaseAdapter {
    List<Report> allReports;
    Context context;

    public ListAdapterForReports(List<Report> allReports,Context context){
        this.allReports = allReports;
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
        ((TextView)v.findViewById(R.id.testOutPut)).setText(allReports.toString());

        return v;
    }
}