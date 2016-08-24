package com.gulpp.android.gulppapp.model;

import java.util.List;

/**
 * Created by lenardgeorge on 01/02/16.
 */
public class Menu  {

    public List<String> getMenuItems() {return menuItems;}

    public void setMenuItems(List<String> menuItems) {this.menuItems = menuItems;}

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuId() {return menuId;}

    public void setMenuId(String menuId) {this.menuId = menuId;}

    public void increaseMenuQty(){ ++this.menuQty ;}

    public void decreaseMenuQty(){ --this.menuQty ; }

    public int getMenuQty(){ return  this.menuQty;}

    public String getMenuImageURL() {
        return menuImageURL;
    }

    public void setMenuImageURL(String menuImageURL) {
        this.menuImageURL = menuImageURL;
    }


    private List<String> menuItems;
    private String menuName;
    private String menuType ;
    private String menuPrice;
    private String menuId;
    private String menuImageURL;
    private int    menuQty = 1;

}
