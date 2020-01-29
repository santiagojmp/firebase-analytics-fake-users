package com.demo.firebase;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class StoreApplication extends Application {

    static FirebaseAnalytics analytics;

    @Override
    public void onCreate() {
        super.onCreate();
        analytics = FirebaseAnalytics.getInstance(getApplicationContext());
    }

    static public void logEvent(String name, Bundle params) {
        analytics.logEvent(name, params);
    }

    static public void setUserProperty(String name, String value) {
        analytics.setUserProperty(name, value);
    }
}
