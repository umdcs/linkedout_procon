package com.example.christopher.linkedoutapp;

import android.content.SharedPreferences;
import android.content.Context;

import static junit.framework.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by daniellepatterson on 4/12/17.
 */

public class loginTest {

    private final static String STUDENT_PREFS = "Student Preferences";
    private SharedPreferences prefs;
    private RESTful_API nodeServer;
    private JSONObject data;
    private Context mContext;

    @Before
    public void setUp() throws JSONException {
        prefs = mContext.getSharedPreferences(STUDENT_PREFS, 0);
        data = new JSONObject();
        data.putOpt("username", "janedoe");

    }

    @Test
    public void testLogin() {

        login loginTest = new login();

        loginTest.setData(data);
        assertEquals(loginTest.getData(), data);

    }

}
