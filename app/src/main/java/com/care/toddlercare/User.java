package com.care.toddlercare;

public class User {
    String first_Name, last_Name, about, years_of_experience;
    long age;

    public User() {
    }

    public User(String first_Name, String last_Name, String about, String years_of_experience, long age) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.about = about;
        this.years_of_experience = years_of_experience;
        this.age = age;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(String years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}