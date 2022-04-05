package com.example.babysitter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class login extends Fragment {
    View view;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login,container,false);
        ((Button)view.findViewById(R.id.signUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    private void showDialog() {
        dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
        dialog.findViewById(R.id.signUpAsEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new signUpEmployee()).commit();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.signUpAsParent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,new signUpParent()).commit();
                dialog.dismiss();
            }
        });

    }

}
