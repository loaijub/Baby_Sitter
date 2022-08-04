package com.example.babysitter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.babysitter.Classes.Employee;
import com.example.babysitter.Classes.Parent;
import com.example.babysitter.Classes.SetImageViewFromUrl;
import com.example.babysitter.Classes.User;
import com.example.babysitter.Classes.dbClass;
import com.google.android.gms.maps.SupportMapFragment;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    View view;
    public static User currentUser;
    Button btnChangePassword;
    Button btnLogout;
    LinearLayout btnEditAddress, btnEditPhone, btnEditEmail;
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
        btnChangePassword = view.findViewById(R.id.changePassword);
        btnEditAddress = view.findViewById(R.id.btnAddressEdit);
        btnEditPhone = view.findViewById(R.id.btnPhoneEdit);
        btnEditEmail = view.findViewById(R.id.btnEmailEdit);

        btnLogout = view.findViewById(R.id.logoutBtn);


        ImageView profilePicture = view.findViewById(R.id.userProfilePicture);
        if(this.currentUser.getProfilePhoto() != null)
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
            userAddress.setText(((Employee) currentUser).getAddress().toString());

            //specialDemands.setText("Special demands: " + ((Employee) currentUser).getSpecialDemands());
            rate.setText(((Employee) currentUser).getRate());
            //experience.setText("Experience: " + ((Employee) currentUser).getExperience());
            //workingHoursInMonth.setText("Working hours in a month: " + ((Employee) currentUser).getWorkingHoursInMonth());

        }
        // current user is a parent
        else {
            userAddress.setText(((Parent) currentUser).getAddress().toString());
            //specialDemands.setText("Special demands: " + ((Parent) currentUser).getSpecialDemands());
            //numberOfChildren.setText("Number of children: " + ((Parent) currentUser).getNumberOfChildren());
            rate.setText(((Parent) currentUser).getRate());

            // we don't put the Employee attributes on screen, because the current user is a Parent.
            //workingHoursInMonth.setText("");
            //experience.setText("");
        }


        // if user clicked on "change my password" button
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangePassword();
            }
        });

        // if user clicked on edit address button
        btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangeAddress();
            }
        });
        // if user clicked on edit email button
        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangeEmail();
            }
        });
        // if user clicked on edit phone button
        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupToChangePhone();
            }
        });

        // if user clicked on the logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFromProfile();
            }
        });


        return view;

    }

    private void showPopupToChangePhone() {
        // function show for user the popup to change his phone number
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_phone_dialog);
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
                closeAndChangePhone();
            }
        });
    }

    private void closeAndChangePhone() {
        // function gets the new password to change, and saves it encrypted in the database.

        // the EditText in the dialog
        EditText newPhoneNum = dialog.findViewById(R.id.newPhoneNumber);
        if (newPhoneNum.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!login.dbClass.getCurrentUser().getPhoneNumber().equals(newPhoneNum.getText().toString()))
            // this function changes the phone in the database, and informs the user if it was successful or if it failed to save the new password
            login.dbClass.changePhoneOfCurrentUser(newPhoneNum.getText().toString(), dialog, view.findViewById(R.id.userPhoneNumber));
        else
            dialog.dismiss();

    }

    private void showPopupToChangeEmail() {
        // function show for user the popup to change his phone number
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_email_dialog);
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
                closeAndChangeEmail();
            }
        });
    }

    private void closeAndChangeEmail() {
        EditText newEmail = dialog.findViewById(R.id.newEmail);
        if (newEmail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!login.dbClass.getCurrentUser().getEmail().equals(newEmail.getText().toString()))
            // this function changes the email in the database, and informs the user if it was successful or if it failed to save the new password
            login.dbClass.changeEmailOfCurrentUser(newEmail.getText().toString(), dialog, view.findViewById(R.id.userEmail));
        else
            dialog.dismiss();
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
                closeAndChangePass();
            }
        });


    }

    public void closeAndDoNotSave() {
        // function closes the dialog and do not save the changes
        Toast.makeText(getContext(), "Changes are NOT saved!", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public void closeAndChangePass() {
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
    public void showPopupToChangeAddress() {
        // function show for user the popup to change his password
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_address_dialog);
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
                closeAndChangeAddress();
            }
        });
    }

    private void closeAndChangeAddress() {
        String newAddressCity = ((EditText)dialog.findViewById(R.id.newAddressCity)).getText().toString();
        String newAddressStreet = ((EditText)dialog.findViewById(R.id.newAddressStreet)).getText().toString();
        String newAddressHouse = ((EditText)dialog.findViewById(R.id.newAddressHouse)).getText().toString();
        if (newAddressCity.equals("") || newAddressStreet.equals("") || newAddressHouse.equals(""))
        {
            Toast.makeText(getContext(), "Please enter address", Toast.LENGTH_SHORT).show();
            return;
        }
        login.dbClass.changeAddressOfCurrentUser(new String[]{newAddressCity,newAddressStreet,newAddressHouse}, dialog, view.findViewById(R.id.userAddress));

    }


    // function logs out from the current user account
    public void logoutFromProfile() {
        ////////////////////////***************************////////////////////
        getContext().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
        login.dbClass.setCurrentUser(null);

    }


}