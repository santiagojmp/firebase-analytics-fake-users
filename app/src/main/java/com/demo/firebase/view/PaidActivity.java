package com.demo.firebase.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.firebase.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class PaidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);
        FirebaseCrashlytics.getInstance().log("Showing 'paid' view");
    }

    public void continueShopping(View v){
        MainActivity.navigate(this);
    }
}
