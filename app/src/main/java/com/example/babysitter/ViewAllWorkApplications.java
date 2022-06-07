package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.WorkApplication;
import com.example.babysitter.Classes.dbClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewAllWorkApplications extends Fragment {

    static ListView list;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.admin_view_all_workapplications, container, false);

        list = view.findViewById(R.id.listOfAllWorkApplications);


        // calling function that gets all the work applications from the data base
        login.dbClass.getAllWorkApplications("allWorkApplications");

        return view;
    }

    public static void showAllWorkApplicationsInList(WorkApplication[] allWorkApplications, Context context) { // function gets an array of objects of all work applications and organize it in list view

        ListAdapterForAllWorkApplications allWorkAppAdapter = new ListAdapterForAllWorkApplications(allWorkApplications, context);
        list.setAdapter(allWorkAppAdapter);

    }
}

class ListAdapterForAllWorkApplications extends BaseAdapter {
    WorkApplication[] applicationsArr;
    Context context;
    FragmentActivity activity;

    public ListAdapterForAllWorkApplications(WorkApplication[] applicationsArr, Context context) {
        this.applicationsArr = applicationsArr;
        this.activity = (FragmentActivity) context;
        this.context = context;
    }

    @Override
    public int getCount() {
        return applicationsArr.length;
    }

    @Override
    public Object getItem(int position) {
        return applicationsArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = ly.inflate(R.layout.admin_view_single_workapplication, null);

        TextView applicationInfo = v.findViewById(R.id.singleWorkApplicationInfo);
        applicationInfo.setText(applicationsArr[position].toString());
        return v;
    }
}
