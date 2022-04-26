package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends Fragment {
    View view;
    ListView list;


    String tutorials[]
            = {"Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "UGC NET CS", "CS Subjects",
            "Web Technologies"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history, container, false);

        list = view.findViewById(R.id.listOfHistory);









        // code for getting data from database

        //cmd- > ipconfig -> Wireless LAN adapter -> copy IPv4 Address
        String url = "http://172.16.0.67/?????????????????";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_users = jsonResponse.getJSONArray("main_course");


                            int k = 0; // to run over all the arrays to get the info from the json array
                            for (int i = 0; i < jsonArray_users.length(); i++) {

                                JSONObject jsonObject = jsonArray_users.getJSONObject(i);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);





        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, tutorials);
        list.setAdapter(arr);

        return view;
    }


}
