package com.example.babysitter.Classes;

public class Report {

    private final String reportId; // a unique number for each report.
    private final String applicantId; // id of the user who applied the report.
    private final String reportedUserId; // id of the user who was reported.
    private final Date dateOfSub; // date of the submitting the report to the system.
    private Date dateOfCheck; // date of checking the report by the admin.
    private final Date dateOfAccident; // date of the accident described in the report.
    private String accidentDetails; // the accident details
    private boolean hasChecked; // if the admin checked the report or not yet.
    private String outcome; // the result of the decision of the admin.

    public Report(String reportId, String applicantId, String reportedUserId, Date dateOfSub, Date dateOfAccident, String accidentDetails) {
        this.reportId = reportId;
        this.applicantId = applicantId;
        this.reportedUserId = reportedUserId;
        this.dateOfSub = dateOfSub;
        this.dateOfCheck = null;
        this.dateOfAccident = dateOfAccident;
        this.accidentDetails = accidentDetails;
        this.hasChecked = false;
        this.outcome = "";
    }

    // getters and setters
    public String getReportId() {
        return reportId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getReportedUserId() {
        return reportedUserId;
    }

    public Date getDateOfSub() {
        return dateOfSub;
    }

    public Date getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(Date dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public Date getDateOfAccident() {
        return dateOfAccident;
    }

    public String getAccidentDetails() {
        return accidentDetails;
    }

    public void setAccidentDetails(String accidentDetails) {
        this.accidentDetails = accidentDetails;
    }

    public boolean isHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(boolean hasChecked) {
        this.hasChecked = hasChecked;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    // toString
    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", applicantId='" + applicantId + '\'' +
                ", reportedUserId='" + reportedUserId + '\'' +
                ", dateOfSub=" + dateOfSub +
                ", dateOfCheck=" + dateOfCheck +
                ", dateOfAccident=" + dateOfAccident +
                ", accidentDetails='" + accidentDetails + '\'' +
                ", hasChecked=" + hasChecked +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
