package com.example.babysitter.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        View v = ly.inflate(R.layout.view_single_job, null);

        TextView applicationInfo = v.findViewById(R.id.singleDeal);
        applicationInfo.setText(jobsArr.get(position).toString());
        return v;
    }


}
