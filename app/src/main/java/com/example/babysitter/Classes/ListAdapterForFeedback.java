package com.example.babysitter.Classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysitter.History;
import com.example.babysitter.R;
import com.example.babysitter.login;

import java.util.List;

public class ListAdapterForFeedback extends BaseAdapter {
    List<Feedback> feedbackList;
    Context context;
    public ListAdapterForFeedback(List<Feedback> feedbackList, Context context) {
        this.feedbackList = feedbackList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedbackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater ly = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = ly.inflate(R.layout.single_feedback, null);

        ((RatingBar)v.findViewById(R.id.singleRating)).setRating(feedbackList.get(position).getRating());
        ((TextView)v.findViewById(R.id.singleFeedback)).setText(feedbackList.get(position).getFeedback());
        //((TextView)v.findViewById(R.id.username)).setText(feedbackList.get(position).getUser().getFirstName() + " " + feedbackList.get(position).getUser().getLastName());
        ImageView profilePhoto = v.findViewById(R.id.profilePhotoSingle);
        new SetImageViewFromUrl(profilePhoto).execute(context.getString(R.string.default_profile_pic));

        return v;
    }

}