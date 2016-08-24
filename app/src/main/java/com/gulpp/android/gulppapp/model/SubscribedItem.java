package com.gulpp.android.gulppapp.model;

/**
 * Created by lenardgeorge on 07/03/16.
 */
public class SubscribedItem {

    private String day ;
    private SubscriptionMenu   subscribed_item ;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public SubscriptionMenu getSubscribed_item() {
        return subscribed_item;
    }

    public void setSubscribed_item(SubscriptionMenu subscribed_item) {
        this.subscribed_item = subscribed_item;
    }
}
