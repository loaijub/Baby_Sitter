package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.babysitter.Classes.Employee;
import com.example.babysitter.Classes.WorkApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class adminAddEmployee extends Fragment {


    ListView list;
    View view;
    ProgressDialog dialogLoading;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.admin_add_employee, container, false);

        list = view.findViewById(R.id.listOfWorkApplications);


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


                        // splitting the fields of the object to variables to create WorkApplication object
                        String employeeId = application.getString("employee_id");
                        String employeeFirstName = application.getString("employee_fname");
                        String employeeLastName = application.getString("employee_lname");
                        String employeePhoneNumber = application.getString("employee_phone_num");
                        String employeeBirthDate = application.getString("employee_birthdate");
                        String employeeEmail = application.getString("employee_email");
                        String employeeExperience = application.getString("employee_experience");
                        String employeeSpecialDemands = application.getString("employee_special_demands");
                        String status = application.getString("status");

                        // converting the birthdate from String to date
                        String[] fieldsOfDate = employeeBirthDate.split("-");
                        Date actualEmployeeBirthdate = new Date(fieldsOfDate[0], fieldsOfDate[1], fieldsOfDate[2]);

                        // creating the WorkApplication object
                        WorkApplication tempApplicationObj = new WorkApplication(employeeId, employeeFirstName, employeeLastName, employeePhoneNumber, actualEmployeeBirthdate, employeeEmail, employeeExperience, employeeSpecialDemands, status);

                        // adding the object to the array
                        workApplicationsAsObjectArr[j++] = tempApplicationObj;
                    }

                    // now workApplicationsAsObjectArr array has objects from WorkApplication type, and has all the information from the database.
                    // we call showListViewItems function and we send the array of objects.
                    showListViewItems(workApplicationsAsObjectArr);


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


    private void showListViewItems(WorkApplication[] allApplicationArr) { // function gets an array of WorkApplication objects and shows the information in it in ListView

        ListAdapterForAddEmployee myAdapter = new ListAdapterForAddEmployee(allApplicationArr, getContext());
        list.setAdapter(myAdapter);

    }


}

class ListAdapterForAddEmployee extends BaseAdapter {
    WorkApplication[] applicationsArr;
    Context context;

    public ListAdapterForAddEmployee(WorkApplication[] applicationsArr, Context context) {
        this.applicationsArr = applicationsArr;
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
        View v = ly.inflate(R.layout.admin_listview_add_employee_item, null);

        TextView applicationInfo = v.findViewById(R.id.workApplicationInfo);
        Button addEmployeeBtn = v.findViewById(R.id.addBtn);
        Button deleteApplicationBtn = v.findViewById(R.id.deleteBtn);

        // setting listener for the add button
        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployeeFromWorkApplication(applicationsArr[position].getEmployeeId());
            }
        });

        // setting listener for the deny button
        deleteApplicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWorkApplication(applicationsArr[position].getEmployeeId());
            }
        });


        applicationInfo.setText(applicationsArr[position].toString());

        return v;
    }


    ///////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    /////////////////////also fix the database and connect the tables/////////////
    ////////////////// continue from here ///////////////
    ///////////////////////////////////////////////////
    private void addEmployeeFromWorkApplication(String idOfEmployeeToAdd) {
        // function adds the employee from the current work application to the system.


        // generating a default password for the new employee
        String password = generateDefaultPassword();
        Toast.makeText(context, password, Toast.LENGTH_LONG).show();


    }

    private void deleteWorkApplication(String idOfEmployeeToDeny) { // function deletes the work application from the list view and updates the data base status.


    }

    private String generateDefaultPassword() { // function generates a default password and returns it

        String password = "";
        String characters = "123456789abcdefghijklmnopqrstuvwxyz";
        final int passwordLength = 9; // the default password length is 9
        int length = characters.length();

        for (int i = 0; i < passwordLength; i++) {
            password += characters.charAt((int) Math.floor(Math.random() * length));
        }

        return password;

    }
}