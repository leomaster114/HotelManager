package com.example.hotelmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpref {
    private static final String NAME = "SHARED_PREF";
    private static Sharedpref mInstances;
    private Context context;

    public Sharedpref(Context context) {
        this.context = context;
    }

    public static synchronized Sharedpref getInstances(Context context) {
        if (mInstances == null) mInstances = new Sharedpref(context);
        return mInstances;
    }
    public void saveFloor(int nf,int nr){
        SharedPreferences preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("number_floor",nf);
        editor.putInt("number_room",nr);
        editor.apply();
    }

    public int getFloor(){
        SharedPreferences preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return preferences.getInt("number_floor",-1);
    }
    public int getRoom(){
        SharedPreferences preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return preferences.getInt("number_room",-1);
    }
}
