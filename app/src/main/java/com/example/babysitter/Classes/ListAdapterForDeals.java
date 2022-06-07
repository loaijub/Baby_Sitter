package com.example.babysitter.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.babysitter.R;

import java.util.List;

public class ListAdapterForDeals extends BaseAdapter {
    List<Deals> dealsArr;
    Context context;

    public ListAdapterForDeals(List<Deals> dealsArr, Context context) {
        this.dealsArr = dealsArr;
        this.context = context;
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

        TextView applicationInfo = v.findViewById(R.id.singleDeal);
        applicationInfo.setText(dealsArr.get(position).toString());
        return v;
    }
}