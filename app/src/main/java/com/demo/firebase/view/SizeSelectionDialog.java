package com.demo.firebase.view;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.demo.firebase.StoreApplication;
import com.google.firebase.analytics.FirebaseAnalytics;

class SizeSelectionDialog {

    private static final String[] SIZES = new String[]{"S", "M", "L"};
    private static final String SIZE_PREF_KEY = "size_pref_key";
    private static final String SIZE_FIREBASE_PROPERTY = "size";

    private Context context;

    SizeSelectionDialog(Context context) {
        this.context = context;
    }

    void show() {
        new AlertDialog.Builder(context)
                .setTitle("Select your size")
                .setItems(SIZES, (dialog, which) -> saveSizeSelection(SIZES[which]))
                .create()
                .show();
    }

    private void saveSizeSelection(String size) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(SIZE_PREF_KEY, size).apply();

        StoreApplication.setUserProperty(SIZE_FIREBASE_PROPERTY, size);
    }
}