package com.example.myfirstapplication.Model;

public class User {
    private String fullName,email,phone,cin,password;

    public User() {
    }

    public User(String fullName, String email, String phone, String cin, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.cin = cin;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

