package com.example.babysitter;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.babysitter.Classes.Date;
import com.example.babysitter.Classes.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllUsers extends Fragment {

    View view;
    ProgressDialog dialogLoading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.admin_show_all_users, container,false);

        getAllUsers();

        return view;
    }

    public void getAllUsers(){
        List<User> users = new ArrayList<>();

        dialogLoading = ProgressDialog.show(getContext(), "",
                "Loading... Please wait", true);

        StringRequest request = new StringRequest(Request.Method.POST, login.url + "?action=getAllUsers", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                dialogLoading.dismiss();
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray allusers = new JSONArray(response);


                    for (int i = 0; i < allusers.length(); i++) {
                        JSONObject user = allusers.getJSONObject(i);
                        String[] dateOfSubAsString = user.get("birthdate").toString().split("-");
                        Date dateOfbirth = new Date(dateOfSubAsString[0], dateOfSubAsString[1], dateOfSubAsString[2]);

                        users.add(new User(user.getString("id"),user.getString("first_name"),user.getString("last_name"),user.getString("phone_number"),dateOfbirth,"",user.getString("role"),user.getString("email")));
                    }

                    showListView(users);

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
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }
    public void showListView(List<User> users){
        //showing the users in the list
        ListView listView = view.findViewById(R.id.listview_show_users);


        MyAdapter myAdapter = new MyAdapter(users,getContext());
        listView.setAdapter(myAdapter);
    }
}



class MyAdapter extends BaseAdapter{
    List<User> usersList;
    Context context;

    public MyAdapter(List<User> usersList,Context context){
        this.usersList = usersList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = ly.inflate(R.layout.list_item,null);

        ((TextView)v.findViewById(R.id.personName)).setText(usersList.get(position).getFirstName() + " " + usersList.get(position).getLastName());
        String role = "";
        switch(usersList.get(position).getRole()){
            case "1":
                role = "Employee";
                break;
            case "2":
                role = "Parent";
                break;
            case "3":
                role = "Parent and Employee";
        }


        ((TextView)v.findViewById(R.id.role)).setText(role);

        ((TextView)v.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
            }
        });
        ((TextView)v.findViewById(R.id.edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}