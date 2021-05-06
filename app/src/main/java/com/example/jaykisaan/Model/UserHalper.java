package com.example.jaykisaan.Model;

public class UserHalper {
    String full_name , email_id , mobile_no , gender , password , user_type , userID;

    public UserHalper(String full_name, String email_id, String mobile_no, String gender, String password, String user_type, String userID) {
        this.full_name = full_name;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.gender = gender;
        this.password = password;
        this.user_type = user_type;
        this.userID = userID;
    }

    public UserHalper() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
