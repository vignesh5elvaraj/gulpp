package com.gulpp.android.gulppapp.singletons;

import android.content.Context;

import com.gulpp.android.gulppapp.model.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenardgeorge on 18/03/16.
 */
public class PlateItemsList {

       private List<Menu> plateItemsList ;
       private int plateItemsCount  ;

       public PlateItemsList(){
           this.plateItemsList = new ArrayList<>();
           this.plateItemsCount = 0 ;
       }


       public List<Menu> getPlateItemsList(){
           return plateItemsList;
       }

       public int getItemCount(){
           return plateItemsCount;
       }

       public void setItemCount(int value){
           plateItemsCount = value ;
       }

       public void setCountToZero(){
           plateItemsCount = 0;
       }



}
