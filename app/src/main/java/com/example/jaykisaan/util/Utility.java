package com.example.jaykisaan.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jaykisaan.activity.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class Utility {

    public static void setIsLogin(Context activity, String value){
        SharedPreferences pref = activity.getSharedPreferences("IsLogin",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("IsLogin",value);
        editor.apply();
    }

    public static String getIsLogin(Context activity){
        SharedPreferences pref = activity.getSharedPreferences("IsLogin",0);
        return pref.getString("IsLogin","");
    }
}
