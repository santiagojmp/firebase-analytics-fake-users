package com.demo.firebase;

public class Log {

    private static final String TAG = "Store";

    public static void e(String log) {
        android.util.Log.e(TAG, log);
    }

    public static void w(String log) {
        android.util.Log.w(TAG, log);
    }

    public static void i(String log) {
        android.util.Log.i(TAG, log);
    }
}
