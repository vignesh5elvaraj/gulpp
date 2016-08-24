package com.gulpp.android.gulppapp.singletons;

import com.gulpp.android.gulppapp.model.SubscribedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenardgeorge on 18/03/16.
 */
public class SubscriptionList {
    private static SubscriptionList instance ;
    private static List<SubscribedItem> subscribedItemsList;

    public SubscriptionList(){
        subscribedItemsList = new ArrayList() {
            {
                add(null);
                add(null);
                add(null);
                add(null);
                add(null);
            }
        };
    }

    public static SubscriptionList getSubscribedItemListInstance() {
        if (instance == null) {
            return instance = new SubscriptionList();
        }
        return  instance;
    }

    public static List<SubscribedItem> getSubscriptionList(){
        return subscribedItemsList;
    }
}
