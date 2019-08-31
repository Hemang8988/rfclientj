package com.example.crudappjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String PREF_FILE_NAME = "com.example.crudappjava";
    private static final String PREF_IS_INSERTED = "PREF_IS_INSERTED";

    private boolean isInseted;
    private SharedPreferences sharedPreferences;

    public Prefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,0);
    }

    public boolean isInseted() {
        return sharedPreferences.getBoolean(PREF_IS_INSERTED,false);
    }

    public void setInseted(boolean inseted) {
        sharedPreferences.edit().putBoolean(PREF_IS_INSERTED,inseted).apply();
    }
}
