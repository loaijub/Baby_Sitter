/*
This class is to connect with the database and to create and to use different queries.
* */

package com.example.babysitter.Classes;

import static com.example.babysitter.ViewAllReports.showListViewForReports;
import static com.example.babysitter.ViewAllUsers.showListView;
import static com.example.babysitter.ViewAllWorkApplications.showAllWorkApplicationsInList;
import static com.example.babysitter.adminAddEmployee.showListViewItems;
import static com.example.babysitter.signUpEmployee.bitmapForCV;
import static com.example.babysitter.signUpEmployee.bitmapForPD;
import static com.example.babysitter.signUpEmployee.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.EmployeeHomePage;
import com.example.babysitter.History;
import com.example.babysitter.JobRequest;
import com.example.babysitter.JobRequestList;
import com.example.babysitter.MapsActivity;
import com.example.babysitter.Profile;
import com.example.babysitter.R;
import com.example.babysitter.ViewAllUsers;
import com.example.babysitter.admin;
import com.example.babysitter.signUpEmployee;
import com.example.babysitter.signUpParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbClass {
    private ProgressDialog dialogLoading;
    private String url = "http://37.142.233.102:131/babysitter/dbMain.php";
    //private String url = "http://192.168.1.10:131/babysitter/dbMain.php";
    private Context context;
    public User currentUser; // to save the current user.
    public static List<User> users; // to list the list of users from database.
    public static List<ProfilePhoto> profilePhoto; // to save a list of profile pictures from database.

    // getters
    public String getUrl() {
        return url;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User newUser) {
        if (newUser != null)
            this.currentUser = new User(newUser);
        else
            this.currentUser = null;
    }

    // constructor
    public dbClass(Context context) {
        this.context = context;
    }

    /**
     * Function logs in the system according to the id and password it gets as parameters.
     *
     * @param id   The id that the user inserted.
     * @param pass The password that the user inserted.
     */
    public void login(String id, String pass) {
        getAllUsers(false);
        getAllProfilePhoto();
        dialogLoading = ProgressDialog.show(context, "",
                "Logging in. Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=login", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        JSONObject userDetails = result.getJSONObject("user");
                        buildUser(userDetails);
                        if (currentUser.getRole().equals("0"))
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new admin()).commit();
                        else if (currentUser.getRole().equals("2")) { // parent ui
                            if (currentUser.getStatus().equals("0")) {
                                ((FragmentActivity) context).startActivity(new Intent(((FragmentActivity) context), MapsActivity.class));
                                ((Activity) context).finish();
                                getUserDetailsFromDatabase();
                            } else {
                                new AlertDialog.Builder(((FragmentActivity) context))
                                        .setTitle("Login failed..")
                                        .setMessage("Your account is disabled, please contact the support")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }

                        } else if (currentUser.getRole().equals("1")) { //employee ui
                            if (currentUser.getStatus().equals("0")) {
                                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new EmployeeHomePage()).commit();
                                getUserDetailsFromDatabase();
                            } else {
                                new AlertDialog.Builder(((FragmentActivity) context))
                                        .setTitle("Login failed..")
                                        .setMessage("Your account is disabled, please contact the support")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        } else {
                            /* both ui (parent + employee) */
                            // we show a box dialog for user to choose for what ui to connect
                            String[] options = {"Parents", "Employees"};
                            AlertDialog.Builder optionsAlert = new AlertDialog.Builder((FragmentActivity) context);
                            optionsAlert.setTitle("Choose one: ").setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // checking the index to know what user choose
                                    if (which == 0) {
                                        // user chose Parents interface
                                        if (currentUser.getStatus().equals("0")) {
                                            ((FragmentActivity) context).startActivity(new Intent(((FragmentActivity) context), MapsActivity.class));
                                            ((Activity) context).finish();
                                            getUserDetailsFromDatabase();
                                        }
                                        // user account is disabled
                                        else {
                                            new AlertDialog.Builder(((FragmentActivity) context))
                                                    .setTitle("Login failed..")
                                                    .setMessage("Your account is disabled, please contact the support")
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }
                                    }


                                    if (which == 1) {
                                        // user chose Employees interface
                                        if (currentUser.getStatus().equals("0")) {
                                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new EmployeeHomePage()).commit();
                                            getUserDetailsFromDatabase();
                                        }
                                        // user account is disabled
                                        else {
                                            new AlertDialog.Builder(((FragmentActivity) context))
                                                    .setTitle("Login failed..")
                                                    .setMessage("Your account is disabled, please contact the support")
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                        }


                                    }

                                }
                            }).show();

                        }

                    } else {
                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Login failed..")
                                .setMessage(result.getString("cause"))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    dialogLoading.dismiss();

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }

    private void buildUser(JSONObject userDetails) {
        String id, fName, lName, phoneNum, birthDate, pass, role, email, status;
        try {
            id = userDetails.getString("id");
            fName = userDetails.getString("first_name");
            lName = userDetails.getString("last_name");
            phoneNum = userDetails.getString("phone_number");
            birthDate = userDetails.getString("birthdate");
            pass = userDetails.getString("password");
            role = userDetails.getString("role");
            email = userDetails.getString("email");
            status = userDetails.getString("status");

            String[] s = birthDate.split("-");
            setCurrentUser(new User(id, fName, lName, phoneNum, new Date(s[2], s[1], s[0]), pass, role, email, status));

        } catch (Exception e) {
            Toast.makeText(context, "error parsing" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * function send an email for admin that the user has forgot his password and he needs to restore it
     *
     * @param uid the id of user who forgot the password.
     */
    public void forgetPassword(String uid) {
        dialogLoading = ProgressDialog.show(context, "",
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
                        sendEmail.putExtra(Intent.EXTRA_TEXT, "User with id number: " + uid + " forgot the password. Please contact the user to restore!");
                        (context).startActivity(sendEmail);
                    }

                    // user is not found in system
                    else {
                        Toast.makeText(context, "You are not registered in the system.\nPlease check if you signed up.", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
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
                map.put("uid", uid);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * function gets all the work applications from the database
     *
     * @param clickedCategory the category that the admin choose.
     */
    public void getAllWorkApplications(String clickedCategory) {

        dialogLoading = ProgressDialog.show(context, "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllWorkApplications", new Response.Listener<String>() {

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

                    if (clickedCategory.equals("allWorkApplications"))
                        showAllWorkApplicationsInList(workApplicationsAsObjectArr, context);
                    else
                        showListViewItems(workApplicationsAsObjectArr, context);


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
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    /**
     * Function gets for the admin all the users in the system that are in the database.
     *
     * @param showListView a flag that indicates if to show the list.
     */
    public void getAllUsers(boolean showListView) {
        users = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllUsers", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray allusers = new JSONArray(response);

                    for (int i = 0; i < allusers.length(); i++) {
                        JSONObject user = allusers.getJSONObject(i);
                        String[] dateOfSubAsString = user.get("birthdate").toString().split("-");
                        Date dateOfbirth = new Date(dateOfSubAsString[2], dateOfSubAsString[1], dateOfSubAsString[0]);

                        users.add(new User(user.getString("id"), user.getString("first_name"), user.getString("last_name"), user.getString("phone_number"), dateOfbirth, user.getString("password"), user.getString("role"), user.getString("email"), user.getString("status")));
                    }

                    if (showListView)
                        showListView(users, context);

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }

    /**
     * Function gets for the admin all the reports in the database.
     */
    public void getAllReports() {
        List<Report> allReports = new ArrayList<>();

        dialogLoading = ProgressDialog.show(context, "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllReports", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                try {
                    JSONArray all_reports = new JSONArray(response);


                    for (int i = 0; i < all_reports.length(); i++) {
                        JSONObject report = all_reports.getJSONObject(i); // gets the single report
                        String[] dateOfSubAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfSub = new Date(dateOfSubAsString[2], dateOfSubAsString[1], dateOfSubAsString[0]);

                        String[] dateOfAccidentAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfAccident = new Date(dateOfAccidentAsString[2], dateOfAccidentAsString[1], dateOfAccidentAsString[0]);

                        // adding the report to the list
                        allReports.add(new Report(report.getString("report_id"), report.getString("applicant_id"), report.getString("reported_user_id"), dateOfSub, dateOfAccident, report.getString("accident_details")));
                    }

                    showListViewForReports(allReports, context);

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
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
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function creates a parent user.
     */
    public void createParentUser() {
        dialogLoading = ProgressDialog.show(context, "",
                "Signing up. Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=createParentUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Success")
                                .setMessage("Your account was created successfully! You can sign in now")
                                .setIcon(R.drawable.ic_baseline_check_circle_24)
                                .show();
                        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
                    } else {
                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Error")
                                .setMessage("Ther was an error while making your account! Try again later")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Error! \n" + response, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                dialogLoading.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }

        }) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", signUpParent.fields[0]);
                map.put("fname", signUpParent.fields[1]);
                map.put("lname", signUpParent.fields[2]);
                map.put("phoneNum", signUpParent.fields[3]);
                map.put("email", signUpParent.fields[4]);
                map.put("pass", signUpParent.fields[5]);
                map.put("city_name", signUpParent.fields[6]);
                map.put("street_name", signUpParent.fields[7]);
                map.put("house_number", signUpParent.fields[8]);
                map.put("number_of_children", signUpParent.fields[9]);
                map.put("children_birthdate", signUpParent.fields[10]);
                map.put("demands", signUpParent.fields[11]);

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }

    /**
     *
     * @param cvImageData
     * @param pdImageData
     */
    public void ImageUploadToServerFunction(String cvImageData, String pdImageData) {

        ByteArrayOutputStream byteArrayOutputStreamObject;
        ByteArrayOutputStream byteArrayOutputStreamObject1;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        byteArrayOutputStreamObject1 = new ByteArrayOutputStream();
        bitmapForCV.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        bitmapForPD.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject1);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        byte[] byteArrayVar1 = byteArrayOutputStreamObject1.toByteArray();


        //image to string (cv)
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        //image to string (pd)
        final String ConvertImage1 = Base64.encodeToString(byteArrayVar1, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialogLoading = ProgressDialog.show(context, "Sending...", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                Toast.makeText(context, string1, Toast.LENGTH_SHORT).show();
                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                dialogLoading.dismiss();
                try {
                    JSONObject result = new JSONObject(string1);
                    String success = result.getString("success");
                    String cause = result.getString("cause");
                    if(success.equals("true")) {
                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Success")
                                .setMessage("Your work application was send successfully. sooner you well get email with answer")
                                .setIcon(R.drawable.ic_baseline_check_circle_24)
                                .show();
                        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
                    }
                    else{

                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Failed!")
                                .setMessage("Failed to send the work application ("+cause+")")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(cvImageData, ConvertImage);
                map.put(pdImageData, ConvertImage1);
                map.put("uid", signUpEmployee.fields[0]);
                map.put("fname", signUpEmployee.fields[1]);
                map.put("lname", signUpEmployee.fields[2]);
                map.put("birthdate", signUpEmployee.fields[3]);
                map.put("email", signUpEmployee.fields[4]);
                map.put("phone_number", signUpEmployee.fields[5]);
                map.put("city_name", signUpEmployee.fields[6]);
                map.put("street_name", signUpEmployee.fields[7]);
                map.put("house_number", signUpEmployee.fields[8]);
                map.put("worked_as_babysitter", signUpEmployee.fields[9]);
                map.put("special_demands", signUpEmployee.fields[10]);

                String FinalData = imageProcessClass.ImageHttpRequest(url + "?action=uploadFile", map);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }


    /**
     * Function gets all the deals from the database.
     *
     * @param historyOrRequests a string that indicates for what we are using th deal (for history, or for requests)
     * @param progress          the progress bar
     */
        public Void getAllDeals(String historyOrRequests, ProgressBar progress) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllDealsForUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (progress != null)
                    progress.setVisibility(View.GONE);

                try {
                    // we get all the deals that are related to the user in an array.
                    JSONArray allDealsArr = new JSONArray(response);
                    for (int i = 0; i < allDealsArr.length(); i++) {

                        // each item on array is an object
                        JSONObject deal = allDealsArr.getJSONObject(i);

                        String dealId = deal.getString("deal_id");
                        String dealEmployeeId = deal.getString("employee_id");
                        String dealParentId = deal.getString("parent_id");

                        String dealEmployeeAccepted = deal.getString("employee_accepted");
                        String dealHasDone = deal.getString("has_done");
                        String completedDealDate = deal.getString("completed_deal_date");

                        // first we check if the date from the database is not null
                        Date actualCompletedDealDate = null;
                        if (!completedDealDate.equals("")) {
                            String[] fieldsOfDate = completedDealDate.split("-");
                            actualCompletedDealDate = new Date(fieldsOfDate[2], fieldsOfDate[1], fieldsOfDate[0]);
                        }

                        Deals tempDeal = new Deals(dealId, dealEmployeeId, dealParentId, dealEmployeeAccepted, dealHasDone, actualCompletedDealDate);

                        // we add the deal object to the listView
                        if (historyOrRequests.equals("history1")) {
                            if (!dealParentId.equals(currentUser.getId())) {
                                History.allDeals.add(tempDeal);
                            }
                        }else if(historyOrRequests.equals("history")){
                            if (!dealEmployeeId.equals(currentUser.getId())) {
                                History.allDeals.add(tempDeal);
                            }
                        } else if(historyOrRequests.equals("job1")) {
                            if (!dealParentId.equals(currentUser.getId()))
                                JobRequestList.allJobs.add(tempDeal);
                        } else {
                            if (!dealEmployeeId.equals(currentUser.getId()))
                                JobRequest.allJobs.add(tempDeal);
                        }

                    }
                    // if the list is not empty, we show the deals for the user
                    if (History.list != null || JobRequestList.list != null) {
                        if (historyOrRequests.contains("history")) {
                            // we filter the array to show only the done deals
                            History.filterArray();
                            ListAdapterForDeals myAdapter = new ListAdapterForDeals(History.allDeals, context,historyOrRequests);
                            History.list.setAdapter(myAdapter);
                        } else {
                            // we filter the deals to show only the ones that don't have an answer yet

                            if (historyOrRequests.equals("job1")) {
                                JobRequestList.filterList();
                                ListAdapterForJobEmployee myAdapter = new ListAdapterForJobEmployee(JobRequestList.allJobs, context);
                                JobRequestList.list.setAdapter(myAdapter);
                            } else {
                                JobRequest.filterList();
                                ListAdapterForJob myAdapter = new ListAdapterForJob(JobRequest.allJobs, context);
                                JobRequest.list.setAdapter(myAdapter);
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        return null;
    }
    public Void getAllDealsCountForUser(String uid) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllDealsForUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // we get all the deals that are related to the user in an array.
                    JSONArray allDealsArr = new JSONArray(response);
                    MapsActivity.dealsMade.setText("" + allDealsArr.length());
                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", uid);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        return null;
    }


    /**
     * Function gets all the user details from the database and returns it as an array of strings
     */
    public void getUserDetailsFromDatabase() {

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getCurrentUserDetails", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONArray allDetailsArr = new JSONArray(response);

                    // to save the info about parent OR employee
                    User user = null;
                    JSONObject currentAddress = allDetailsArr.getJSONObject(0);

                    Address userAddress = new Address(currentAddress.getString("city"), currentAddress.getString("street"), currentAddress.getString("house_number"));
                    if (currentUser.getRole().equals("1")) {
                        JSONObject currentEmployee = allDetailsArr.getJSONObject(1);
                        user = new Employee(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getPhoneNumber(), currentUser.getBirthDate(), currentUser.getPassword(), currentUser.getEmail(), currentUser.getRole(), currentEmployee.getString("status"), currentEmployee.getString("rate"), currentEmployee.getString("specialDemands"), currentEmployee.getString("workingHoursInMonth"), currentEmployee.getString("experience"), userAddress);
                    } else {
                        JSONObject currentParent = allDetailsArr.getJSONObject(1);
                        user = new Parent(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getPhoneNumber(), currentUser.getBirthDate(), currentUser.getPassword(), currentUser.getEmail(), currentUser.getRole(), currentParent.getString("status"), currentParent.getString("rate"), currentParent.getString("specialDemands"), currentParent.getString("numberOfChildren"), userAddress);
                    }
                    try {
                        user.setProfilePhoto(new ProfilePhoto(user.getId(), allDetailsArr.getJSONObject(2).getString("profile_image_path")));
                    } catch (Exception ex) {
                    }
                    Profile.currentUser = user;
                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                map.put("urole", currentUser.getRole());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function gets a password and a dialog, it tries to change the password of the current user in the database to the new password. At the end it closes the dialog.
     *
     * @param newPasswordToChange The new password of the current user.
     * @param dialog              The dialog that shows for the user to enter his new password.
     */
    public void changePasswordOfCurrentUser(String newPasswordToChange, Dialog dialog) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=changePasswordForCurrentUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");

                    // changing the password in the database was successful
                    if (success.equals("true")) {
                        Toast.makeText(context, "Changes were saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Changes were not saved. Please try again!", Toast.LENGTH_SHORT).show();
                    }

                    // whatever the result was, we close the dialog
                    dialog.dismiss();


                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                map.put("newPassword", newPasswordToChange);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    /**
     * Function adds a new deal to the database
     *
     * @param dealToAdd the new deal to add to database.
     */
    public void sendWorkRequest(Deals dealToAdd) {
        StringRequest request = new StringRequest(Request.Method.POST, this.getUrl() + "?action=addDeal", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        // if the adding to the database was successful, then we add the new deal to the home page of the employee.
                        Toast.makeText(context, "ADDING WAS SUCCESSFUL", Toast.LENGTH_LONG).show();

                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Sending job request failed..")
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
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<>();
                map.put("employee_id", dealToAdd.getEmployeeId());
                map.put("parent_id", dealToAdd.getParentId());
                map.put("employee_accepted", dealToAdd.getEmployeeAccepted());
                map.put("has_done", dealToAdd.isHasDone());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }


    /**
     * Function changes the current user phone number.
     *
     * @param newPhoneNum the new phone number.
     * @param dialog      The dialog
     * @param phoneNumber the textView that holds the phone number
     */
    public void changePhoneOfCurrentUser(String newPhoneNum, Dialog dialog, TextView phoneNumber) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=changePhoneForCurrentUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");

                    // changing the password in the database was successful
                    if (success.equals("true")) {
                        Toast.makeText(context, "Changes were saved successfully", Toast.LENGTH_SHORT).show();
                        currentUser.setPhoneNumber(newPhoneNum);
                    } else {
                        Toast.makeText(context, "Changes were not saved. Please try again!", Toast.LENGTH_SHORT).show();
                    }

                    // whatever the result was, we close the dialog
                    dialog.dismiss();
                    phoneNumber.setText(newPhoneNum);


                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                map.put("newPhoneNumber", newPhoneNum);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function changes the current user email.
     *
     * @param newEmail the new email.
     * @param dialog   The dialog to show/dismiss
     * @param email    the textView that holds the email.
     */
    public void changeEmailOfCurrentUser(String newEmail, Dialog dialog, TextView email) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=changeEmailForCurrentUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");

                    // changing the email in the database was successful
                    if (success.equals("true")) {
                        Toast.makeText(context, "Changes were saved successfully", Toast.LENGTH_SHORT).show();
                        currentUser.setEmail(newEmail);
                    } else {
                        Toast.makeText(context, "Changes were not saved. Please try again!", Toast.LENGTH_SHORT).show();
                    }

                    // whatever the result was, we close the dialog
                    dialog.dismiss();
                    email.setText(newEmail);


                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                map.put("newEmail", newEmail);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function changes the address of current user.
     *
     * @param newAddress the new address.
     * @param dialog     The dialog to show/dismiss
     * @param address    the textView that holds the address.
     */
    public void changeAddressOfCurrentUser(String[] newAddress, Dialog dialog, TextView address) {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=changeAddressForCurrentUser", new Response.Listener<String>() {
            Address newAddrss = new Address(newAddress[0], newAddress[1], newAddress[2]);

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    // changing the email in the database was successful
                    if (success.equals("true")) {
                        Toast.makeText(context, "Changes were saved successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Changes were not saved. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                    currentUser.setAddress(newAddrss);
                    // whatever the result was, we close the dialog
                    dialog.dismiss();
                    address.setText(newAddrss.toString());


                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", currentUser.getId());
                map.put("newCity", newAddress[0]);
                map.put("newStreet", newAddress[1]);
                map.put("newHouse", newAddress[2]);
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }


    /**
     * Function gets all te profile pictures in a list.
     */
    public void getAllProfilePhoto() {
        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllProfilePhotos", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray allUsersPhotos = new JSONArray(response);
                    profilePhoto = new ArrayList<>();
                    for (int i = 0; i < allUsersPhotos.length(); i++) {
                        JSONObject userPhoto = allUsersPhotos.getJSONObject(i);
                        profilePhoto.add(new ProfilePhoto(userPhoto.getString("id"), userPhoto.getString("profile_image_path")));
                    }

                } catch (Exception e) {
                    Toast.makeText(context, "Json parse error" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function deletes a user (only changes the status in the database)
     *
     * @param uid The id of user to delete.
     * @param fg  The fragment activity.
     */
    public void deleteUser(String uid, FragmentActivity fg) {
        dialogLoading = ProgressDialog.show(context, "",
                "Deleting... Please wait", true);
        StringRequest request = new StringRequest(Request.Method.POST, this.getUrl() + "?action=deleteUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();

                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Error deleting user..")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    fg.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new ViewAllUsers()).commit();
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

                Map<String, String> map = new HashMap<>();
                map.put("user_id", uid);

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    /**
     * Function gets a report and adds it to the database.
     *
     * @param reportToAddFor The report to add.
     */
    public void addReport(Report reportToAddFor) {
        StringRequest request = new StringRequest(Request.Method.POST, getUrl() + "?action=addReport", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        // if the adding to the database was successful, we inform the user
                        Toast.makeText(context, "Report was sent successfully", Toast.LENGTH_LONG).show();

                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Sending the report failed..")
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
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<>();
                map.put("applicant_id", reportToAddFor.getApplicantId());
                map.put("reported_user_id", reportToAddFor.getReportedUserId());
                map.put("date_of_accident", reportToAddFor.getDateOfAccident().toString());
                map.put("accident_details", reportToAddFor.getAccidentDetails());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function removes a deal.
     *
     * @param dealId The id of the deal to remove.
     * @param fg     The fragment activity.
     */
    public void removeDeal(String dealId, FragmentActivity fg) {
        StringRequest request = new StringRequest(Request.Method.POST, getUrl() + "?action=removeJobRequest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        // if the adding to the database was successful, we inform the user
                        Toast.makeText(context, "Deal was canceled successfully", Toast.LENGTH_LONG).show();
                        fg.getSupportFragmentManager().beginTransaction().replace(R.id.map, new JobRequest()).addToBackStack(null).commit();
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("canceling the deal failed..")
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
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<>();
                map.put("deal_id", dealId);

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    /**
     * Function edits the user's details.
     *
     * @param user The user to change his details.
     */
    public void editUser(User user) {
        dialogLoading = ProgressDialog.show(context, "",
                "Saving... Please wait", true);
        StringRequest request = new StringRequest(Request.Method.POST, getUrl() + "?action=editUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                try {
                    JSONObject result = new JSONObject(response);
                    String success = result.getString("success");
                    if (success.equals("true")) {
                        new AlertDialog.Builder(((FragmentActivity) context))
                                .setTitle("Successfully updated")
                                .setMessage("The user details are updated successfully")
                                .setIcon(R.drawable.ic_baseline_check_circle_24)
                                .show();
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Sending the data failed..")
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

                Map<String, String> map = new HashMap<>();
                map.put("fname", user.getFirstName());
                map.put("lname", user.getLastName());
                map.put("phone", user.getPhoneNumber());
                map.put("email", user.getEmail());
                map.put("pass", user.getPassword());
                map.put("id", user.getId());
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}