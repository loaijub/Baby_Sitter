package com.example.babysitter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class signUpParent extends Fragment {
    View view;
    int current_step = 1;
    public static String[] fields = new String[11];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up_parent,container,false);
        for (int i=0; i<fields.length; i++)
            fields[i] = "";


      // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutParent,new signUpParentStepOne()).commit();

        getActivity().getSupportFragmentManager().beginTransaction()
        .replace(R.id.frameLayoutParent, new signUpParentStepOne())
        .commit();

        //making onclick listener for all steps textview in the layout
        for(int i=1;i<3; i++)
            view.findViewById(getResources().getIdentifier("step" + i, "id", getActivity().getPackageName())).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStep(v);
                }
            });

        //making onclick listener for next button\
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStepParentSignUp(v);
            }
        });

        return view;
    }


    public void nextStepParentSignUp(View v) {
        if(v.getTag() != null && v.getTag().equals("Finish"))
            return;

        int stepToGo;
        if(current_step == 2)
            stepToGo = 1;
        else
            stepToGo = current_step + 1;

        showStep(this.view.findViewById(getResources().getIdentifier("step" + stepToGo, "id", getActivity().getPackageName())));

        /*Intent i = new Intent(getContext(), mainGoogleMap.class);
        startActivity(i);*/

    }

    public void showStep(View view){

        String s =  getResources().getResourceName(view.getId());
        int step_number = Integer.parseInt(s.substring(s.length()-1));

        //making all the steps default font
        ((TextView)this.view.findViewById(R.id.step1)).setTypeface(Typeface.DEFAULT);
        ((TextView)this.view.findViewById(R.id.step2)).setTypeface(Typeface.DEFAULT);


        //making the chosen step bold
        TextView stepToGo = this.view.findViewById(view.getId());
        stepToGo.setTypeface(Typeface.DEFAULT_BOLD);

        //saving the state of edit texts
        saveStep(current_step);

        switch(step_number){
            case 1:
                current_step = 1;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                (this.view.findViewById(R.id.next)).setTag(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutParent,new signUpParentStepOne()).commit();

                break;
            case 2:
                current_step = 2;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutParent,new signUpParentStepTwo()).commit();
                ((Button)this.view.findViewById(R.id.next)).setText("Finish");
                (this.view.findViewById(R.id.next)).setTag("Finish");

        }

    }




    private void saveStep(int stepNumberToSave){
        if(stepNumberToSave == 1)
        {
            fields[0] = ((EditText)view.findViewById(R.id.idOfParent)).getText().toString();
            fields[1] = ((EditText)view.findViewById(R.id.firstNameParent)).getText().toString();
            fields[2] = ((EditText)view.findViewById(R.id.lastNameParent)).getText().toString();
            fields[3] = ((EditText)view.findViewById(R.id.phoneNumberParent)).getText().toString();
            fields[4] = ((EditText)view.findViewById(R.id.emailParent)).getText().toString();
        }
        if (stepNumberToSave == 2)
        {
            fields[5] = ((EditText)view.findViewById(R.id.cityParent)).getText().toString();
            fields[6] = ((EditText)view.findViewById(R.id.streetNameParent)).getText().toString();
            fields[7] = ((EditText)view.findViewById(R.id.houseNumberParent)).getText().toString();
            fields[8] = ((EditText)view.findViewById(R.id.amountOfKids)).getText().toString();
            fields[9] = ((EditText)view.findViewById(R.id.agesOfChildren)).getText().toString();
            fields[10] = ((EditText)view.findViewById(R.id.specialDemandsParent)).getText().toString();

        }

    }


}
