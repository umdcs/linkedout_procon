package com.example.christopher.linkedoutapp;

/**
 * Created by Chris on 3/15/2017.
 */
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class student_profile_test {
    Student_Profile dummyProfile = new Student_Profile("dummy@d.umn.edu", "loginDummy", "Dummy",
                                                        "John Doe", "Duluth", "MN", "Spring",
                                                        "2018", "Computer Science");
    @Test
    public void testProfile() throws Exception {
        assertTrue(dummyProfile.verifyCredentials("Dummy", "loginDummy"));
        dummyProfile.setPassword("newP@ssw0rd");
        assertTrue(dummyProfile.verifyCredentials("Dummy", "newP@ssw0rd"));

    }
}
