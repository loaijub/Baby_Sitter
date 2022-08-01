package com.example.babysitter.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysitter.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterForJobEmployee extends BaseAdapter {
    List<Deals> jobsArr;
    Context context;

    public ListAdapterForJobEmployee(List<Deals> jobsArr, Context context) {
        this.jobsArr = jobsArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jobsArr.size();
    }

    @Override
    public Object getItem(int position) {
        return jobsArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = ly.inflate(R.layout.employee_homepage_adapter, null);

        ImageView profileImage = v.findViewById(R.id.parentImage);
        TextView name = v.findViewById(R.id.parentName);
        TextView acceptDeal = v.findViewById(R.id.acceptDeal);

        User currentUser = null;
        if(dbClass.users != null) {
            for (User user : dbClass.users) {
                if (user.getId().equals(jobsArr.get(position).getParentId())) {
                    currentUser = user;
                    break;
                }
            }
        }
        if(currentUser != null) {
            name.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            for(ProfilePhoto pf : dbClass.profilePhoto)
                if(jobsArr.get(position).getParentId().equals(pf.getUserId()))
                    currentUser.setProfilePhoto(pf);
            new SetImageViewFromUrl(profileImage).execute(currentUser.getProfilePhoto().getImageUrl());
        }
        return v;
    }
}
