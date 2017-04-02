package com.example.christopher.linkedoutapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by imcdo on 3/30/2017.
 */

public class Profile_Pic {

    public Profile_Pic(){
        pic = null;
    }

    public final Bitmap pic;

    //Photo code used when strting the intent to open the gallery.
    public final static int PICK_PHOTO_CODE = 1;

    //To be checked against request code in onActivityResult
    public final static int SELECT_IMAGE = 1;

    //Permission code checked in onRequestPermissionsResult
    public final static int STORAGE_PERMISSION_CODE = 44;

    //Get path to image file.
    public String getPath(Uri uri, Context context){
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        return cursor.getString(columnIndex);
    }

    //Check for read storage permission.
    public boolean isReadStorageAllowed(Activity activity) {
        //Get the permission status
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted return true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted return false
        return false;
    }

    //Requesting permission
    public void requestStoragePermission(Activity activity){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously
            //Notify the user they have denied the permission previously
            Toast.makeText(activity,"You denied this permission previously.",Toast.LENGTH_LONG).show();
        }

        //Request permission
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    //@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code
        if(requestCode == STORAGE_PERMISSION_CODE){

            //Permission granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast if permission is granted.
                //Toast.makeText(this,"You have granted access to the photo gallery.",Toast.LENGTH_LONG).show();
            }else{
                //Displaying toast if permission is not granted.
                //Toast.makeText(this,"You have denied access to your photo gallery.",Toast.LENGTH_LONG).show();
            }
        }
   }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int rotateAngle) {
        float aspectRatio = bm.getWidth() /
                (float) bm.getHeight();
        int width = 480;
        int height = Math.round(width / aspectRatio);

        bm = Bitmap.createScaledBitmap(
                bm, width, height, false);
        return RotateBitmap(bm, rotateAngle);
    }

    public int getPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        context.getContentResolver().notifyChange(imageUri, null);
        File imageFile = new File(imagePath);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        System.out.println("angle: " + rotate);
        return rotate;

    }

}
