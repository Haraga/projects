package com.example.ubbapp.structures;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    private static final String PREF_USER_NAME = "";
    private static final String PREF_LOGGED_IN_USER = "email";

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearSharedPreferences(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    public static void setUser(Context ctx, String user) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGED_IN_USER, user);
        editor.apply();
    }

    public static String getUser(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_LOGGED_IN_USER, "");
    }
}
