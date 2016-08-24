package com.gulpp.android.gulppapp;

/**
 * Created by lenardgeorge on 22/01/16.
 */
public class Constant {

    public static String SHARED_PREF_KEY = "GulppAppPrefKey" ;
    public static String IS_USER_LOGGED_IN = "IsUserLoggedIn" ;

    public static final int LOGIN_USER_FOR_MENUPAGE = 0;
    public static final int LOGIN_USER_FOR_PROFILE  = 1;
    public static final int LOGIN_USER_FOR_CART     = 2;
    public static final int LOGIN_USER_FOR_SUBSCRIPTION = 3;

    public static final String LOGIN_INTENT_FOR_MENUPAGE  = "menu";
    public static final String LOGIN_INTENT_FOR_PROFILE = "profile";
    public static final String LOGIN_INTENT_FOR_CART    = "cart";
    public static final String LOGIN_INTENT_FOR_SUBSCRIPTION = "subscription";

    public static String CHECKOUT_TYPE_CART  = "CHECKOUT_CART" ;
    public static String CHECKOUT_TITLE_CART = "ORDER COMPLETE";
    public static String CHECKOUT_BODY_CART  = "Your food will be delivered shortly";

    public static String LOGIN_INTRO_FRAGMENT_TAG = "login_intro_fragment" ;
    public static String SIGN_UP_FRAGMENT_TAG     = "sign_up_fragment";
    public static String SIGN_IN_FRAGMENT_TAG     = "sign_in_fragment";

    public static String CHECKOUT_TYPE_SUBSCRIPTION  = "CHECKOUT_SUBSCRIPTION";
    public static String CHECKOUT_TITLE_SUBSCRIPTION = "SUBSCRIPTION COMPLETE";
    public static String CHECKOUT_BODY_SUBSCRIPTION  = "Your food will be delivered on the subscribed days";

    /**
     * ERROR MESSAGES
     */

    //SIGN UP FRAGMENT
    public static String PASSWORD_MISMATCH = "Passwords dont match !";

}
