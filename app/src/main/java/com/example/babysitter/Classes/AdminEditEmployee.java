package com.example.babysitter.Classes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysitter.R;
import com.example.babysitter.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AdminEditEmployee extends Fragment {

    View view;
    User user;
    final Calendar myCalendar= Calendar.getInstance();
    EditText fname, lname, email, phoneNum, password;
    ImageView profileImage;

    public AdminEditEmployee(User user) {
        this.user = user;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_edit_employee, container, false);

        fname = view.findViewById(R.id.employeeFirstNameAdminPanel);
        lname = view.findViewById(R.id.employeeLastNameAdminPanel);
        email = view.findViewById(R.id.employeeEmailAdminPanel);
        phoneNum = view.findViewById(R.id.employeePhoneNumberAdminPanel);
        password = view.findViewById(R.id.employeePasswordAdminPanel);
        profileImage = view.findViewById(R.id.AdminPanelEmployeeProfileIcon);

        for (ProfilePhoto pf : dbClass.profilePhoto) {
            if(user.getId().equals(pf.getUserId())) {
                new SetImageViewFromUrl(profileImage).execute(pf.getImageUrl());
                break;
            }
        }
        fname.setText(user.getFirstName());
        lname.setText(user.getLastName());
        email.setText(user.getEmail());
        phoneNum.setText(user.getPhoneNumber());


        view.findViewById(R.id.adminPanelSaveChangesEmployeeEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateUser())
                    login.dbClass.editUser(user);
            }
        });

        return view;
    }

    private boolean updateUser() {
        if(fname.getText().toString().equals("") || lname.getText().toString().equals("")
                 || email.getText().toString().equals("") || phoneNum.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Error! please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        user.setFirstName(fname.getText().toString());
        user.setLastName(lname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPhoneNumber(phoneNum.getText().toString());
        if(!password.getText().toString().equals(""))
            user.setPassword(password.getText().toString());
        return true;
    }


}