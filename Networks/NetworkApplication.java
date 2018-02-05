package com.example.rspl_rahul.gitrepo.Networks;

import android.app.Application;

/**
 * Created by rspl-rahul on 20/1/18.
 */

public class NetworkApplication extends Application {
    private static NetworkApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized NetworkApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}