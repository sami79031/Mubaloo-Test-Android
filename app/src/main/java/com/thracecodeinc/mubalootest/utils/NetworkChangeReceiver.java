package com.thracecodeinc.mubalootest.utils;

/**
 * Created by Samurai on 6/30/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private OnNetworkChangeListener listener = null;
    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        if (listener != null) {
            listener.onSNetworkChange(status);
        }

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

    public interface OnNetworkChangeListener {
         void onSNetworkChange(String message);
    }
}
