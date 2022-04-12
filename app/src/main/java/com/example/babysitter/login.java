package com.example.babysitter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.*;

import java.util.HashMap;
import java.util.Map;

public class login extends Fragment {
    public static String url = "http://172.16.5.244/babysitter/";
    View view;
    Dialog dialog;
    User currentUser = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login,container,false);
        //if signup pressed
        view.findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //if login pressed

        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id,password;
                id = view.findViewById(R.id.uid);
                password = view.findViewById(R.id.password);
                // if the login success
                if(login(id.getText().toString(),password.getText().toString())){
                    // if the user is admin
                    if(currentUser != null && currentUser.getRole().equals("0"))
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new admin()).commit();

                }
            }
        });


        return view;

    }

    private void showDialog() {
        dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
        dialog.findViewById(R.id.signUpAsEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new signUpEmployee()).commit();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.signUpAsParent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new signUpParent()).commit();
                dialog.dismiss();
            }
        });

    }



    private boolean login(String id,String pass){


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

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

        return false;
    }
}
