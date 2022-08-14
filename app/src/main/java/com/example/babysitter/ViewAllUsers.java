package com.example.babysitter;

import android.app.AlertDialog;
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
import com.example.babysitter.Classes.User;
import com.example.babysitter.Classes.dbClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllUsers extends Fragment {

    View view;
    static ListView listView;
    ProgressDialog dialogLoading;
    public static FragmentActivity fg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.admin_show_all_users, container,false);
        listView = view.findViewById(R.id.listview_show_users);
        login.dbClass.getAllUsers(true);
        fg = getActivity();
        return view;
    }


    public static void showListView(List<User> users, Context context){
        List<User> activeUsers = new ArrayList<>();
        for(User user : users){
            if(user.getStatus().equals("0"))
                activeUsers.add(user);
        }
        //showing the users in the list
        MyAdapter myAdapter = new MyAdapter(activeUsers,context);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        login.dbClass.deleteUser(usersList.get(position).getId(), ViewAllUsers.fg);

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

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