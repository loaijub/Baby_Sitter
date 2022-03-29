package com.example.babysitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class informationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
    }

    public void sendFeedbackViaEmail(View view) {

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

    public void shareApp(View view) {

        String txtShare = "Babysitter finder application";
        String bodyOfTxt = "You can download this application for free!";

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT , txtShare + "\n" + bodyOfTxt);
        startActivity(Intent.createChooser(share , "Application Share"));
    }
}