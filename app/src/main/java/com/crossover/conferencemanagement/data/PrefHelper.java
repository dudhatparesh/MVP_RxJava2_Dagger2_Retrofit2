package com.crossover.conferencemanagement.data;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by PareshDudhat on 10-03-2017.
 */
@Singleton
public class PrefHelper {
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_TYPE = "user_type";
    private SharedPreferences mPreferences;

    @Inject
    public PrefHelper(SharedPreferences preferences) {
        this.mPreferences = preferences;
    }

    public boolean set(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String get(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }
}
