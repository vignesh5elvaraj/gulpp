package com.gulpp.android.gulppapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gulpp.android.gulppapp.activity.MenuPageActivity;
import com.gulpp.android.gulppapp.activity.SubscribedListActivity;
import com.gulpp.android.gulppapp.handlers.ParseManager;
import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.gulpp.android.gulppapp.activity.CartActivity;
import com.gulpp.android.gulppapp.activity.LoginActivity;
import com.gulpp.android.gulppapp.activity.UserProfileActivity;
import com.gulpp.android.gulppapp.model.UserData;
import com.gulpp.android.gulppapp.singletons.PlateItemsList;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by lenardgeorge on 25/01/16.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "BaseActivity" ;
    ParseManager parseManager;
    ProgressDialog pd;
    PlateItemsList plateItemsListInstance;


    Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intializeParseHandler() ;
        global = (Global) getApplicationContext();
        pd = new ProgressDialog(BaseActivity.this);
        pd.setMessage("loading");

    }

    /**
     * START ACTIVITY METHODS
     */


    private void intializeParseHandler() {
        parseManager = new ParseManager();
    }

    public void showSnackBar(View parentLayout , String message)
    {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                //.setAction(R.string.snackbar_action, myOnClickListener)
                .show(); // Don’t forget to show!

    }

    public ParseManager getParseHandler(){
        return this.parseManager;
    }

    public void setUserLoginFlag(Boolean value)
    {
        android.content.SharedPreferences.Editor editor = getGlobal().getMySharedPreferences().edit();
        editor.putBoolean(Constant.IS_USER_LOGGED_IN,value);
        editor.commit();
    }

    public void showProgressDialog(){
        pd.show();

    }

    public void hideProgressDialog(){
        pd.hide();

    }


    /**
     * LOGIN METHODS
     */

    public void validateUserSignIn(int type) {

        Log.i(TAG_MAIN, "inside validateUserSignIn");

        if (!isUserSignedIn()) {
            Log.i(TAG_MAIN, "User not logged in");
            switch (type) {
                case Constant.LOGIN_USER_FOR_MENUPAGE:
                    loginUserForMenuPage();
                    break;
                case Constant.LOGIN_USER_FOR_PROFILE:
                    loginUserForProfile();
                    break;
                case Constant.LOGIN_USER_FOR_CART:
                    loginUserForCart();
                    break;
                case Constant.LOGIN_USER_FOR_SUBSCRIPTION:
                    loginUserForSubscription();
                    break;
            }
        } else {
            Log.i(TAG_MAIN, "User logged in");
            switch (type) {
                case Constant.LOGIN_USER_FOR_PROFILE:
                    goToActivity(UserProfileActivity.class);
                    break;
                case Constant.LOGIN_USER_FOR_CART:
                    goToActivity(CartActivity.class);
                    break;
                case Constant.LOGIN_USER_FOR_SUBSCRIPTION:
                    goToActivity(SubscribedListActivity.class);
                    break;
            }

        }
    }

    public void loginUserForMenuPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("value",Constant.LOGIN_INTENT_FOR_MENUPAGE);
        startActivityForResult(intent, Constant.LOGIN_USER_FOR_MENUPAGE);
    }


    public void loginUserForProfile(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("value",Constant.LOGIN_INTENT_FOR_PROFILE);
        startActivityForResult(intent, Constant.LOGIN_USER_FOR_PROFILE);
    }

    public void loginUserForCart(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("value",Constant.LOGIN_INTENT_FOR_CART);
        startActivityForResult(intent, Constant.LOGIN_USER_FOR_CART);
    }

    public void loginUserForSubscription(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("value",Constant.LOGIN_INTENT_FOR_SUBSCRIPTION);
        startActivityForResult(intent, Constant.LOGIN_USER_FOR_SUBSCRIPTION);
    }

    /**
     * START ACTIVITY METHODS
     */

    public void goToActivity(Class activityClass){
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void gotoActivity(Activity fromActivity , Class toActivity){
        Intent intent = new Intent(this, toActivity);
        startActivity(intent);
        killActivity(fromActivity);
    }

    public void goToActivityWithIntent(Class activity, String stringValue){
        Intent intent = new Intent(this , activity);
        intent.putExtra("value",stringValue);
        startActivity(intent);

    }

    public void goToActivityWithIntent(Activity fromActivity , Class activity, String stringValue ){
        Intent intent = new Intent(this , activity);
        intent.putExtra("value",stringValue);
        startActivity(intent);
        killActivity(fromActivity);

    }

    public void killActivity(Activity activity){
        Log.i("","Activity killed");
        activity.finish();
    }




    public static Boolean isUserSignedIn(){

            if(ParseUser.getCurrentUser() == null){
                return false ;
            }
            else
                return true ;

    }

    public Global getGlobal(){
        return global;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Constant.LOGIN_USER_FOR_MENUPAGE) {
            goToActivity(MenuPageActivity.class);
        }
        if(resultCode==Constant.LOGIN_USER_FOR_PROFILE) {
            goToActivity(UserProfileActivity.class);
        }
        if(resultCode==Constant.LOGIN_USER_FOR_CART){
            goToActivity(CartActivity.class);
        }
        if (resultCode==Constant.LOGIN_USER_FOR_SUBSCRIPTION){
            goToActivity(SubscribedListActivity.class);
        }
    }


}
