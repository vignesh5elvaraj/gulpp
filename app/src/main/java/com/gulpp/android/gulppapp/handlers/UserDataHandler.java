package com.gulpp.android.gulppapp.handlers;

import com.gulpp.android.gulppapp.model.UserData;

/**
 * Created by lenardgeorge on 13/04/16.
 */
public class UserDataHandler {

    UserDataHandler instance ;
    UserData userDataInstace ;

    UserDataHandler(){
       userDataInstace = UserData.getInstance();
    }

    public void newInstance(){

        if(instance != null){
            instance = new UserDataHandler();
        }

    }

    public void initialise(){


    }

}
