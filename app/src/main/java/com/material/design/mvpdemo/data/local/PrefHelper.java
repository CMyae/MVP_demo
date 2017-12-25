package com.material.design.mvpdemo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chan on 12/16/17.
 */

public class PrefHelper {

    private static final String USERNAME = "username";

    public static void saveUsernameInLocal(Context context,String username){
        SharedPreferences pref = context.getSharedPreferences("USER",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(USERNAME,username.trim());
        editor.commit();
    }


    public static String getLoginUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences("USER",Context.MODE_PRIVATE);
        return pref.getString(USERNAME,"User");

    }


}
