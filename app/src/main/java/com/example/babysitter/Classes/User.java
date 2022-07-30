package com.example.babysitter.Classes;

public class User {

    // attributes
    protected final String id;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected final Date birthDate;
    protected String password;
    protected String role;
    protected String email;
    private ProfilePhoto profilePhoto;
    protected Address address;

    // constructor
    public User(String id, String fName, String lName, String phoneNumber, Date birthDate, String password, String role, String email) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.role = role;
        this.email = email;
    }
    public void setProfilePhoto(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public User(User other) {
        this.id = other.id;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.phoneNumber = other.phoneNumber;
        this.birthDate = other.birthDate;
        this.password = other.password;
        this.role = other.role;
        this.email = other.email;
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString
    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    // methods
}
