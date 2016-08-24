package com.gulpp.android.gulppapp.model;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenardgeorge on 02/02/16.
 */
public class ComboList extends ArrayList<Parcelable> {

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public List<Menu> getComboList() {
        return comboList;
    }

    public void setComboList(List<Menu> comboList) {
        this.comboList = comboList;
    }

    private String comboType ;
    private List<Menu> comboList ;

}
