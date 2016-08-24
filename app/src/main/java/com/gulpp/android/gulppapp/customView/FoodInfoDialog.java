package com.gulpp.android.gulppapp.customView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.activity.CartActivity;
import com.gulpp.android.gulppapp.interfaces.DialogOnClickListener;
import com.gulpp.android.gulppapp.model.Menu;

/**
 * Created by lenardgeorge on 29/01/16.
 */
public class FoodInfoDialog extends Dialog implements View.OnClickListener {

    DialogOnClickListener dialogOnClickListener ;

    Context context ;

    Menu menuData ;

    Button menuDialog_goBackBtn;
    Button menuDialog_CheckOutBtn;

    public FoodInfoDialog(Context context) {
        super(context);
        this.context =context;
        dialogOnClickListener = (DialogOnClickListener) context ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_add_to_cart_dialog);
        initialiseViews();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void initialiseViews() {

        menuDialog_CheckOutBtn = (Button) findViewById(R.id.menuDialog_CheckOutButton);
        menuDialog_goBackBtn = (Button) findViewById(R.id.menuDialog_goBackButton);
        menuDialog_CheckOutBtn.setOnClickListener(this);
        menuDialog_goBackBtn.setOnClickListener(this);

        setupViews();
    }

    private void setupViews() {


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.menuDialog_CheckOutButton :
                openCartActivity();
                break;
            case R.id.menuDialog_goBackButton :
                this.dismiss();
                break;
        }


    }

    private void openCartActivity() {
        dialogOnClickListener.onDialogButtonClick();
        dismiss();

    }
}
