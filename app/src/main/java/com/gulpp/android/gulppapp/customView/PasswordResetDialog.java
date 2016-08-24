package com.gulpp.android.gulppapp.customView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.gulpp.android.gulppapp.R;

/**
 * Created by lenardgeorge on 15/04/16.
 */
public class PasswordResetDialog extends Dialog {

    Context context ;
    Button openEmailOptionsBtn ;


    public PasswordResetDialog(Context context) {
        super(context);
        this.context = context ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_verify_email_dialog);
        intialiseViews();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void intialiseViews() {

        openEmailOptionsBtn = (Button) findViewById(R.id.showEmailOptionsButton);
        openEmailOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }



}
