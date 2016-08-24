package com.gulpp.android.gulppapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gulpp.android.gulppapp.activity.MainActivity;
import com.gulpp.android.gulppapp.activity.MenuPageActivity;
import com.gulpp.android.gulppapp.activity.SubscriptionActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goToActivity(MainActivity.class);

    }



}
