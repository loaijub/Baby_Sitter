package com.example.babysitter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.*;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends Fragment {
    View view;
    Dialog dialog;
    static dbClass dbClass;
    TextView forgetPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);
        dbClass = new dbClass(getContext());
        forgetPassword = view.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = ((EditText)view.findViewById(R.id.uid)).getText().toString();
                if(!uid.equals(""))
                    dbClass.forgetPassword(uid);
                else
                    Toast.makeText(getContext(), "Please enter your ID", Toast.LENGTH_SHORT).show();
            }
        });

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
                EditText id, password;
                id = view.findViewById(R.id.uid);
                password = view.findViewById(R.id.password);
                // login request

                dbClass.login(id.getText().toString(), password.getText().toString());
            }
        });


        return view;

    }


    //showing sign up dialog
    private void showDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
        dialog.findViewById(R.id.signUpAsEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new signUpEmployee()).addToBackStack(null).commit();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.signUpAsParent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new signUpParent()).addToBackStack(null).commit();
                dialog.dismiss();
            }
        });

    }



}
