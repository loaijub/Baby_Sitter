package com.example.babysitter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class signUpEmployee extends Fragment {
    View view;
    int current_step = 1;
    public static String[] fields;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up_employee,container,false);



        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStepEmployeeSignUp(v);
            }
        });

        for (int i=1; i<=3;i++) {
            TextView steps = view.findViewById(getResources().getIdentifier("step" + i, "id", getActivity().getPackageName()));
            steps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStep(v);
                }
            });
        }


        return view;
    }

    public void nextStepEmployeeSignUp(View v) {
        if(v.getTag() != null && v.getTag().equals("Finish"))
            return;
        int stepToGo;
        if(current_step == 3)
            stepToGo = 1;
        else
            stepToGo = current_step + 1;

        showStep(this.view.findViewById(getResources().getIdentifier("step" + stepToGo, "id", getActivity().getPackageName())));

        /*Intent i = new Intent(getContext(), mainGoogleMap.class);
        startActivity(i);*/

    }


    //the function change step according to view clicked (step 1 or 2 or 3)
    public void showStep(View view){

        String s =  getResources().getResourceName(view.getId());
        int step_number = Integer.parseInt(s.substring(s.length()-1));

        //making all the steps default font
        TextView step;
        for (int i=1; i<=3;i++) {
            step = this.view.findViewById(getResources().getIdentifier("step" + i, "id", getActivity().getPackageName()));
            step.setTypeface(Typeface.DEFAULT);
        }

        //making the chosen step bold
        TextView stepToGo = this.view.findViewById(view.getId());
        stepToGo.setTypeface(Typeface.DEFAULT_BOLD);

        //saving the state of edit texts
        saveStep(current_step);

        switch(step_number){
            case 1:
                current_step = 1;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                this.view.findViewById(R.id.next).setTag(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep1()).commit();
                break;
            case 2:
                current_step = 2;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                this.view.findViewById(R.id.next).setTag(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep2()).commit();
                break;
            case 3:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new signupEmployeeStep3()).commit();
                ((Button)this.view.findViewById(R.id.next)).setText("Finish");
                this.view.findViewById(R.id.next).setTag("Finish");
                current_step = 3;

        }
    }

    private void saveStep(int stepNumberToSave){
        if(stepNumberToSave == 1)
            signupEmployeeStep1.getInputFromEmployee();

        if (stepNumberToSave == 2)
            signupEmployeeStep2.getInputFromEmployee();

    }

}
