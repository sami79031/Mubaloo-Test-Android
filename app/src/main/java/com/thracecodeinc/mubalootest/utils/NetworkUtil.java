package com.thracecodeinc.mubalootest.utils;

/**
 * Created by Samurai on 6/30/16.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public final static String WIFI_ENABLED = "Wifi enabled";
    public final static String MOBILE_ENABLED = "Mobile data enabled";
    public final static String NOT_CONNECTED = "Not connected to the internet";

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = WIFI_ENABLED;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = MOBILE_ENABLED;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = NOT_CONNECTED;
        }
        return status;
    }
}
