package com.example.crudappjava;

import android.app.Application;

import com.example.crudappjava.utils.Prefs;

public class App extends Application {
    public static Prefs prefs;
    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new Prefs(this);
    }
}
