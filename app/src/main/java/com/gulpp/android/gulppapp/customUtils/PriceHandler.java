package com.gulpp.android.gulppapp.customUtils;

/**
 * Created by lenardgeorge on 14/04/16.
 */
public  class PriceHandler {

    public static String addCurrencySymbol(String price){

        String priceString =  "Rs. "+ price;
        return priceString;
    }
}
