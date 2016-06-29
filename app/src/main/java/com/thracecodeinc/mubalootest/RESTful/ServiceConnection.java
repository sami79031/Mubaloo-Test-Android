package com.thracecodeinc.mubalootest.RESTful;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Samurai on 6/22/16.
 */


public class ServiceConnection {

    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;


    public ServiceConnection() {
    }

    public String makeWebServiceCall(String url, int requestmethod) throws Exception {
        return this.doHttpUrlConnectionAction(url, requestmethod);
    }
    private String doHttpUrlConnectionAction(String desiredUrl, int method)
            throws Exception {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            if (method == GETRequest)
                connection.setRequestMethod("GET");
            else
                connection.setRequestMethod("POST");

            //write output to this url
            //connection.setDoOutput(true);

            //15 seconds to respond
            connection.setReadTimeout(15 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
