package com.care.nannycare;

public class User {
    String full_name;
    String date;
    String job_description;
    String number;
    String user_id;
    String email;
    String phone_number;


    public User() {
    }

    public User(String full_name, String date, String job_description, String number, String user_id, String email, String phone_number) {
        this.full_name = full_name;
        this.date = date;
        this.job_description = job_description;
        this.number = number;
        this.user_id = user_id;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}