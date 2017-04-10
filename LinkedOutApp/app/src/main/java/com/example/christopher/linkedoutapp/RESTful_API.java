package com.example.christopher.linkedoutapp;

/**
 * Created by Chris on 3/23/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.app.PendingIntent.getActivity;
import static org.json.JSONObject.NULL;

public class RESTful_API extends AppCompatActivity {
    /**
     * HTTPAsyncTask for managing HTTP messages in a separate thread
     *
     * This class uses the AsyncTask class and extends it to open up
     * a HTTP connection to a HTTP Server. The class overrides the doInBackground
     * method to create the HTTP connection and make the Request while also
     * managing the response.  Upon completion of doInBackground, the task
     * calls the onPostExecute function.
     */
    private boolean login = false;
    private login loginClass;
    private SharedPreferences shpref;

    private class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            /* The String... params argument represents an arbitrary sized
             * array.  In this case, we might have 2 or 3 parameters depending
             * on if the request type is a GET or POST
             *
             * params[0] contains the HOST and port number and route of the URI
             */
            Log.d("Debug:", "Attempting to connect to: " + params[0]);

            /* Java class to create a network connection to a HTTP
             * Server. InputStreams are used to process the incoming
             * network data.
             */
            HttpURLConnection serverConnection = null;
            InputStream is = null;

            try {
                /* The Java URL class is used to hold the URI */
                URL url = new URL(params[0]);

                /* We can open a connection to this URL now */
                serverConnection = (HttpURLConnection) url.openConnection();

                /* The second parameter, params[1] contains the TYPE of the HTTP
                 * request. It can be GET, POST, PUT or DELETE.
                 */
                serverConnection.setRequestMethod(params[1]);

                /* If the TYPE is POST, PUT or DELETE then we need to take
                 * the third parameter params[2] which contains the JSON data
                 * we need to place in the body of the HTTP message, and write
                 * that JSON data as a string to the network connection to the
                 * HTTP server.
                 */
                if (params[1].equals("POST") ||
                        params[1].equals("PUT") ||
                        params[1].equals("DELETE")) {
                    Log.d("DEBUG POST/PUT/DELETE:", "In post: params[0]=" + params[0] + ", params[1]=" + params[1] + ", params[2]=" + params[2]);

                    /* Various server parameters need to set on HTTP connections that indicate the type
                     * of data that will be sent. In our case, we are sending JSON as output so need to
                     * set the Content-Type header attribute.
                     */
                    serverConnection.setDoOutput(true);
                    serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    /* Since params[2] contains the JSON String to send, we must also calculate the
                     * byte length of this data and set the Content-Length header attribute as well.
                     */
                    serverConnection.setRequestProperty("Content-Length", "" +
                            Integer.toString(params[2].toString().getBytes().length));

                    /* Finally, the JSON data can be written out to the server by using
                     * a DataOutputStream that is created with the server's output stream.
                     */
                    DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());

                    /* Write the json string data to the network */
                    out.writeBytes(params[2].toString());

                    /* flush and close the output stream buffer */
                    out.flush();
                    out.close();
                }

                /* ************************
                 * HTTP RESPONSE Section
                 * All requests are followed by a response with HTTP
                 *
                 * Get the response code and determine what to do.
                 */
                int responseCode = serverConnection.getResponseCode();

                Log.d("Debug: ", "HTTP Response Code : " + responseCode);

                /* Get the input stream (what's coming from our server to the Android client)
                 * process the JSON data that's contained with it.
                 */
                is = serverConnection.getInputStream();

                /* This implementation is built so that ALL Responses send back a JSON data, as either
                 * the data you want from a GET Request or as confirmation of receiving the data
                 * on a POST, PUT, or DELETE Request.
                 */
                StringBuilder sb = new StringBuilder();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                /* At this point, the StringBuilder sb contains all the data that was in the
                 * body of the Response. Since we expect JSON to be in this, the string hopefully
                 * contains valid JSON data.  We need to return this string out of this
                 * function and the onPostExecute function will process it.
                 */
                return sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }

            /* If you receive this statement, you know there is an error */
            return "Should not get to this if the data has been sent/received correctly!";
        }
        // END ASYNC TASK


        /**
         * @param result the result from the query
         */
        protected void onPostExecute(String result) {

            /* Take the result (a String) returned from the doInBackground function and
             * convert it to a JSONObject to test that it was a valid JSON data format.
             */
            try {
                JSONObject jsonData = new JSONObject(result);
                Log.d("onPostExec Valid JSON:", jsonData.toString());

                // used to save JSON response from loginGET to the login activity
                if (login) {
                    Log.d("loginGET", "returnData: " + jsonData.toString());
                    loginClass.setData(jsonData);
                    loginClass.updatePrefsAndStart();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    /***** BEGIN URI DEFINITIONS *****/
    /**
     * Acts as the onClick callback for the login Student Button. The code will generate a REST GET
     * action to the REST Server.
     *
     * @param email
     * @param password
     *
     * @returns void - But it does set the JSONObject data in loginClass to the response from server
     */
    public void loginGET(String email, String password, login l) {
        login = true; // bool to let onPostExe know to update loginClass...
        loginClass = l; // set loginClass so that it may save the response...



//        StringBuilder sb = new StringBuilder("http://ukko.d.umn.edu:8080/login/");
        StringBuilder sb = new StringBuilder("http://akka.d.umn.edu:8456/login/");


        sb.append(email + "/");
        sb.append(password);

        new HTTPAsyncTask().execute(sb.toString(), "GET");
    }

    /**
     * Acts as the onClick callback for the registerStudentPOST Button. The code will generate a REST POST
     *6s action to the REST Server.
     *
     * @param prefs
     */
    public void registerStudentPOST(SharedPreferences prefs) {

        JSONObject jsonParam = new JSONObject();
        try {
            // Put values into the jsonParam object
            jsonParam.put("username", prefs.getString("username", ""));
            jsonParam.put("password", prefs.getString("password", ""));
            jsonParam.put("email", prefs.getString("email", ""));
            jsonParam.put("fullName", prefs.getString("fullName", ""));
            jsonParam.put("city", prefs.getString("city", ""));
            jsonParam.put("state", prefs.getString("state", ""));
            jsonParam.put("gradTerm", prefs.getString("gradTerm", ""));
            jsonParam.put("gradYear", prefs.getString("gradYear", ""));
            jsonParam.put("major", prefs.getString("major", ""));
            jsonParam.put("photo", prefs.getString("profilePic", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());

<<<<<<< HEAD
=======
//        new HTTPAsyncTask().execute("http://ukko.d.umn.edu:8080/registerStudent", "POST", jsonParam.toString());
>>>>>>> sprint2
        new HTTPAsyncTask().execute("http://akka.d.umn.edu:8456/registerStudent", "POST", jsonParam.toString());

    }

    /***** IGNORE THIS FOR NOW ****
     * Acts as the onClick callback for the REST PUT Button. The code will generate a REST PUT
     * action to the REST Server.
     *
     * @param view
     */
/*    public void restPUT(View view) {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("name", "Pete");
            jsonParam.put("description", "Real");
            jsonParam.put("enable", "true");
            jsonParam.put("val1", "arg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG [PUT]:", jsonParam.toString());
        new HTTPAsyncTask().execute("http://192.168.0.3:8080/userData", "PUT", jsonParam.toString());
    } */

    /***** IGNORE FOR NOW *****
     * Acts as the onClick callback for the REST DELETE Button. The code will generate a REST DELETE
     * action to the REST Server.
     *
     * @param view
     */
    /*
    public void restDELETE(View view) {

        JSONObject jsonParam = null;
        try {
            //Create JSONObject here
            jsonParam = new JSONObject();
            jsonParam.put("name", "Pete");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("DEBUG:", jsonParam.toString());
        new HTTPAsyncTask().execute("http://192.168.0.3:8080/userData", "DELETE", jsonParam.toString());
    } */
}
