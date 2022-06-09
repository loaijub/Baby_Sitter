package com.example.babysitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysitter.Classes.Employee;
import com.example.babysitter.Classes.Parent;
import com.example.babysitter.Classes.SetImageViewFromUrl;
import com.example.babysitter.Classes.User;
import com.example.babysitter.Classes.dbClass;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    View view;
    public static User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);


        // we get all the ids of the textView from the xml so we can fill them with the info from the database.
        TextView id = view.findViewById(R.id.userId);
        TextView firstName = view.findViewById(R.id.userFirstName);
        TextView lastName = view.findViewById(R.id.userLastName);
        TextView phoneNumber = view.findViewById(R.id.userPhoneNumber);
        TextView birthdate = view.findViewById(R.id.userBirthdate);
        TextView email = view.findViewById(R.id.userEmail);
        TextView role = view.findViewById(R.id.userRole);
        TextView city = view.findViewById(R.id.userCity);
        TextView street = view.findViewById(R.id.userStreet);
        TextView houseNumber = view.findViewById(R.id.userHouseNumber);
        TextView numberOfChildren = view.findViewById(R.id.userNumberOfChildren);
        TextView specialDemands = view.findViewById(R.id.userSpecialDemands);
        TextView rate = view.findViewById(R.id.userRate);
        TextView experience = view.findViewById(R.id.userExperience);
        TextView workingHoursInMonth = view.findViewById(R.id.userWorkingHoursInMonth);

        Button btnChangePassword = view.findViewById(R.id.changePassword);

        ImageView profilePicture = view.findViewById(R.id.userProfilePicture);
        new SetImageViewFromUrl(profilePicture).execute(this.currentUser.getProfilePhoto().getImageUrl());


        // we fill the textView with the current user information
        id.setText(login.dbClass.getCurrentUser().getId());
        firstName.setText(login.dbClass.getCurrentUser().getFirstName());
        lastName.setText(login.dbClass.getCurrentUser().getLastName());
        phoneNumber.setText(login.dbClass.getCurrentUser().getPhoneNumber());
        birthdate.setText(login.dbClass.getCurrentUser().getBirthDate().toString());
        email.setText(login.dbClass.getCurrentUser().getEmail());
        role.setText(login.dbClass.getCurrentUser().getRole());

        // we do down casting
        if (login.dbClass.getCurrentUser().getRole().equals("1")) {
            city.setText(((Employee) currentUser).getAddress().getCity());
            street.setText(((Employee) currentUser).getAddress().getStreet());
            houseNumber.setText(((Employee) currentUser).getAddress().getHouse_number());
            specialDemands.setText(((Employee) currentUser).getSpecialDemands());
            rate.setText(((Employee) currentUser).getRate());
            experience.setText(((Employee) currentUser).getExperience());
            workingHoursInMonth.setText(((Employee) currentUser).getWorkingHoursInMonth());
        } else {
            city.setText(((Parent) currentUser).getAddress().getCity());
            street.setText(((Parent) currentUser).getAddress().getStreet());
            houseNumber.setText(((Parent) currentUser).getAddress().getHouse_number());
            specialDemands.setText(((Parent) currentUser).getSpecialDemands());
            numberOfChildren.setText(((Parent) currentUser).getNumberOfChildren());
            rate.setText(((Parent) currentUser).getRate());
        }


        return view;

    }

}