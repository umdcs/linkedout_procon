package com.example.christopher.linkedoutapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import org.junit.Test;

import static android.util.Base64.decode;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by imcdo on 4/12/2017.
 */

//@RunWith(RobolectricTestRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class profilePicTest {
    //String imgString = "This is an image.";
//    @Mock
//    Context mMockContext;
//    Bitmap icon = BitmapFactory.decodeResource(mMockContext.getResources(),
//            R.drawable.linkedoutlogo);
//    byte[] decodedString = Base64.decode(imgString.getBytes(), Base64.DEFAULT);
//    Bitmap imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//    ProfilePic testPicObject = new ProfilePic(imageBitmap);


    //@Mock
    //Context mMockContext;   //Create new mock context.

//    @Test
//    public void profilePicEncodingTest(){
//
//        assertEquals(testPicObject.getEncodedBitmap(testPicObject.getBitmap()), imgString, 0);
//
//
//    }

    @Test
    public void profilePicPathTest(){
        ProfilePic aPic = new ProfilePic(null);
        String pathString = "This is a path.";
        aPic.setPath(pathString);
        assertEquals(aPic.getPath(), pathString);
        assertFalse(aPic.getPath() == null);
    }



}
