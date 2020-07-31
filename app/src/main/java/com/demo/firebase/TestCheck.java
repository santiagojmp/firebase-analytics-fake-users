package com.demo.firebase;

public class TestCheck {

    public static synchronized boolean isRunningTest() {
        try {
            Class.forName("androidx.test.espresso.Espresso");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
