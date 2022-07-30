package com.example.babysitter.Classes;

public class Employee extends User {

    private String status; // 0- active employee, 1- inactive employee.
    private String rate; // the rate for the employee from other parents.
    private String specialDemands; // the special demands regarding work.
    private String workingHoursInMonth; // the total working hours of employee in a month
    private String experience; // the experience of the employee with working with kids.


    public Address getAddress() {
        return address;
    }



    public void setAddress(Address address) {
        this.address = address;
    }

    // constructor
    public Employee(String id, String fName, String lName, String phoneNumber, Date birthDate, String password, String email, String role, String status, String rate, String specialDemands, String workingHoursInMonth, String experience, Address address) {
        super(id, fName, lName, phoneNumber, birthDate, password, role, email);
        this.status = status;
        this.rate = rate;
        this.specialDemands = specialDemands;
        this.workingHoursInMonth = workingHoursInMonth;
        this.experience = experience;
        this.address = address;
    }

    //getters and setters
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

    public String getWorkingHoursInMonth() {
        return workingHoursInMonth;
    }

    public void setWorkingHoursInMonth(String workingHoursInMonth) {
        this.workingHoursInMonth = workingHoursInMonth;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    // toString
    @Override
    public String toString() {
        return "Employee{" + super.toString() +
                "status='" + status + '\'' +
                ", rate='" + rate + '\'' +
                ", specialDemands='" + specialDemands + '\'' +
                ", workingHoursInMonth=" + workingHoursInMonth +
                ", experience='" + experience + '\'' +
                '}';
    }

}
