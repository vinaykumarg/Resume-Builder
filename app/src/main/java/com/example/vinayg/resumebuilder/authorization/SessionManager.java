package com.example.vinayg.resumebuilder.authorization;

/**
 * Created by vinay.g.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.vinayg.resumebuilder.activities.LoginActivity;
import com.example.vinayg.resumebuilder.database.MyAppDb;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    private final SharedPreferences pref;

    // Editor for Shared preferences
    private final Editor editor;

    // Context
    private final Context _context;

    // Shared pref mode
    private final int PRIVATE_MODE = 0;

    // Sharedpreference file name
    private static final String PREF_NAME = "ResumeBuilder";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public static final String KEY_ID = "ID";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_PHOTO = "photouri";
    private final MyAppDb mMyAppDb;
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        mMyAppDb = new MyAppDb(context);
        mMyAppDb.open();

    }
    public String getKeyId(){
        return pref.getString(KEY_ID, null);
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id,String name, String email,String photouri){
        if(!mMyAppDb.checkifexist(id)){
            mMyAppDb.createUser(id,name,email);
        }
        editor.putString(KEY_ID, id);
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // Storing photourl in pref
        editor.putString(KEY_PHOTO, photouri);

        // commit changes
        editor.commit();
    }
    public void changeImageuri(String photouri) {
        editor.putString(KEY_PHOTO, photouri);
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // user photouri
        user.put(KEY_PHOTO,pref.getString(KEY_PHOTO,null));
        user.put(KEY_ID,pref.getString(KEY_ID,null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        mMyAppDb.close();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        // Starting Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}