package com.gulpp.android.gulppapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenardgeorge on 24/03/16.
 */
public class OrderHistory implements Parcelable{

    String orderDate ;
    List<String> orderItems ;

    public OrderHistory() {

    }


    public List<String> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<String> orderItems) {
        this.orderItems = orderItems;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public OrderHistory(Parcel in) {
        if (in.readByte() == 0x01) {
            orderItems = new ArrayList<String>();
            in.readList(orderItems, String.class.getClassLoader());
        } else {
            orderItems = null;
        }
        orderDate = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (orderItems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(orderItems);
        }
        dest.writeString(orderDate);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OrderHistory> CREATOR = new Parcelable.Creator<OrderHistory>() {
        @Override
        public OrderHistory createFromParcel(Parcel in) {
            return new OrderHistory(in);
        }

        @Override
        public OrderHistory[] newArray(int size) {
            return new OrderHistory[size];
        }
    };


}
