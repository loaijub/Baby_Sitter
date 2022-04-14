package com.example.babysitter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class signUpParent extends Fragment {
    View view;
    int current_step = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_up_parent,container,false);

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

        switch(step_number){
            case 1:
                current_step = 1;
                ((Button)this.view.findViewById(R.id.next)).setText("Next");
                (this.view.findViewById(R.id.next)).setTag(null);
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutParent,new signUpParentStepOne()).addToBackStack(null).commit();

                break;
            case 2:
                current_step = 2;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutParent,new signUpParentStepTwo()).addToBackStack(null).commit();

                ((Button)this.view.findViewById(R.id.next)).setText("Finish");
                (this.view.findViewById(R.id.next)).setTag("Finish");



        }
    }

}
