package com.example.formationmobile.models;

public class User {
    private String fullName,email,cin,tel,password;

    public User() {
    }

    public User(String fullName, String email, String cin, String tel, String password) {
        this.fullName = fullName;
        this.email = email;
        this.cin = cin;
        this.tel = tel;
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
