package com.example.babysitter.Classes;

public class Address {
    private String city;
    private String street;
    private String house_number;

    public Address(String city, String street, String house_number) {
        this.city = city;
        this.street = street;
        this.house_number = house_number;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse_number() {
        return house_number;
    }

    @Override
    public String toString() {
        return
                "City: " + city + "\n" +
                "Street: " + street + "\n" +
                "House number: " + house_number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }
}
