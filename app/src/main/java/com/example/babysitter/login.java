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
    public static String url = "http://192.168.7.137//babysitter/dbMain.php";
    View view;
    Dialog dialog;
    public static User currentUser = null;
    ProgressDialog dialogLoading;

    TextView forgetPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);

        forgetPassword = view.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
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
                login(id.getText().toString(), password.getText().toString());
            }
        });


        return view;

    }

    private void forgetPassword() {
        // function send an email for admin that the user has forgot his password and he needs to restore it
        EditText userId = view.findViewById(R.id.uid);

        dialogLoading = ProgressDialog.show(getContext(), "",
                "Checking... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=checkUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();

                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("exists");

                    // user is found in system
                    if (success.equals("true")) {

                        // if the user is in the system, it can send the admin a request to restore the password
                        Intent sendEmail = new Intent(Intent.ACTION_SEND);
                        sendEmail.setData(Uri.parse("mailto:"));
                        sendEmail.setType("message/rfc822");
                        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                "jow.isaac@gmail.com", "lo2ay.2000@gmail.com"
                        });
                        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Restore password");
                        sendEmail.putExtra(Intent.EXTRA_TEXT, "User with id number: " + userId.getText().toString() + " forgot the password. Please contact the user to restore!");
                        startActivity(sendEmail);
                    }

                    // user is not found in system
                    else {
                        Toast.makeText(getContext(), "You are not registered in the system.\nPlease check if you signed up.", Toast.LENGTH_LONG).show();
                    }


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
                map.put("uid", userId.getText().toString());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

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


    private void login(String id, String pass) {
        dialogLoading = ProgressDialog.show(getContext(), "",
                "Logging in. Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=login", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        JSONObject userDetails = result.getJSONObject("user");
                        buildUser(userDetails);
                        // admin is tyring to log in - it shows the admin panel
                        if (currentUser.getRole().equals("0"))
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new admin()).commit();
                    } else
                        // employee is trying to log in - it shows the the employee interface
                        if (currentUser.getRole().equals("1")) {
                            Toast.makeText(getContext(), "logging in to employee", Toast.LENGTH_LONG).show();
                        } else
                            // employee is trying to log in - it shows the the parents interface
                            if (currentUser.getRole().equals("2")) {
                                Toast.makeText(getContext(), "logging in to parents", Toast.LENGTH_LONG).show();
                            } else {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Login failed..")
                                        .setMessage(result.getString("cause"))
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Json parse error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", id);
                map.put("pass", pass);
                return map;

            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }

    private void buildUser(JSONObject userDetails) {
        String id, fName, lName, phoneNum, birthDate, pass, role, email;
        try {
            id = userDetails.getString("id");
            fName = userDetails.getString("first_name");
            lName = userDetails.getString("last_name");
            phoneNum = userDetails.getString("phone_number");
            birthDate = userDetails.getString("birthdate");
            pass = userDetails.getString("password");
            role = userDetails.getString("role");
            email = userDetails.getString("email");

            String[] s = birthDate.split("-");
            currentUser = new User(id, fName, lName, phoneNum, new Date(s[2], s[1], s[0]), pass, role, email);
        } catch (Exception e) {
            Toast.makeText(getContext(), "error parsing" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
