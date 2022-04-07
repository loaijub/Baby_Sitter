package com.example.babysitter.Classes;

public class WorkApplication {

    private final String employeeId; // the id of the workApplication applicant.
    private String employeeFirstName; // the first name of the applicant.
    private String employeeLastName; // the last name of the applicant.
    private String employeePhoneNumber; // the phone number of the applicant.
    private final Date employeeBirthDate; // the birthdate of the applicant.
    private String employeeEmail; // the email of the applicant.
    private String employeeExperience; // the experience of the applicant.
    private String employeeSpecialDemands; // the special demands of the applicant.

    public WorkApplication(String employeeId, String employeeFirstName, String employeeLastName, String employeePhoneNumber, Date employeeBirthDate, String employeeEmail, String employeeExperience, String employeeSpecialDemands) {
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeBirthDate = employeeBirthDate;
        this.employeeEmail = employeeEmail;
        this.employeeExperience = employeeExperience;
        this.employeeSpecialDemands = employeeSpecialDemands;
    }

    // getters and setters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public Date getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeExperience() {
        return employeeExperience;
    }

    public void setEmployeeExperience(String employeeExperience) {
        this.employeeExperience = employeeExperience;
    }

    public String getEmployeeSpecialDemands() {
        return employeeSpecialDemands;
    }

    public void setEmployeeSpecialDemands(String employeeSpecialDemands) {
        this.employeeSpecialDemands = employeeSpecialDemands;
    }

    // toString

    @Override
    public String toString() {
        return "WorkApplication{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeFirstName='" + employeeFirstName + '\'' +
                ", employeeLastName='" + employeeLastName + '\'' +
                ", employeePhoneNumber='" + employeePhoneNumber + '\'' +
                ", employeeBirthDate=" + employeeBirthDate +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeExperience='" + employeeExperience + '\'' +
                ", employeeSpecialDemands='" + employeeSpecialDemands + '\'' +
                '}';
    }
}
