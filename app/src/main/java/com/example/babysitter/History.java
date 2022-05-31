package com.example.babysitter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.babysitter.Classes.Deals;
import com.example.babysitter.Classes.Report;
import com.example.babysitter.Classes.WorkApplication;
import com.example.babysitter.Classes.dbClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    View view;
    ListView list;

    // array of all deals in database
    public static List<Deals> allDeals;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history, container, false);

        list = view.findViewById(R.id.listOfHistory);
        allDeals = new ArrayList<>();

        login.dbClass.getAllDeals();

        ListAdapterForDeals myAdapter = new ListAdapterForDeals(allDeals, getContext());
        list.setAdapter(myAdapter);




        return view;
    }

}

class ListAdapterForDeals extends BaseAdapter {
    List<Deals> dealsArr;
    Context context;

    public ListAdapterForDeals(List<Deals> dealsArr, Context context ) {
        this.dealsArr = dealsArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dealsArr.size();
    }

    @Override
    public Object getItem(int position) {
        return dealsArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = ly.inflate(R.layout.view_single_deal, null);

        TextView applicationInfo = v.findViewById(R.id.singleDeal);
        applicationInfo.setText(dealsArr.get(position).toString());
        return v;
    }
}

