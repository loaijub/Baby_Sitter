package com.example.babysitter.Classes;

public class Parent extends User {

    private String rate; // the rate that other employees give to parent.
    private String specialDemands; // the special demands of the parent regarding their children and working with them.
    private String numberOfChildren; // the number of children that will take part in the system.

    public Parent(String id, String fName, String lName, String phoneNumber, Date birthDate, String password, String email, String role, String status, String rate, String specialDemands, String numberOfChildren, Address address) {
        super(id, fName, lName, phoneNumber, birthDate, password, role, email,status);
        this.rate = rate;
        this.specialDemands = specialDemands;
        this.numberOfChildren = numberOfChildren;
        this.address = address;
    }

    // getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSpecialDemands() {
        return specialDemands;
    }

    public void setSpecialDemands(String specialDemands) {
        this.specialDemands = specialDemands;
    }

    public String getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(String numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // toString
    @Override
    public String toString() {
        return "Parent{" + super.toString() +
                "status='" + status + '\'' +
                ", rate='" + rate + '\'' +
                ", specialDemands='" + specialDemands + '\'' +
                ", numberOfChildren=" + numberOfChildren +
                '}';
    }
}
