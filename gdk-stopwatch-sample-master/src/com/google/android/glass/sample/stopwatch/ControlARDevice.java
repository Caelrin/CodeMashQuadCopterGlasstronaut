package com.google.android.glass.sample.stopwatch;

import android.os.AsyncTask;
import android.util.Log;
import com.codeminders.ardrone.ARDrone;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by agibson on 1/7/14.
 */
public class ControlARDevice extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... command) {
        Log.e("AR", "TRYING TO LIFT OFF");
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpclient.execute(new HttpGet("http://10.32.35.200:8080/?command=" + command[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                Log.e("AR", "RESPONSE IS THE FOLLOWING:" + responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            String toPrint = e.getMessage();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(StackTraceElement ele : stackTrace) {
                toPrint += "\r\n" + ele.toString();
            }
            Log.e("AR", toPrint);
        }
        return "";
    }
}
