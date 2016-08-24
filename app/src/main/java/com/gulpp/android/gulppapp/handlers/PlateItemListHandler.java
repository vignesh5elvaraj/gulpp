package com.gulpp.android.gulppapp.handlers;

import android.util.Log;

import com.gulpp.android.gulppapp.model.Menu;
import com.gulpp.android.gulppapp.singletons.PlateItemsList;

import java.util.List;

/**
 * Created by lenardgeorge on 18/03/16.
 */
public class PlateItemListHandler {


    private static final String TAG_MAIN = "PlateItemListHandler";
    private static PlateItemListHandler instance ;
    private static PlateItemsList plateItemsList;

    public PlateItemListHandler(){
        this.plateItemsList = new PlateItemsList();
    }

    public static void initializeInstance(){
        if (instance == null){
            instance = new PlateItemListHandler();
        }
    }

    public static PlateItemListHandler getInstance() {
        return instance;
    }


    /**
     * Private methods
     */

    private static void removeItemFromList(int location){
        Log.i(TAG_MAIN, "item " + getPlateItemList().get(location).getMenuName() + " removed.");
        getPlateItemList().remove(location);
        Log.i(TAG_MAIN, "Current item list size " + getPlateItemList().size());
    }

    private static int findItemLocationInList(Menu selectedItem) {

        int itemLocation = 0;

            for (int i=0 ;i <getPlateItemList().size() ; i++)
                if(getPlateItemList().get(i).getMenuId().equals(selectedItem.getMenuId()))
                    itemLocation = i;

        return itemLocation;
    }

    private static void incrementItemCount(){

        int count = getPlateItemCount() + 1;
        setItemCount(count);
    }

    private static void decreamentItemCount(){

        if(getPlateItemCount() >= 0){
            int count = getPlateItemCount() - 1;
            setItemCount(count);
        }
        else
            plateItemsList.setCountToZero();

    }


    /**
     * Public methods
     */

    public static List<Menu> getPlateItemList(){
        return plateItemsList.getPlateItemsList() ;
    }

    public static int getPlateItemListCount(){
       return plateItemsList.getPlateItemsList().size();
    }

    public static Menu getPlateItem(int pos){
        return plateItemsList.getPlateItemsList().get(pos);
    }

    public static boolean clearPlateItems() {

        if (plateItemsList.getPlateItemsList() != null) {
            plateItemsList.getPlateItemsList().clear();
            plateItemsList.setCountToZero();
            return true ;
        }
        else
            return false ;

    }

    public static int getPlateItemCount(){
        return plateItemsList.getItemCount();
    }

    public static void setItemCount(int count){
         plateItemsList.setItemCount(count);
    }

    public static void addItemToPlate(Menu selectedItem){

        PlateItemListHandler.incrementItemCount();
        Log.i(TAG_MAIN, "Plate count " + String.valueOf(PlateItemListHandler.getPlateItemCount()));

        if(isItemPresentInList(selectedItem)){
            int location = findItemLocationInList(selectedItem);
            Log.i(TAG_MAIN, "increasing the qty for" + getPlateItem(location).getMenuName());
            getPlateItem(location).increaseMenuQty();
        }
        else {
            Log.i(TAG_MAIN, "adding " + selectedItem.getMenuName() + " to plate");
            getPlateItemList().add(selectedItem);
        }

    }

    public static int getQtyForItem(Menu selectedItem) {

        if (isItemPresentInList(selectedItem)) {
            int location = findItemLocationInList(selectedItem);
            return getPlateItemList().get(location).getMenuQty();
        }
        else return 0;
    }
    public static void reduceItemQtyInList(Menu selectedItem) {

        int location = findItemLocationInList(selectedItem);
        decreamentItemCount();

        if (getPlateItemList().get(location).getMenuQty() > 1) {
            getPlateItemList().get(location).decreaseMenuQty();
            Log.i(TAG_MAIN, "decreasing qty for " + getPlateItemList().get(location).getMenuName());
        }
        else if (getPlateItemList().get(location).getMenuQty() == 1){
            Log.i(TAG_MAIN, "removing item " +   getPlateItemList().get(location).getMenuName());
            removeItemFromList(location);
        }
    }

    public static void increaseItemQtyInList(Menu selectedItem) {

        int location = findItemLocationInList(selectedItem);

        if (getPlateItem(location).getMenuQty() < 10) {
            incrementItemCount();
            getPlateItem(location).increaseMenuQty();
        }
    }

    public static boolean isItemPresentInList(Menu selectedItem){

        if (getPlateItemList().size() > 0)
         for (int i=0 ;i <getPlateItemList().size() ; i++)
            if(getPlateItemList().get(i).getMenuId().equals(selectedItem.getMenuId()))
                return true ;

        return false;
    }

    public static float getTotalBillAmount(){

        float totalPrice =0  ;

        if (PlateItemListHandler.getPlateItemList().size() > 0)
            for(int i=0 ; i< PlateItemListHandler.getPlateItemList().size() ;i++){
                int qty = PlateItemListHandler.getPlateItem(i).getMenuQty();
                float price = Float.parseFloat(PlateItemListHandler.getPlateItem(i).getMenuPrice());
                float totalPriceOfOneItem = price * qty;
                totalPrice = totalPrice + totalPriceOfOneItem ;
            }

        return totalPrice;
    }


}
