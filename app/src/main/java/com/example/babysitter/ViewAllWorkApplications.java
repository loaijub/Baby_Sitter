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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewAllWorkApplications extends Fragment {

    ListView list;
    View view;
    ProgressDialog dialogLoading;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.admin_view_all_workapplications, container, false);

        list = view.findViewById(R.id.listOfAllWorkApplications);


        // calling function that gets all the work applications from the data base
        getAllWorkApplications();




        return view;
    }
    private void getAllWorkApplications() {
        // function gets all the work applications from the data base

        dialogLoading = ProgressDialog.show(getContext(), "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, login.url + "?action=getAllWorkApplications", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                dialogLoading.dismiss();

                try {
                    JSONArray allWorkApplications = new JSONArray(response);
                    WorkApplication[] workApplicationsAsObjectArr = new WorkApplication[allWorkApplications.length()];
                    int j = 0; // an index to go over the workApplicationsAsObjectArr array

                    for (int i = 0; i < allWorkApplications.length(); i++) {
                        JSONObject application = allWorkApplications.getJSONObject(i);


                        // first we check the status, only if the status is 3 (admin hasn't checked it yet) it displays it.
                        String status = application.getString("status");

                        // splitting the fields of the object to variables to create WorkApplication object
                        String employeeId = application.getString("employee_id");
                        String employeeFirstName = application.getString("employee_fname");
                        String employeeLastName = application.getString("employee_lname");
                        String address = application.getString("address");
                        String employeePhoneNumber = application.getString("employee_phone_num");
                        String employeeBirthDate = application.getString("employee_birthdate");
                        String employeeEmail = application.getString("employee_email");
                        String employeeExperience = application.getString("employee_experience");
                        String employeeSpecialDemands = application.getString("employee_special_demands");


                        // converting the birthdate from String to date
                        String[] fieldsOfDate = employeeBirthDate.split("-");
                        Date actualEmployeeBirthdate = new Date(fieldsOfDate[2], fieldsOfDate[1], fieldsOfDate[0]);

                        // creating the WorkApplication object
                        WorkApplication tempApplicationObj = new WorkApplication(employeeId, employeeFirstName, employeeLastName, address, employeePhoneNumber, actualEmployeeBirthdate, employeeEmail, employeeExperience, employeeSpecialDemands, status);

                        // adding the object to the array
                        workApplicationsAsObjectArr[j++] = tempApplicationObj;

                    }

                    // now workApplicationsAsObjectArr array has objects from WorkApplication type, and has all the information from the database.
                    // we call showListViewItems function and we send the array of objects.
                    showAllWorkApplicationsInList(workApplicationsAsObjectArr);


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    private void showAllWorkApplicationsInList(WorkApplication[] allWorkappications) { // function gets an array of objects of all work applications and organize it in list view

        ListAdapterForAllWorkApplications allWorkAppAdapter = new ListAdapterForAllWorkApplications(allWorkappications, getContext(), getActivity());
        list.setAdapter(allWorkAppAdapter);

    }
}

class ListAdapterForAllWorkApplications extends BaseAdapter {
    WorkApplication[] applicationsArr;
    Context context;
    FragmentActivity activity;

    public ListAdapterForAllWorkApplications(WorkApplication[] applicationsArr, Context context, FragmentActivity f) {
        this.applicationsArr = applicationsArr;
        this.activity = f;
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
