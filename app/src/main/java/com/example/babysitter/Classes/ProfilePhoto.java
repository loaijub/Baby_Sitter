package com.example.babysitter.Classes;

public class ProfilePhoto {
    String userId;
    String imageUrl;

    public ProfilePhoto(String userId, String imageUrl) {
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
