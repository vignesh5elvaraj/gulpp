package com.gulpp.android.gulppapp.model;

/**
 * Created by lenardgeorge on 22/01/16.
 */
public class UserData {

    String userName ;
    String userEmail ;
    String userMobileNumber ;

    static UserData instance ;

    public static UserData getInstance(){

        if(instance!= null){
           instance = new UserData();
           return instance ;
        }
        else
           return instance ;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
