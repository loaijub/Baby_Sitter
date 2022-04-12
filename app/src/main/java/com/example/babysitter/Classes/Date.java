package com.example.babysitter.Classes;

public class Date {
    // attributes
    private String day;
    private String month;
    private String year;

    // constructor
    public Date(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // copy constructor
    public Date(Date date) {
        this.day = date.day;
        this.month = date.month;
        this.year = date.year;
    }

    // getters and setters
    public String getYear() {
        return this.year;
    }

    public String getMonth() {
        return this.month;
    }

    public String getDay() {
        return this.day;
    }

    public void setYear(String yearToSet) {
        this.year = yearToSet;
    }

    public void setMonth(String monthToSet) {
        this.month = monthToSet;
    }

    public void setDay(String dayToSet) {
        this.day = dayToSet;
    }


    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }


}
