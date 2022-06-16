package com.example.babysitter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysitter.Classes.Employee;
import com.example.babysitter.Classes.Parent;
import com.example.babysitter.Classes.SetImageViewFromUrl;
import com.example.babysitter.Classes.User;
import com.example.babysitter.Classes.dbClass;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    View view;
    public static User currentUser;
    Button btnChangePassword;
    Button btnChangeDetails;
    Dialog dialog;

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
        btnChangePassword = view.findViewById(R.id.changePassword);
        btnChangeDetails = view.findViewById(R.id.changeMyDetails);


        ImageView profilePicture = view.findViewById(R.id.userProfilePicture);
        new SetImageViewFromUrl(profilePicture).execute(this.currentUser.getProfilePhoto().getImageUrl());


        // we fill the textView with the current user information
        id.setText("Id: " + login.dbClass.getCurrentUser().getId());
        firstName.setText("First name: " + login.dbClass.getCurrentUser().getFirstName());
        lastName.setText("Last name: " + login.dbClass.getCurrentUser().getLastName());
        phoneNumber.setText("Phone number: " + login.dbClass.getCurrentUser().getPhoneNumber());
        birthdate.setText("Birthdate: " + login.dbClass.getCurrentUser().getBirthDate().toString());
        email.setText("Email: " + login.dbClass.getCurrentUser().getEmail());
        role.setText("You signed up as: " + login.dbClass.getCurrentUser().getRole() == "1" ? "Employee" : "Parent");

        // we do down casting
        // current user is an Employee
        if (login.dbClass.getCurrentUser().getRole().equals("1")) {
            city.setText("City: " + ((Employee) currentUser).getAddress().getCity());
            street.setText("Street: " + ((Employee) currentUser).getAddress().getStreet());
            houseNumber.setText("House number: " + ((Employee) currentUser).getAddress().getHouse_number());
            specialDemands.setText("Special demands: " + ((Employee) currentUser).getSpecialDemands());
            rate.setText("Rate: " + ((Employee) currentUser).getRate());
            experience.setText("Experience: " + ((Employee) currentUser).getExperience());
            workingHoursInMonth.setText("Working hours in a month: " + ((Employee) currentUser).getWorkingHoursInMonth());

            // we don't put the parent attributes on screen, because the current user is an Employee.
            numberOfChildren.setText("");


        }
        // current user is a parent
        else {
            city.setText("City: " + ((Parent) currentUser).getAddress().getCity());
            street.setText("Street: " + ((Parent) currentUser).getAddress().getStreet());
            houseNumber.setText("House number: " + ((Parent) currentUser).getAddress().getHouse_number());
            specialDemands.setText("Special demands: " + ((Parent) currentUser).getSpecialDemands());
            numberOfChildren.setText("Number of children: " + ((Parent) currentUser).getNumberOfChildren());
            rate.setText("Rate: " + ((Parent) currentUser).getRate());

            // we don't put the Employee attributes on screen, because the current user is a Parent.
            workingHoursInMonth.setText("");
            experience.setText("");
        }

        // if user clicked on "change my password" button
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangePassword();
            }
        });

        // if user clicked on "change my details" button
        btnChangeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangeDetails();
            }
        });


        return view;

    }


    public void showPopupToChangePassword() {
        // function show for user the popup to change his password
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.show();

        // the buttons in the dialog
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        Button saveChangesBtn = dialog.findViewById(R.id.saveBtn);


        // setting listeners on each button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAndDoNotSave();
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAndSave();
            }
        });


    }

    public void closeAndDoNotSave() {
        // function closes the dialog and do not save the changes
        Toast.makeText(getContext(), "Changes are NOT saved!", Toast.LENGTH_SHORT).show();


        dialog.dismiss();
    }

    public void closeAndSave() {
        // function gets the new password to change, and saves it encrypted in the database.

        boolean flagOfDialog = true; // flag to know if the operation failed and to close dialog, or it succeeded

        // the EditText in the dialog
        EditText currentPassword = dialog.findViewById(R.id.currentPassword);
        EditText newPassword = dialog.findViewById(R.id.newPassword);
        EditText confirmNewPassword = dialog.findViewById(R.id.confirmNewPassword);

        // getting what the user typed
        String currentPasswordFromUser = currentPassword.getText().toString();
        String newPasswordFromUser = newPassword.getText().toString();
        String confirmNewPasswordFromUser = confirmNewPassword.getText().toString();

        String currentUserPass = currentUser.getPassword().substring(0, 2) + 'a' + currentUser.getPassword().substring(3);
        System.out.println("loaii" + currentUserPass);
        // first we check if the user typed his old password correctly
        if (!BCrypt.checkpw(currentPasswordFromUser, currentUserPass)) {
            Toast.makeText(getContext(), "The password you wrote is not correct!", Toast.LENGTH_SHORT).show();
            flagOfDialog = false;
        }

        // second we check if the new password matches the new password in the confirmation section
        if (!newPasswordFromUser.equals(confirmNewPasswordFromUser)) {
            Toast.makeText(getContext(), "The confirmation of the new password is not correct!", Toast.LENGTH_SHORT).show();
            flagOfDialog = false;
        }

        // if the information is not correct, we close and dialog and do NOT save changes
        if (!flagOfDialog) {
            Toast.makeText(getContext(), "The passwords were not correct, please try again!", Toast.LENGTH_SHORT).show();
            closeAndDoNotSave();
        } else
            // this function changes the password in the database, and informs the user if it was successful or if it failed to save the new password
            login.dbClass.changePasswordOfCurrentUser(newPasswordFromUser, dialog);


    }


    /**
     * Function shows a popup with the user current details as EditText, and user can change his own details.
     */
    public void showPopupToChangeDetails() {

    }


}