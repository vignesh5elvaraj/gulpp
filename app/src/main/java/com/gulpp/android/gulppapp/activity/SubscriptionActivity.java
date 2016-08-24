package com.gulpp.android.gulppapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.fragments.SubscriptionFragment;
import com.gulpp.android.gulppapp.handlers.SubscriptionListHandler;
import com.gulpp.android.gulppapp.model.SubscribedItem;
import com.gulpp.android.gulppapp.model.SubscriptionMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class SubscriptionActivity extends BaseActivity implements SubscriptionFragment.OnFragmentInteractionListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    private static String TAG_PARSE = "PARSE_HANDLER";
    private static String TAG_MAIN = "MenuPageActivity";

    LinearLayout loadingLayout ;
    android.support.v7.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager mViewPager;

    List<List<SubscriptionMenu>> subscriptionList;

    private List<String> dayListFullName = new ArrayList<String>() {
        {
            add("monday");
            add("tuesday");
            add("wednesday");
            add("thursday");
            add("friday");
        }
    };

    private List<String> dayList = new ArrayList<String>() {
        {
            add("Mon");
            add("Tue");
            add("Wed");
            add("Thur");
            add("Fri");
        }
    };

     List<SubscriptionMenu> monday_menuList;
     List<SubscriptionMenu> tuesday_menuList;
     List<SubscriptionMenu> wednesday_menuList;
     List<SubscriptionMenu> thursday_menuList;
     List<SubscriptionMenu> friday_menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkIfUserHasSubscribed() == false) {
            fetchMenusForAllDays();
            setContentView(R.layout.activity_subscription);
        }
        //else
           // setContentView();

    }

    private void initialiseViews() {

        setupViewPager();
        setupTabLayout();
        setupToolBar();
    }

    private void setupViewPager() {

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    private void setupTabLayout() {

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupToolBar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My plate");
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private List<List<SubscriptionMenu>> getSubscriptionList() {

        if(subscriptionList == null)
         subscriptionList = new ArrayList<List<SubscriptionMenu>>();

        return subscriptionList;
    }


    public  List<SubscriptionMenu> getMenuListForDay(int day) {

        switch (day) {
            case 0:
                Log.i(TAG_MAIN, " return mon");
                if (monday_menuList == null)
                    monday_menuList = new ArrayList<>();
                return monday_menuList;

            case 1:
                Log.i(TAG_MAIN," return tues");
                if (tuesday_menuList == null)
                    tuesday_menuList = new ArrayList<>();
                return tuesday_menuList;

            case 2:
                Log.i(TAG_MAIN," return wed");
                if (wednesday_menuList == null)
                    wednesday_menuList = new ArrayList<>();
                return wednesday_menuList;

            case 3:
                Log.i(TAG_MAIN," return thur");
                if (thursday_menuList == null)
                    thursday_menuList = new ArrayList<>();
                return  thursday_menuList;

            case 4:
                Log.i(TAG_MAIN," return fri");
                if (friday_menuList == null)
                    friday_menuList = new ArrayList<>();
                return friday_menuList;
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_subscribed_list) {
            showSubscriptionList();
            return true;
        }

        if(id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSubscriptionList() {
        validateUserSignIn(Constant.LOGIN_USER_FOR_SUBSCRIPTION);
    }


    @Override
    public void addItemToSubscription(SubscribedItem subscribedItem , int pos) {
        SubscriptionListHandler.addItemToSubscription(pos,subscribedItem);
    }

    @Override
    public void removeItemFromSubscription( int pos) {
        SubscriptionListHandler.removeItemForDayFromList(pos);
    }

    private boolean checkIfUserHasSubscribed(){

//        Log.i(TAG_PARSE, "Inside fetch menus");
//        if(ParseUser.getCurrentUser().getBoolean("hasSubscribed")){
//            return true;
//        }
        return false;

    }

    private void fetchMenusForAllDays() {

        Log.i(TAG_PARSE, "Inside fetch menus");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
        query.include("Type");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i(TAG_PARSE, "fetch subscription combos retrieval success");

                    for (int i = 0; i < objects.size(); i++) {
                        try {
                            setupSubcriptionList(objects.get(i));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                    addToSubscriptionList();
                    initialiseViews();
                    hideLoadingView();

                } else {
                    hideLoadingView();
                    Log.i(TAG_PARSE, "fetch subscription combos retrieval Failure");
                    Log.i(TAG_PARSE, String.valueOf(e.getCode()));
                }
            }
        });

    }

    private void addToSubscriptionList() {

        for (int i = 0; i < dayList.size(); i++) {
            getSubscriptionList().add(i, getMenuListForDay(i));
        }
    }
    private void setupSubcriptionList(ParseObject parseObject) throws ParseException {

        for(int i= 0 ; i < dayList.size() ; i ++){
            if(parseObject.getString("day").equals(dayListFullName.get(i))){

                SubscriptionMenu subscriptionMenu = new com.gulpp.android.gulppapp.model.SubscriptionMenu();
                subscriptionMenu.setMenuItems(getItemsForMenu(parseObject));
                subscriptionMenu.setMenuType(getTypeForMenu(parseObject));
                subscriptionMenu.setMenuName(parseObject.getString("Name"));
                subscriptionMenu.setMenuPrice(String.valueOf(parseObject.getNumber("Price")));
                subscriptionMenu.setMenuId(parseObject.getObjectId());

                 Log.i(TAG_PARSE, "Adding item " + parseObject.getString("Name") + " for day " + dayListFullName.get(i));
                 getMenuListForDay(i).add(subscriptionMenu);
            }
         }
        }

    private List<String> getItemsForMenu(ParseObject object) throws ParseException {

        ParseRelation itemRelations = object.getRelation("Items");
        List<ParseObject> itemList =  itemRelations.getQuery().find();

        List<String> menuItems = new ArrayList<>();

        for (int i=0 ; i<itemList.size() ; i++) {
            menuItems.add(itemList.get(i).getString("itemName"));
            Log.i(TAG_PARSE, menuItems.get(i));
        }
        return menuItems;
    }

    private String getTypeForMenu(ParseObject object) {

        ParseObject type ;
        type = object.getParseObject("Type");
        return type.getString("TypeName");
    }

    private void hideLoadingView(){
        loadingLayout = (LinearLayout) findViewById(R.id.loadingView);
        loadingLayout.setVisibility(View.GONE);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<com.gulpp.android.gulppapp.model.Menu> menuListForDay ;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubscriptionFragment.newInstance(dayList.get(position), getSubscriptionList().get(position) , position);
        }

        @Override
        public int getCount() {
            return dayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
          return dayList.get(position) ;
        }

    }


}
