package com.gulpp.android.gulppapp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class  CheckoutActivity extends BaseActivity {

    private static String TAG_MAIN = "CheckoutActivity" ;
    private static String TAG_PARSE = "PARSE_HANDLER" ;

    TextView checkoutPage_title ;
    TextView checkoutPage_body ;

    LinearLayout gotoSubscriptionLayout ;

    Button openMenuPageBtn ;
    Button openSubscriptionPageBtn ;

    String transactionID = "sss" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        createNewTransaction();
        clearItemsFromCart();
        Log.i(TAG_MAIN, "inside activity");
        initializeViews();
    }


    private void initializeViews() {

        gotoSubscriptionLayout =(LinearLayout)findViewById(R.id.goToSubscriptionLayout);
        openSubscriptionPageBtn = (Button)findViewById(R.id.checkoutAct_goToSubscription_Btn);
        openSubscriptionPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CheckoutActivity.this , SubscriptionActivity.class);
            }
        });
        checkoutPage_title = (TextView) findViewById(R.id.textView_title);
        checkoutPage_body = (TextView) findViewById(R.id.textView_body);
        openMenuPageBtn = (Button)findViewById(R.id.checkoutAct_goToMenu_Btn);
        openMenuPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MenuPageActivity.class);
            }
        });

        Log.i(TAG_MAIN, "inside initialise");

        if(getIntent().getStringExtra("value").equals(Constant.CHECKOUT_TYPE_CART)){
            Log.i(TAG_MAIN,Constant.CHECKOUT_TYPE_CART);
            setupViews(Constant.CHECKOUT_TITLE_CART , Constant.CHECKOUT_BODY_CART);
        }
        else if (getIntent().getStringExtra("value").equals(Constant.CHECKOUT_TYPE_SUBSCRIPTION)){
            Log.i(TAG_MAIN,Constant.CHECKOUT_TYPE_SUBSCRIPTION);
            gotoSubscriptionLayout.setVisibility(View.GONE);
            setupViews(Constant.CHECKOUT_TITLE_SUBSCRIPTION, Constant.CHECKOUT_BODY_SUBSCRIPTION);
        }

    }

    private void setupViews(String title , String body){
        checkoutPage_title.setText(title);
        checkoutPage_body.setText(body);
    }

    /**
     *PARSE METHODS
     */

    private void createNewTransaction(){

        //create a transaction object
        ParseObject transactionObject = new ParseObject("Transaction");

        for (int i= 0 ; i<PlateItemListHandler.getPlateItemListCount() ; i++)
        transactionObject.addAllUnique("order_item_list", Arrays.asList(getItemNameAndQty(i)));

        transactionObject.put("comments", "ORDER PLACED");
        transactionObject.put("userId", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));

        transactionObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG_PARSE, "Transaction Created Successfully");
                } else
                    Log.i(TAG_PARSE, e.getMessage());
            }
        });
    }


    private void clearItemsFromCart() {
        PlateItemListHandler.clearPlateItems();
    }

    private String getItemNameAndQty(int i){
        String itemName = PlateItemListHandler.getPlateItem(i).getMenuName();
        int itemQty = PlateItemListHandler.getPlateItem(i).getMenuQty();
        String string = itemQty + "*" + itemName;
        return string ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){

        showSnackBar(findViewById(R.id.parentCoordinatorLayout), "OOPS, THATS NOT ALLOWED ! TAP ON ORDER MORE TO CONTINUE" );
    }

}



