package com.demo.firebase;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    static FirebaseAnalytics get(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}
