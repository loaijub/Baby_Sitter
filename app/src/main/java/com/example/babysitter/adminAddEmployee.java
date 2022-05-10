package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class adminAddEmployee extends Fragment {

    public static String url = "http://192.168.200.137/babysitter/dbMain.php";
    ListView list;
    View view;
    ProgressDialog dialogLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.admin_add_employee, container, false);

        list = view.findViewById(R.id.listOfWorkApplications);
        getAllWorkApplications();


        return view;
    }

    //////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////// CONTINUE ////////////
    ////////////////////////////////////////////
    ///////////////////////////////////////////
    private void getAllWorkApplications() {
        dialogLoading = ProgressDialog.show(getContext(), "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllWorkApplications", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray allreports = new JSONArray(response);

                    for (int i = 0; i < allreports.length(); i++) {
                        JSONObject report = allreports.getJSONObject(i);

                        Toast.makeText(getContext(), report.getString("applicantId"), Toast.LENGTH_LONG).show();

                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getContext(), "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
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