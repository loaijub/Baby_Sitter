package com.example.babysitter.Classes;

public class Children {

    private final String parentId; // id of one parent
    private final Date birthDate; // the birthdate of the child

    public Children(String parentId, Date ageOfChildren) {
        this.parentId = parentId;
        this.birthDate = ageOfChildren;
    }

    // getters
    public String getParentId() {
        return parentId;
    }

    public Date getAgeOfChildren() {
        return birthDate;
    }

    // toString
    @Override
    public String toString() {
        return "Children{" +
                "parentId='" + parentId + '\'' +
                ", ageOfChildren=" + birthDate +
                '}';
    }
}
