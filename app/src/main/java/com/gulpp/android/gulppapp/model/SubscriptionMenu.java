package com.gulpp.android.gulppapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public  class SubscriptionMenu implements Parcelable {

    private List<String> menuItems;
    private String menuName;
    private String menuType ;
    private String menuPrice;
    private String menuId;

    public SubscriptionMenu(){}

    public void setMenuItems(List<String> menuItems) {
        this.menuItems = menuItems;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


    public List<String> getMenuItems() {
        return menuItems;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public String getMenuType() {
        return menuType;
    }

    public String getMenuName() {
        return menuName;
    }





    public SubscriptionMenu(Parcel in) {
        if (in.readByte() == 0x01) {
            menuItems = new ArrayList<String>();
            in.readList(menuItems, String.class.getClassLoader());
        } else {
            menuItems = null;
        }
        menuName = in.readString();
        menuType = in.readString();
        menuPrice = in.readString();
        menuId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (menuItems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(menuItems);
        }
        dest.writeString(menuName);
        dest.writeString(menuType);
        dest.writeString(menuPrice);
        dest.writeString(menuId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SubscriptionMenu> CREATOR = new Parcelable.Creator<SubscriptionMenu>() {
        @Override
        public SubscriptionMenu createFromParcel(Parcel in) {
            return new SubscriptionMenu(in);
        }

        @Override
        public SubscriptionMenu[] newArray(int size) {
            return new SubscriptionMenu[size];
        }
    };
}