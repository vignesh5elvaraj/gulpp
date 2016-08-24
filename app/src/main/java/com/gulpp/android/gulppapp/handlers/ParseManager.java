package com.gulpp.android.gulppapp.handlers;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenardgeorge on 27/01/16.
 */
public class ParseManager {

    private static String TAG = "PARSE_HANDLER" ;

    private OnFinishListener onFinishListener;

    /**
     * MENU PAGE ACTIVITY
     */
    public  List<String> getMenuTypes(){

        final List<String> menuTypesList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuTypes");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    Log.i(TAG, "Object retrieval success");
                    for (int i = 0; i < objects.size(); i++) {
                        menuTypesList.add(objects.get(i).getString("TypeName"));
                        Log.i(TAG, objects.get(i).getString("TypeName"));
                        onFinishListener.onFetchComplete();

                    }
                }
                else{
                    Log.i(TAG,"Object retrieval Failure");
                    Log.i(TAG, String.valueOf(e.getCode()));
                }
            }
        });

        return menuTypesList ;

    }



    public  List<String> getCityList() {

        final List<String> cityList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cities");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    Log.i(TAG, "Object retrieval success");
                    for (int i = 0; i < objects.size(); i++) {
                        cityList.add(objects.get(i).getString("cityName"));
                        Log.i(TAG, objects.get(i).getString("cityName"));
                    }

                }
                else{
                    Log.i(TAG,"Object retrieval Failure");
                }
            }
        });

        return cityList ;
    }


    public interface OnFinishListener{

        public void onFetchComplete();

    }




}
