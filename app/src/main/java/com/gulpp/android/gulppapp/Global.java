package com.gulpp.android.gulppapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.gulpp.android.gulppapp.handlers.SubscriptionListHandler;
import com.gulpp.android.gulppapp.model.Menu;
import com.gulpp.android.gulppapp.model.SubscribedItem;
import com.gulpp.android.gulppapp.singletons.SharedPref;
import com.parse.Parse;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lenardgeorge on 22/01/16.
 */
public class Global extends Application {

    private String userName ;
    private String userMobNumber ;
    private String userEmail ;
    private String userId ;
    private SharedPreferences mySharedPreferences ;

    //For Menu page
    private List<Menu> menuList ;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mySharedPreferences = getSharedPreferences(Constant.SHARED_PREF_KEY,MODE_PRIVATE);
        Parse.initialize(this);
        initializePlateItemsList();
        initializeSharedPreferences();
        initializeSubscribedItemsList();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ChampagneLimousines.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }

    /**
     * For Plate items list
     */
    private void initializePlateItemsList(){
        PlateItemListHandler.initializeInstance();
    }

    /**
     *For Subscription list
     */
    private void initializeSubscribedItemsList(){
        SubscriptionListHandler.initializeInstance();
    }

    /**
     *For Shared Preferences
     */
    private void initializeSharedPreferences(){
        SharedPref.initialiseSharedPreferences(this);

    }



    /**
     * MENU ITEMS LIST
     */
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }



    public SharedPreferences getMySharedPreferences() {
        return mySharedPreferences;
    }


    public void setUserId(String userId){
        this.userId = userId ;
    }

    public String getUserId(){
        return this.userId ;
    }

}
