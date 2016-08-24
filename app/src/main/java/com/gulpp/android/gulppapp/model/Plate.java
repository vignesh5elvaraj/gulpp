package com.gulpp.android.gulppapp.model;

import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created by lenardgeorge on 17/02/16.
 */
public class Plate {

    public void setItems(List<Menu> items) {
        Items = items;
    }

    public List<Menu> getItems() {
        return Items;
    }

    List<Menu> Items ;

}
