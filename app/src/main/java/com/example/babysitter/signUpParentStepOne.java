package com.example.babysitter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class signUpParentStepOne extends Fragment {
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sign_up_parent_step_one,container,false);

        loadPreviousStepState();

        return view;
    }


    private void loadPreviousStepState() {
        ((EditText)view.findViewById(R.id.idOfParent)).setText(signUpParent.fields[0]);
        ((EditText)view.findViewById(R.id.firstNameParent)).setText(signUpParent.fields[1]);
        ((EditText)view.findViewById(R.id.lastNameParent)).setText(signUpParent.fields[2]);
        ((EditText)view.findViewById(R.id.phoneNumberParent)).setText(signUpParent.fields[3]);
        ((EditText)view.findViewById(R.id.emailParent)).setText(signUpParent.fields[4]);

    }

}
