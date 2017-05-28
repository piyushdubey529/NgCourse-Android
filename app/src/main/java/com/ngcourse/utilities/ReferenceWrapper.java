package com.ngcourse.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ngcourse.Settings.Keys;


/**
 * Created by piyush on 7/7/16.
 */
public class ReferenceWrapper {
    private Activity activity;
    private static ReferenceWrapper referenceProvider;
    private SharedPreferences sharedPreferences;


    private ReferenceWrapper(Activity activity) {
        this.activity = activity;

    }

    public static ReferenceWrapper getReferenceProvider(Activity activity) {
        if (referenceProvider == null) {
            referenceProvider = new ReferenceWrapper(activity);
        }
        return referenceProvider;
    }


    public SharedPreferences getSharedPreferences() {

        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(Keys.MY_PREFERENCES, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}

