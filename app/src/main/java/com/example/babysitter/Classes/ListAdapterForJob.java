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

import com.example.babysitter.JobRequest;
import com.example.babysitter.R;

import java.util.List;

public class ListAdapterForJob extends BaseAdapter {

    List<Deals> jobsArr;
    Context context;

    public ListAdapterForJob(List<Deals> jobsArr, Context context) {
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
        View v = ly.inflate(R.layout.view_single_deal, null);

        TextView employeeName = v.findViewById(R.id.employeeName);
        TextView employeeAccepted = v.findViewById(R.id.acceptedOrNot);
        TextView completingDate = v.findViewById(R.id.dateOfCompletingDate);
        ImageView profilePhoto = v.findViewById(R.id.ivDeals);

        User current_user = null;
        for (User user : dbClass.users) {
            if(jobsArr.get(position).getEmployeeId().equals(user.getId())) {
                current_user = user;
                break;
            }
        }
        String url = "";
        for (ProfilePhoto pf :
                dbClass.profilePhoto) {
            if (current_user.getId().equals(pf.getUserId()))
                url = pf.getImageUrl();
        }
        if(!url.equals(""))
            new SetImageViewFromUrl(profilePhoto).execute(url);



        employeeName.setText(current_user.getFirstName() + " " + current_user.getLastName() + " (Deal number: " + jobsArr.get(position).getDealId() + ")");
        employeeAccepted.setText("Waiting for employee");

        completingDate.setText("");
        Button cancel = v.findViewById(R.id.reportDeal);
        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobRequest.cancelRequest(jobsArr.get(position));
            }
        });
        v.findViewById(R.id.rateTheUser).setVisibility(View.GONE);
        v.findViewById(R.id.contactInfo).setVisibility(View.GONE);


        return v;
    }


}
