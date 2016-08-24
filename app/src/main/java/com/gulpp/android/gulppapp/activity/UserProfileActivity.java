package com.gulpp.android.gulppapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.fragments.MenuPageFragment;
import com.gulpp.android.gulppapp.fragments.UserInfoFragment;
import com.gulpp.android.gulppapp.fragments.UserOrderHistoryFragment;
import com.gulpp.android.gulppapp.fragments.UserPersonalDetails;
import com.gulpp.android.gulppapp.interfaces.UserLoginListener;
import com.gulpp.android.gulppapp.model.OrderHistory;
import com.gulpp.android.gulppapp.model.UserData;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserProfileActivity extends BaseActivity
        implements AppBarLayout.OnOffsetChangedListener , UserInfoFragment.OnFragmentInteractionListener , UserOrderHistoryFragment.OnFragmentInteractionListener {
    private UserLoginListener userLoginListener ;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private static final String TAG = "UserProfileActivity";
    private boolean mIsAvatarShown = true;

    private String[] tabTitles = new String[] {"Profile Info" , "Order History"};

    private LinearLayout loadingLayout ;

    private TextView userName  ;
    private TextView userEmail ;

    private List<OrderHistory> orderHistoryList ;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initialiseUserCredentials();
        initialiseOrderHistoryData();
    }

    private void initialiseViews(){

        loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.GONE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initialiseOrderHistoryData() {

        orderHistoryList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.whereEqualTo("userId", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    setupOrderHistoryItems(objects);
                } else {
                    Log.i(TAG, e.getMessage());
                }
            }
        });

    }

    private void initialiseUserCredentials() {

        userName = (TextView) findViewById(R.id.userProfile_name);
        userEmail = (TextView) findViewById(R.id.userProfile_email);

        if ( ParseUser.getCurrentUser() != null ) {
            userName.setText(ParseUser.getCurrentUser().getUsername());
            userEmail.setText(ParseUser.getCurrentUser().getEmail());
        } else {
            // show the signup or login screen
            System.out.println("USER NOT PRESENT");
        }

    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, UserProfileActivity.class));
    }

    private void setupOrderHistoryItems(List<ParseObject> objects) {

        Log.i(TAG, "inside setupOrderHistory");
        if(objects.size() == 0){
            Log.i(TAG, "SIZE IS ZERO");
        }
        else {
            Log.i(TAG, "SIZE IS " + objects.size());
        }

       // List<String> sampleList = Arrays.asList("Chicken Curry Combo 1", "Fish Curry Combo 1", "Biriyani Combo 1");

        for (int i=0 ; i<objects.size() ; i++){
            OrderHistory orderHistoryItem = new OrderHistory();
            orderHistoryItem.setOrderDate(getOrderDate(objects.get(i).getCreatedAt()));
            orderHistoryItem.setOrderItems((ArrayList) objects.get(i).getList("order_item_list"));
            printList(orderHistoryItem);
            orderHistoryList.add(orderHistoryItem);
        }
        initialiseViews();
    }

    public String getOrderDate(Date date){

        String dateString = new String();
        String month      = new String();
        int    day        = 0;

        switch (date.getMonth()){
            case 0:  month= "JAN" ; break;
            case 1:  month= "FEB" ; break;
            case 2:  month= "MAR" ; break;
            case 3:  month= "APR" ; break;
            case 4:  month= "MAY" ; break;
            case 5:  month= "JUN" ; break;
            case 6:  month= "JUL" ; break;
            case 7:  month= "AUG" ; break;
            case 8:  month= "SEPT"; break;
            case 9:  month= "OCT" ; break;
            case 10: month= "NOV" ; break;
            case 11: month= "DEC" ; break;
        }

        day = date.getDate();
        dateString = month + " " + day + ", " + Calendar.getInstance().get(Calendar.YEAR);

        return dateString ;
    }


    public void printList(OrderHistory orderHistoryItem){

        Log.i(TAG, "Item date  : " + orderHistoryItem.getOrderDate() );
        Log.i(TAG, "Item names : " + orderHistoryItem.getOrderItems());
        Log.i(TAG, "Item List size : " + orderHistoryList.size());

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
           // mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

        }
    }



    @Override
    public void onLoggingOut() {
        ParseUser.logOut();
        gotoActivity(this, MenuPageActivity.class);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class TabsAdapter extends FragmentPagerAdapter {
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0: return UserInfoFragment.newInstance(ParseUser.getCurrentUser().getNumber("phone") ,
                                                            ParseUser.getCurrentUser().getString("officeName"));
                case 1: return UserOrderHistoryFragment.newInstance(orderHistoryList);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
