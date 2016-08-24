package com.gulpp.android.gulppapp.singletons;

import android.content.Context;
import android.content.SharedPreferences;

import com.gulpp.android.gulppapp.Constant;

/**
 * Created by lenardgeorge on 18/03/16.
 */
public class SharedPref {

    private static SharedPreferences sharedPreferences ;

    public static void initialiseSharedPreferences(Context context){

        if(sharedPreferences != null)
        {
            sharedPreferences =  context.getSharedPreferences(Constant.SHARED_PREF_KEY, context.MODE_PRIVATE);
        }
    }

    public SharedPreferences getSharedPref(){

        return sharedPreferences;
    }

}
