package com.example.babysitter.Classes;

public class Feedback {
    User user; // the feedback writer
    float rating;
    String feedback;

    public Feedback(User user, float rating, String feedback) {
        this.user = user;
        this.rating = rating;
        this.feedback = feedback;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
