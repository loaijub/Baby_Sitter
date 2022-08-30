package com.example.babysitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminAddEmployee extends Fragment {


    static ListView list;
    View view;
    ProgressDialog dialogLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.admin_add_employee, container, false);

        list = view.findViewById(R.id.listOfWorkApplications);

        // calling function that gets all the work applications from the data base

        login.dbClass.getAllWorkApplications("addEmployee");

        return view;
    }


    public static void showListViewItems(WorkApplication[] allApplicationArr, Context context) { // function gets an array of WorkApplication objects and shows the information in it in ListView

        List<WorkApplication> onlyUncheckedApps = new ArrayList<>();
        for (int i = 0; i < allApplicationArr.length; i++) {
            if (allApplicationArr[i].getStatus().equals("3")) {
                onlyUncheckedApps.add(allApplicationArr[i]);
            }
        }

        // converting from a list to array
        WorkApplication[] applicationsArr = new WorkApplication[onlyUncheckedApps.size()];
        for (int i = 0; i < onlyUncheckedApps.size(); i++)
            applicationsArr[i] = onlyUncheckedApps.get(i);


        ListAdapterForAddEmployee myAdapter = new ListAdapterForAddEmployee(applicationsArr, context);
        list.setAdapter(myAdapter);

    }


}

class ListAdapterForAddEmployee extends BaseAdapter {
    WorkApplication[] applicationsArr;
    Context context;
    FragmentActivity activity;

    public ListAdapterForAddEmployee(WorkApplication[] applicationsArr, Context context) {
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
        View v = ly.inflate(R.layout.admin_listview_add_employee_item, null);

        TextView applicationInfo = v.findViewById(R.id.workApplicationInfo);
        Button addEmployeeBtn = v.findViewById(R.id.addBtn);
        Button deleteApplicationBtn = v.findViewById(R.id.deleteBtn);
        Button showSubmittedFiles = v.findViewById(R.id.ShowSubmittedFiles);

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
                denyWorkApplication(applicationsArr[position].getEmployeeId(), applicationsArr[position].getEmployeeEmail());
            }
        });
        // setting listener for the showSubmittedFiles
        showSubmittedFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSubmittedFiles(applicationsArr[position].getEmployeeId());
            }
        });

        applicationInfo.setText(applicationsArr[position].toString());


        return v;
    }

    private void ShowSubmittedFiles(String employeeId) {
        // function show for user the popup to change his phone number

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.show_submitted_files);
        dialog.show();

        // the buttons in the dialog
        Button btnCV = dialog.findViewById(R.id.btnCV);
        Button btnPD = dialog.findViewById(R.id.btnPD);
        btnCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = login.dbClass.getUrl();
                intent.setDataAndType(Uri.parse(url.substring(0, url.length() - 10) + employeeId + "/cvimage.png"), "image/*");
                context.startActivity(intent);
            }
        });
        btnPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = login.dbClass.getUrl();
                intent.setDataAndType(Uri.parse(url.substring(0, url.length() - 10) + employeeId + "/pdimage.png"), "image/*");
                context.startActivity(intent);
            }
        });

    }


    private void addEmployeeFromWorkApplication(String idOfEmployeeToAdd) {
        // function adds the employee from the current work application to the system.


        WorkApplication chosenEmployee = null;
        for (int i = 0; i < applicationsArr.length; i++)
            if (applicationsArr[i].getEmployeeId().equals(idOfEmployeeToAdd))
                chosenEmployee = applicationsArr[i];

        String fname = chosenEmployee.getEmployeeFirstName();
        String lname = chosenEmployee.getEmployeeLastName();
        String phoneNumber = chosenEmployee.getEmployeePhoneNumber();
        Date birthdate = chosenEmployee.getEmployeeBirthDate();
        String email = chosenEmployee.getEmployeeEmail();
        String address = chosenEmployee.getAddress();
        String specialDemands = chosenEmployee.getEmployeeSpecialDemands();
        String experience = chosenEmployee.getEmployeeExperience();


        // generating a default password for the new employee
        String password = generateDefaultPassword();

        ProgressDialog dialogLoading;
        dialogLoading = ProgressDialog.show(context, "",
                "Creating an Employee account... Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, login.dbClass.getUrl() + "?action=createEmployeeUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                dialogLoading.dismiss();

                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success"); // if adding was successful
                    String msg = result.getString("message"); // if user is already in the system but as a parent
                    if (success.equals("true")) {
                        // user is new in the system
                        if (msg.equals("")) {
                            String bodyOfText = "Dear babysitter, we are glad to inform you that your work application has been approved! \nYour temporary password is: " + password + "\nPlease make sure to change it the moment you log in.\nWelcome to our community! \n\n\nBabysitterFinder team";

                            // we send the user his temporary password
                            // via email
                            Intent sendEmail = new Intent(Intent.ACTION_SEND);
                            sendEmail.setData(Uri.parse("mailto:"));
                            sendEmail.setType("message/rfc822");
                            sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                    email
                            });
                            sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Congratulations! Your work application has been approved!");
                            sendEmail.putExtra(Intent.EXTRA_TEXT, bodyOfText);
                            context.startActivity(sendEmail);
                            Toast.makeText(context, "User was added successfully!", Toast.LENGTH_SHORT).show();
                        }

                        // user is already in the system as a parent and signed up as an employee
                        if (msg.equals("employee_is_parent")) {
                            String bodyOfText = "Dear babysitter, we are glad to inform you that your work application has been approved! \nWe are glad that you joined also our babysitter community!\nWelcome to our community! \n\n\nBabysitterFinder team";

                            // via email
                            Intent sendEmail = new Intent(Intent.ACTION_SEND);
                            sendEmail.setData(Uri.parse("mailto:"));
                            sendEmail.setType("message/rfc822");
                            sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                    email
                            });
                            sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Congratulations! Your work application has been approved!");
                            sendEmail.putExtra(Intent.EXTRA_TEXT, bodyOfText);
                            context.startActivity(sendEmail);
                            Toast.makeText(context, "User was added successfully!", Toast.LENGTH_SHORT).show();
                        }

                        // now we delete the work application from the array, so it only shows the unchecked applications only
                        deleteApprovedWorkApplication(applicationsArr, idOfEmployeeToAdd);


                        // now we refresh the page so the admin wouldn't see the employee he added
                        // Reload current fragment
                        activity.getSupportFragmentManager().popBackStack();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new adminAddEmployee()).addToBackStack(null).commit();


                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Creating user failed..")
                                .setMessage(result.getString("cause"))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", idOfEmployeeToAdd);
                map.put("fname", fname);
                map.put("lname", lname);
                map.put("phoneNum", phoneNumber);
                map.put("birthDate", birthdate.toString());
                map.put("email", email);
                map.put("pass", password);
                map.put("city_name", address);
                map.put("street_name", address);
                map.put("house_number", address);
                map.put("experience", experience);
                map.put("demands", specialDemands);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }

    // this function works when an admin approved a work application and we want to delete it from the list view
    private void deleteApprovedWorkApplication(WorkApplication[] applicationsArr, String idOfEmployeeToDeleteWorkApp) { // function gets an array of work applications and the id of the employee we want to delete his work application

        int index = -1;
        for (int i = 0; i < applicationsArr.length; i++)
            if (applicationsArr[i].getEmployeeId().equals(idOfEmployeeToDeleteWorkApp))
                index = i;

        if (index != -1) // the application was found
        {
            for (int i = index; i < applicationsArr.length - 1; i++)
                applicationsArr[i] = applicationsArr[i + 1];
        }
        applicationsArr[applicationsArr.length - 1] = null;

    }

    private void denyWorkApplication(String idOfEmployeeToDeny, String emailOfEmployeeToDeny) { // function deletes the work application from the list view and updates the data base status.

        ProgressDialog dialogLoading;
        dialogLoading = ProgressDialog.show(context, "",
                "Deleting work application... Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, login.dbClass.getUrl() + "?action=denyWorkapplication", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        String bodyOfText = "Thank you for applying for our application.\n Sadly your work application was denied. \n \n \n Wishing you all the best, \n BabysitterFinder team";

                        // we send the applicant that his work application was denied
                        Intent sendEmail = new Intent(Intent.ACTION_SEND);
                        sendEmail.setData(Uri.parse("mailto:"));
                        sendEmail.setType("message/rfc822");
                        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{
                                emailOfEmployeeToDeny
                        });
                        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Sorry, your work application was denied");
                        sendEmail.putExtra(Intent.EXTRA_TEXT, bodyOfText);
                        context.startActivity(sendEmail);
                        Toast.makeText(context, "Work application was denied successfully", Toast.LENGTH_SHORT).show();

                        // now we delete the work application from the array, so it only shows the unchecked applications only
                        deleteApprovedWorkApplication(applicationsArr, idOfEmployeeToDeny);

                        // now we refresh the page so the admin wouldn't see the employee he denied
                        // Reload current fragment
                        activity.getSupportFragmentManager().popBackStack();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new adminAddEmployee()).addToBackStack(null).commit();


                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Denying the work application failed..")
                                .setMessage(result.getString("cause"))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } catch (Exception e) {
                    // Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", idOfEmployeeToDeny);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

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