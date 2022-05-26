package com.example.babysitter.Classes;

import static com.example.babysitter.ViewAllReports.showListViewForReports;
import static com.example.babysitter.ViewAllUsers.showListView;
import static com.example.babysitter.ViewAllWorkApplications.showAllWorkApplicationsInList;
import static com.example.babysitter.adminAddEmployee.showListViewItems;
import static com.example.babysitter.signUpEmployee.bitmapForCV;
import static com.example.babysitter.signUpEmployee.bitmapForPD;
import static com.example.babysitter.signUpParent.fields;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.MapsActivity;
import com.example.babysitter.R;
import com.example.babysitter.admin;
import com.example.babysitter.login;
import com.example.babysitter.signUpEmployee;
import com.example.babysitter.signUpParent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dbClass {
    private ProgressDialog dialogLoading;
    private String url = "http://87.69.227.67:131/babysitter/dbMain.php" ;
    private Context context;
    public User currentUser;

    public String getUrl(){
        return url;
    }
    public User getCurrentUser(){
        return this.currentUser;
    }
    public void setCurrentUser(User newUser){
        if(newUser != null)
            this.currentUser = new User(newUser);
        else
            this.currentUser = null;
    }
    public dbClass(Context context){
        this.context = context;
    }
    public void login(String id, String pass) {

        dialogLoading = ProgressDialog.show(context, "",
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
                        if (currentUser.getRole().equals("0"))
                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, new admin()).commit();
                        else if(currentUser.getRole().equals("2")) { // parent ui
                            ((FragmentActivity) context).startActivity(new Intent(((FragmentActivity) context), MapsActivity.class));
                            ((Activity)context).finish();
                        }
                    } else {
                        new AlertDialog.Builder(((FragmentActivity)context))
                                .setTitle("Login failed..")
                                .setMessage(result.getString("cause"))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }


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
            setCurrentUser(new User(id, fName, lName, phoneNum, new Date(s[2], s[1], s[0]), pass, role, email));
        } catch (Exception e) {
            Toast.makeText(context, "error parsing" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void forgetPassword(String uid) {
        // function send an email for admin that the user has forgot his password and he needs to restore it
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

    public void getAllWorkApplications(String clickedCategory) {
        // function gets all the work applications from the data base

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

                    if(clickedCategory.equals("allWorkApplications"))
                        showAllWorkApplicationsInList(workApplicationsAsObjectArr,context);
                    else
                        showListViewItems(workApplicationsAsObjectArr,context);


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

    public void getAllUsers(){
        List<User> users = new ArrayList<>();

        dialogLoading = ProgressDialog.show(context, "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllUsers", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray allusers = new JSONArray(response);


                    for (int i = 0; i < allusers.length(); i++) {
                        JSONObject user = allusers.getJSONObject(i);
                        String[] dateOfSubAsString = user.get("birthdate").toString().split("-");
                        Date dateOfbirth = new Date(dateOfSubAsString[2], dateOfSubAsString[1], dateOfSubAsString[0]);

                        users.add(new User(user.getString("id"),user.getString("first_name"),user.getString("last_name"),user.getString("phone_number"),dateOfbirth,"",user.getString("role"),user.getString("email")));
                    }

                    showListView(users,context);

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

    public void getAllReports() {
        List<Report> allReports = new ArrayList<>();

        dialogLoading = ProgressDialog.show(context, "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=getAllReports", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray all_reports = new JSONArray(response);


                    for (int i = 0; i < all_reports.length(); i++) {
                        JSONObject report = all_reports.getJSONObject(i);
                        String[] dateOfSubAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfSub = new Date(dateOfSubAsString[2], dateOfSubAsString[1], dateOfSubAsString[0]);

                        String[] dateOfAccidentAsString = report.get("date_of_sub").toString().split("-");
                        Date dateOfAccident = new Date(dateOfAccidentAsString[2], dateOfAccidentAsString[1], dateOfAccidentAsString[0]);

                        allReports.add(new Report("","","",dateOfSub,dateOfAccident,""));
                    }

                    showListViewForReports(allReports,context);

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

    public void createParentUser() {
        Toast.makeText(context, "createUser()", Toast.LENGTH_SHORT).show();


        dialogLoading = ProgressDialog.show(context, "",
                "Signing up. Please wait...", true);

        StringRequest request = new StringRequest(Request.Method.POST, url + "?action=createParentUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


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

    public void ImageUploadToServerFunction(String cvImageData, String pdImageData){


        ByteArrayOutputStream byteArrayOutputStreamObject ;
        ByteArrayOutputStream byteArrayOutputStreamObject1 ;
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

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialogLoading = ProgressDialog.show(context,"Sending...","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                dialogLoading.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(context,"dsss" + string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                //CVThumb.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> map = new HashMap<String,String>();

                map.put(cvImageData, ConvertImage);
                map.put(pdImageData, ConvertImage1);
                map.put("uid", signUpEmployee.fields[0]);
                map.put("fname", signUpEmployee.fields[1]);
                map.put("lname", signUpEmployee.fields[2]);
                map.put("birthdate", signUpEmployee.fields[3]);
                map.put("email", signUpEmployee.fields[4]);
                map.put("phone_number", signUpEmployee.fields[5]);
                map.put("city_name", signUpEmployee.fields[6]);
                map.put("street_name", signUpEmployee.fields[6]);
                map.put("house_number", signUpEmployee.fields[6]);
                map.put("worked_as_babysitter", signUpEmployee.fields[7]);
                map.put("special_demands", signUpEmployee.fields[8]);

                String FinalData = imageProcessClass.ImageHttpRequest(url+"?action=uploadFile", map);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

}
