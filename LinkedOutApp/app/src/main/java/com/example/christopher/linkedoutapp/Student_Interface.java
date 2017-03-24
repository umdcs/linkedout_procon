package com.example.christopher.linkedoutapp;

/**
 * Created by Chris on 3/15/2017.
 */

interface Student_Interface {

    void setEmail(String s);
    String getEmail();

    void setPassword(String s);
    String getPassword();

    void setUsername(String s);
    String getUsername();

    void setName(String s);
    String getName();

    void setCity(String s);
    String getCity();

    void setState(String s);
    String getState();

    void setGradTerm(String s);
    String getGradTerm();

    void setGradYear(String n);
    String getGradYear();

    void setMajor(String s);
    String getMajor();

    Boolean verifyCredentials(String un, String pw);
}
