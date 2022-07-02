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
        TextView userName = view.findViewById(R.id.userName);
        TextView phoneNumber = view.findViewById(R.id.userPhoneNumber);
        TextView birthdate = view.findViewById(R.id.userBirthdate);
        TextView email = view.findViewById(R.id.userEmail);
        TextView role = view.findViewById(R.id.userRole);
        TextView userAddress = view.findViewById(R.id.userAddress);

        TextView rate = view.findViewById(R.id.userRate);
        //TextView workingHoursInMonth = view.findViewById(R.id.userWorkingHoursInMonth);
        //btnChangePassword = view.findViewById(R.id.changePassword);
        //btnChangeDetails = view.findViewById(R.id.changeMyDetails);


        ImageView profilePicture = view.findViewById(R.id.userProfilePicture);
        new SetImageViewFromUrl(profilePicture).execute(this.currentUser.getProfilePhoto().getImageUrl());


        // we fill the textView with the current user information
        id.setText("Id: " + login.dbClass.getCurrentUser().getId());
        userName.setText(login.dbClass.getCurrentUser().getFirstName() + " " + login.dbClass.getCurrentUser().getLastName());
        phoneNumber.setText(login.dbClass.getCurrentUser().getPhoneNumber());
        birthdate.setText(login.dbClass.getCurrentUser().getBirthDate().toString());
        email.setText(login.dbClass.getCurrentUser().getEmail());
        role.setText(login.dbClass.getCurrentUser().getRole() == "1" ? "Employee" : "Parent");

        // we do down casting
        // current user is an Employee
        if (login.dbClass.getCurrentUser().getRole().equals("1")) {
            userAddress.setText("City: " + ((Employee) currentUser).getAddress().getCity() + "\nStreet: "
                    + ((Employee) currentUser).getAddress().getStreet() + "\nHouse number: "
                    + ((Employee) currentUser).getAddress().getHouse_number());

            //specialDemands.setText("Special demands: " + ((Employee) currentUser).getSpecialDemands());
            rate.setText(((Employee) currentUser).getRate());
            //experience.setText("Experience: " + ((Employee) currentUser).getExperience());
            //workingHoursInMonth.setText("Working hours in a month: " + ((Employee) currentUser).getWorkingHoursInMonth());

        }
        // current user is a parent
        else {
            userAddress.setText("City: " + ((Parent) currentUser).getAddress().getCity() + "\nStreet: "
                    + ((Parent) currentUser).getAddress().getStreet() + "\nHouse number: "
                    + ((Parent) currentUser).getAddress().getHouse_number());
            //specialDemands.setText("Special demands: " + ((Parent) currentUser).getSpecialDemands());
            //numberOfChildren.setText("Number of children: " + ((Parent) currentUser).getNumberOfChildren());
            rate.setText(((Parent) currentUser).getRate());

            // we don't put the Employee attributes on screen, because the current user is a Parent.
            //workingHoursInMonth.setText("");
            //experience.setText("");
        }


        /*
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
        */


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