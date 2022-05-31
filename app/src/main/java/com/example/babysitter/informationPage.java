package com.example.babysitter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class informationPage extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.activity_information_page, container,false);

        view.findViewById(R.id.shareBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        view.findViewById(R.id.contactUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackViaEmail();
            }
        });

        return view;
    }

    public void sendFeedbackViaEmail() {

        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.setType("message/rfc822");
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{
                "jow.isaac@gmail.com"
        });
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Babysitter finder application");
        sendEmail.putExtra(Intent.EXTRA_TEXT, "Please share us your opinion about our application!");
        startActivity(sendEmail);
    }

    public void shareApp() {

        String txtShare = "Babysitter finder application";
        String bodyOfTxt = "You can download this application for free!";

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT , txtShare + "\n" + bodyOfTxt);
        startActivity(Intent.createChooser(share , "Application Share"));
    }
}