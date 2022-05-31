package com.example.babysitter.Classes;

public class Deals {

    private final String dealId;
    private final String employeeId;
    private final String parentId;
    private String employeeAccepted;
    private String hasDone;
    private double parentRate;
    private String feedbackAboutParent;
    private double employeeRate;
    private String feedbackAboutEmployee;
    private Date completedDealDate;

    public Deals(String dealId, String employeeId, String parentId, String employeeAccepted, String hasDone, Date completedDealDate) {
        this.dealId = dealId;
        this.employeeId = employeeId;
        this.parentId = parentId;
        this.employeeAccepted = employeeAccepted;
        this.hasDone = hasDone;
        this.parentRate = 0; // we assume that the rate is 0.
        this.feedbackAboutEmployee = ""; // we give it a default value that is an empty string.
        this.feedbackAboutParent = ""; // we give it a default value that is an empty string.
        this.employeeRate = 0; // we assume that the rate is 0.
        this.completedDealDate = completedDealDate;
    }

    // getters and setters
    public String getDealId() {
        return dealId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getParentId() {
        return parentId;
    }

    public String isEmployeeAccepted() {
        return employeeAccepted;
    }

    public void setEmployeeAccepted(String employeeAccepted) {
        this.employeeAccepted = employeeAccepted;
    }

    public String isHasDone() {
        return hasDone;
    }

    public void setHasDone(String hasDone) {
        this.hasDone = hasDone;
    }

    public double getParentRate() {
        return parentRate;
    }

    public void setParentRate(double parentRate) {
        this.parentRate = parentRate;
    }

    public String getFeedbackAboutParent() {
        return feedbackAboutParent;
    }

    public void setFeedbackAboutParent(String feedbackAboutParent) {
        this.feedbackAboutParent = feedbackAboutParent;
    }

    public double getEmployeeRate() {
        return employeeRate;
    }

    public void setEmployeeRate(double employeeRate) {
        this.employeeRate = employeeRate;
    }

    public String getFeedbackAboutEmployee() {
        return feedbackAboutEmployee;
    }

    public void setFeedbackAboutEmployee(String feedbackAboutEmployee) {
        this.feedbackAboutEmployee = feedbackAboutEmployee;
    }

    public Date getCompletedDealDate() {
        return completedDealDate;
    }

    public void setCompletedDealDate(Date completedDealDate) {
        this.completedDealDate = completedDealDate;
    }

    // toString
    @Override
    public String toString() {
        return "Deals{" +
                "dealId=" + dealId +
                ", employeeId='" + employeeId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", employeeAccepted=" + employeeAccepted +
                ", hasDone=" + hasDone +
                ", parentRate=" + parentRate +
                ", feedbackAboutParent='" + feedbackAboutParent + '\'' +
                ", employeeRate=" + employeeRate +
                ", feedbackAboutEmployee='" + feedbackAboutEmployee + '\'' +
                ", completedDealDate=" + completedDealDate +
                '}';
    }
}
