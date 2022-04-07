package com.example.babysitter.Classes;

public class Date {
    // attributes
    private int day;
    private int month;
    private int year;

    // constructor
    public Date(int day, int month, int year) {
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
    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public void setYear(int yearToSet) {
        this.year = yearToSet;
    }

    public void setMonth(int monthToSet) {
        this.month = monthToSet;
    }

    public void setDay(int dayToSet) {
        this.day = dayToSet;
    }

    // methods
    public int compareTo(Date other) { // function gets another date and returns a positive number if current date closer to current date, a negative number if the other date is closer, otherwise it returns 0.

        if (this.year > other.year)
            return 1;

        if (this.year < other.year)
            return -1;
        // we have the same years
        if (this.month > other.month)
            return 1;

        if (this.month < other.month)
            return -1;
        // we have the same month
        if (this.day > other.day)
            return 1;

        if (this.day < other.day)
            return -1;
        // it is the same date
        return 0;
    }

    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }


}
