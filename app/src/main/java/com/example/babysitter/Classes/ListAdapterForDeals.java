package com.example.babysitter.Classes;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysitter.History;
import com.example.babysitter.R;
import com.example.babysitter.login;

import java.util.List;

public class ListAdapterForDeals extends BaseAdapter {
    List<Deals> dealsArr;
    Context context;
    Button reportBtn;
    String historyOrRequests;

    public ListAdapterForDeals(List<Deals> dealsArr, Context context,String historyOrRequests) {
        this.dealsArr = dealsArr;
        this.context = context;
        this.historyOrRequests = historyOrRequests;
    }

    @Override
    public int getCount() {
        return dealsArr.size();
    }

    @Override
    public Object getItem(int position) {
        return dealsArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = ly.inflate(R.layout.view_single_deal, null);

        User current_user = null;
        for (User user : dbClass.users) {
            if(historyOrRequests.equals("history")) {
                if (dealsArr.get(position).getEmployeeId().equals(user.getId())) {
                    current_user = user;
                    break;
                }
            }else{
                if (dealsArr.get(position).getParentId().equals(user.getId())) {
                    current_user = user;
                    break;
                }
            }
        }

        TextView userName = v.findViewById(R.id.employeeName);
        TextView userAccepted = v.findViewById(R.id.acceptedOrNot);
        TextView completingDate = v.findViewById(R.id.dateOfCompletingDate);
        ImageView profilePhoto = v.findViewById(R.id.ivDeals);
        String url = "";
        for (ProfilePhoto pf :
                dbClass.profilePhoto) {
            if (current_user.getId().equals(pf.getUserId()))
                url = pf.getImageUrl();
        }
        if(!url.equals(""))
            new SetImageViewFromUrl(profilePhoto).execute(url);

        userName.setText(current_user.getFirstName() + " " + current_user.getLastName() + " (Deal number: " + dealsArr.get(position).getDealId() + ")");
        if(dealsArr.get(position).getEmployeeAccepted().equals("1"))
            userAccepted.setText("Accepted");
        else if(dealsArr.get(position).isHasDone().equals("1"))
            userAccepted.setText("Declined");

        completingDate.setText(dealsArr.get(position).getCompletedDealDate().toString());

        Button report = v.findViewById(R.id.reportDeal);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                History.showReport(dealsArr.get(position));
            }
        });

        return v;
    }

}