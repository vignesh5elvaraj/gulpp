package com.gulpp.android.gulppapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gulpp.android.gulppapp.R;

import java.util.List;

/**
 * Created by lenardgeorge on 09/04/16.
 */
public class LocationSpinnerAdapter extends ArrayAdapter<String> {

    private Context context ;
    private List<String> locationsList ;

    public LocationSpinnerAdapter(Context context, int resource ,List<String> locations) {
        super(context, resource , locations);
        this.context = context ;
        this.locationsList = locations ;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_location_spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.locationName);
        label.setText(locationsList.get(position));

        return row;
    }
}
