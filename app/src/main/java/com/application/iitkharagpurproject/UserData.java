package com.application.iitkharagpurproject;

public class UserData {

    // string variable for
    // storing employee name.
    private String UserName;

    // string variable for storing
    // employee contact number
    private String UserContactNumber;

    // string variable for storing
    // employee address.
    private String UseremailAddress;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public UserData() {

    }

    public UserData(String name, String UserContactNumber, String UseremailAddress) {
        this.UserName = name;
        this.UserContactNumber = UserContactNumber;
        this.UseremailAddress = UseremailAddress;
    }


    // created getter and setter methods
    // for all our variables.
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserContactNumber() {
        return UserContactNumber;
    }

    public void setUserContactNumber(String UserContactNumber) {
        this.UserContactNumber = UserContactNumber;
    }

    public String getUseremailAddress() {
        return UseremailAddress;
    }

    public void setUseremailAddress(String UseremailAddress) {
        this.UseremailAddress = UseremailAddress;
    }
}
