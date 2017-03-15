package com.example.christopher.linkedoutapp;

/**
 * Created by Chris on 3/15/2017.
 */

public class Student_Profile implements Student_Interface {


    public Student_Profile(String e, String p, String u, String n, String c,
                    String s, String t, int y, String m) {
        email = e;
        password = p;
        username = u;
        name = n;
        city = c;
        state = s;
        gradTerm = t;
        gradYear = y;
        major = m;

    }

    public void setEmail(String s) {
        email = s;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String s) {
        password = s;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String s) {
        username = s;
    }
    public String getUsername() {
        return username;
    }

    public void setName(String s) {
        name = s;
    }
    public String getName() {
        return name;
    }

    public void setCity(String s) {
        city = s;
    }
    public String getCity() {
        return city;
    }

    public void setState(String s) {
        state = s;
    }
    public String getState() {
        return state;
    }

    public void setGradTerm(String s) {
        gradTerm = s;
    }
    public String getGradTerm() {
        return gradTerm;
    }

    public void setGradYear(int n) {
        gradYear = n;
    }
    public int getGradYear() {
        return gradYear;
    }

    public void setMajor(String s) {
        major = s;
    }
    public String getMajor() {
        return major;
    }

    public Boolean verifyCredentials(String un, String pw) {
        if(un.equals(username)&&pw.equals(password)) {
            return true;
        }
        else return false;
    }

    private String email, password, name, username, city, state, gradTerm, major;
    private int gradYear;
}
