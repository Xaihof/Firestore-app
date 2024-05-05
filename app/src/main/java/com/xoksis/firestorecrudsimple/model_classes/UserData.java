package com.xoksis.firestorecrudsimple.model_classes;

public class UserData {

    private String name, rollNo, gmail, gender, userID;

    public UserData() {
    }

    public UserData(String name, String rollNo, String gmail, String gender, String userID) {
        this.name = name;
        this.rollNo = rollNo;
        this.gmail = gmail;
        this.gender = gender;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getGmail() {
        return gmail;
    }

    public String getGender() {
        return gender;
    }

    public String getuserID() {
        return userID;
    }
}
