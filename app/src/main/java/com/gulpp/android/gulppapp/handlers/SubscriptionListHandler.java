package com.gulpp.android.gulppapp.handlers;

import com.gulpp.android.gulppapp.model.SubscribedItem;
import com.gulpp.android.gulppapp.model.SubscriptionMenu;
import com.gulpp.android.gulppapp.singletons.SubscriptionList;

import java.util.List;

/**
 * Created by lenardgeorge on 19/03/16.
 */
public class SubscriptionListHandler {

    private static final String TAG_MAIN = "SubscriptionHandler";
    private static SubscriptionListHandler instance ;
    private static SubscriptionList subscriptionList ;

    /**
     * INITIALIZE INSTANCE
     */

    public SubscriptionListHandler() {
        subscriptionList = SubscriptionList.getSubscribedItemListInstance();
    }

    public static void initializeInstance(){
        if (instance == null){
            instance = new SubscriptionListHandler();
        }
    }

    /**
     * PUBLIC METHODS
     */
     public static List<SubscribedItem> getSubscriptionList(){
         return subscriptionList.getSubscriptionList();
     }


     public static SubscribedItem getSubscribedItemFromList(int position){
         return subscriptionList.getSubscriptionList().get(position);
     }


     public static void addItemToSubscription(int day , SubscribedItem subscribedItem){
         if( subscriptionList.getSubscriptionList().get(day)== null)
             subscriptionList.getSubscriptionList().set(day, subscribedItem);
     }

     public static void removeItemForDayFromList(int day){
         subscriptionList.getSubscriptionList().set(day, null);
     }

     public static Boolean isSubscriptionListEmpty(){

         for (int i= 0 ; i<subscriptionList.getSubscriptionList().size() ; i++)
              if (subscriptionList.getSubscriptionList().get(i) !=null)
                  return false ;

         return true;
     }

    public static SubscribedItem getNewSubscribedItemForDay(String day , SubscriptionMenu item){
        SubscribedItem subscribedItem = new SubscribedItem();
        subscribedItem.setDay(day);
        subscribedItem.setSubscribed_item(item);
        return subscribedItem;
    }


    public static float getTotalBillAmount(){

        float totalPrice =0  ;

            for(int i=0 ; i< subscriptionList.getSubscriptionList().size() ;i++) {
                if (subscriptionList.getSubscriptionList().get(i) != null) {
                    float price =  Float.parseFloat(getSubscribedItemFromList(i).getSubscribed_item().getMenuPrice());
                    totalPrice = totalPrice + price;
                }
            }
        return totalPrice;
    }


//    public static void setSubscribedItemToDay(int day, SubscribedItem selectedItem){
//
//    }

}
