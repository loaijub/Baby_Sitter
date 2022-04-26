package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class signUpParentStepTwo extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up_parent_step_two,container,false);

        loadPreviousStepState();
        return view;
    }


    private void loadPreviousStepState() {

            ((EditText)view.findViewById(R.id.cityParent)).setText(signUpParent.fields[5]);
            ((EditText)view.findViewById(R.id.streetNameParent)).setText(signUpParent.fields[6]);
            ((EditText)view.findViewById(R.id.houseNumberParent)).setText(signUpParent.fields[7]);
            ((EditText)view.findViewById(R.id.amountOfKids)).setText(signUpParent.fields[8]);
            ((EditText)view.findViewById(R.id.agesOfChildren)).setText(signUpParent.fields[9]);
            ((EditText)view.findViewById(R.id.specialDemandsParent)).setText(signUpParent.fields[10]);

    }

}
