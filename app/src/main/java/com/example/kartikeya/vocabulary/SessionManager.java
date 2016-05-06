package com.example.kartikeya.vocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by kartikeya on 25/3/16.
 */
public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Vocabulary";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    public SessionManager(Context context)
    {
        this._context=context;
        pref=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN,isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
