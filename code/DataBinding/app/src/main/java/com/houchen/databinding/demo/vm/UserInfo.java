package com.houchen.databinding.demo.vm;

public class UserInfo {

    public String firstName;
    public String lastName;

    private String mFirstName;
    private String mLastName;

    public UserInfo() {
    }

    public UserInfo(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String firstName() {
        return "firstName method";
    }

    public String lastName() {
        return "lastName method";
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

}
