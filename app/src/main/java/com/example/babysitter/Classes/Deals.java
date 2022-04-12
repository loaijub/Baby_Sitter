package com.example.babysitter.Classes;

public class Deals {

    private final int dealId;
    private final String employeeId;
    private final String parentId;
    private boolean employeeAccepted;
    private boolean hasDone;
    private int parentRate;
    private String feedbackAboutParent;
    private int employeeRate;
    private String feedbackAboutEmployee;
    private Date completedDealDate;

    public Deals(int dealId, String employeeId, String parentId) {
        this.dealId = dealId;
        this.employeeId = employeeId;
        this.parentId = parentId;
        this.employeeAccepted = true; // we assume that the employee will accept.
        this.hasDone = false; // we assume that the work wasn't done yet.
        this.parentRate = 0; // we assume that the rate is 0.
        this.feedbackAboutEmployee = ""; // we give it a default value that is an empty string.
        this.feedbackAboutParent = ""; // we give it a default value that is an empty string.
        this.employeeRate = 0; // we assume that the rate is 0.
        this.completedDealDate = null; // the work is not done yet.
    }

    // getters and setters
    public int getDealId() {
        return dealId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getParentId() {
        return parentId;
    }

    public boolean isEmployeeAccepted() {
        return employeeAccepted;
    }

    public void setEmployeeAccepted(boolean employeeAccepted) {
        this.employeeAccepted = employeeAccepted;
    }

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }

    public int getParentRate() {
        return parentRate;
    }

    public void setParentRate(int parentRate) {
        this.parentRate = parentRate;
    }

    public String getFeedbackAboutParent() {
        return feedbackAboutParent;
    }

    public void setFeedbackAboutParent(String feedbackAboutParent) {
        this.feedbackAboutParent = feedbackAboutParent;
    }

    public int getEmployeeRate() {
        return employeeRate;
    }

    public void setEmployeeRate(int employeeRate) {
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
