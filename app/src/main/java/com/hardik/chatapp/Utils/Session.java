package com.hardik.chatapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hardik.chatapp.Models.User;

public class Session {
    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    private static String SHARED_PREFERENCE_NAME = "session";

    public static boolean setUser(Context context, User user) {
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        editor.putInt("id",user.getId());
        editor.putString("email",user.getEmail());
        editor.putString("fname",user.getFname());
        editor.putString("lname",user.getLname());
        editor.putString("username",user.getUsername());
        editor.putString("profile_img",user.getProfile_img());
        editor.commit();
        editor.apply();
        return true;
    }
    public static boolean removeUser(Context context){
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        editor.remove("id");
        editor.remove("fname");
        editor.remove("lname");
        editor.remove("email");
        editor.remove("profile_img");
        editor.remove("username");
        editor.commit();
        editor.apply();
        return true;
    }
    public static boolean isLogin(Context context){
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPreferences.contains("id");
    }
    public static int getId(Context context){
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPreferences.getInt("id",0);
    }
}
