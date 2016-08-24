package com.gulpp.android.gulppapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.customUtils.PriceHandler;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.adapters.LocationSpinnerAdapter;
import com.gulpp.android.gulppapp.customView.ExpandedListView;
import com.gulpp.android.gulppapp.handlers.SubscriptionListHandler;
import com.gulpp.android.gulppapp.model.SubscribedItem;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class SubscribedListActivity extends BaseActivity {

    private static final String TAG_PARSE = "PARSE" ;
    List<SubscribedItem> subscriptionList;
    private Spinner locationSpinner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_list);
        initialiseSubscribedList();
        initialiseToolBar();
        initialiseFAB();
        initialiseListView();
        initialiseLocation();
        initialiseBillAmount();

    }

    private void initialiseLocation() {
        List<String> locations = getITParkNames();
        locationSpinner = (Spinner)findViewById(R.id.card_deliveryLocationSpinner);
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , locations);
        LocationSpinnerAdapter locationSpinnerAdapter = new LocationSpinnerAdapter(getApplicationContext() , R.layout.layout_location_spinner_item , locations);
        locationSpinner.setAdapter(locationSpinnerAdapter);
        locationSpinner.setSelection(0);
        //locationSpinner.setOnItemSelectedListener(this);
    }

    private void initialiseBillAmount() {

        TextView totalBill = (TextView)findViewById(R.id.cart_totalPrice);
        totalBill.setText(PriceHandler.addCurrencySymbol(String.valueOf(SubscriptionListHandler.getTotalBillAmount())));
    }

    private void initialiseSubscribedList() {
        subscriptionList = SubscriptionListHandler.getSubscriptionList();
    }

    private void initialiseListView() {
        ExpandedListView listView = (ExpandedListView) findViewById(R.id.subscribed_ListView);
        SubscribedListAdapter subscribedListAdapter = new SubscribedListAdapter();
        listView.setAdapter(subscribedListAdapter);
    }

    private void initialiseFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeSubcribedList();
                goToActivityWithIntent(CheckoutActivity.class, Constant.CHECKOUT_TYPE_SUBSCRIPTION);
            }
        });
    }

    private void initialiseToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> getITParkNames(){
        final List<String> ITparks = new ArrayList<>();
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Parks");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    Log.i(TAG_PARSE, "IT parks retrieval success");
//                    for (int i = 0; i < objects.size(); i++)
//                        ITparks.add(objects.get(i).getString("parkName"));
//
//                } else {
//                    Log.i(TAG_PARSE, "IT parks retrieval Failure");
//                }
//            }
//        });
//       locationSpinnerAdapter.notifyDataSetChanged();
        ITparks.add("PARK A");
        ITparks.add("PARK B");
        ITparks.add("PARK C");

        return ITparks;
    }

    private void makeSubcribedList() {
        ParseObject subscriptionObject = new ParseObject("Subscription");
        List<String> menuIDList = new ArrayList<>();

        for(int i=0; i<5 ; i++){
            if(SubscriptionListHandler.getSubscribedItemFromList(0).getSubscribed_item().getMenuId() != null)
            {
                menuIDList.add(SubscriptionListHandler.getSubscribedItemFromList(0).getSubscribed_item().getMenuId());
            }
            else
                menuIDList.add("-");
        }

        subscriptionObject.put("mondayMenuID"   ,ParseObject.createWithoutData("Menu",menuIDList.get(0)));
        subscriptionObject.put("tuesdayMenuID"  ,ParseObject.createWithoutData("Menu",menuIDList.get(1)));
        subscriptionObject.put("wednesdayMenuID",ParseObject.createWithoutData("Menu",menuIDList.get(2)));
        subscriptionObject.put("thursdayMenuID" ,ParseObject.createWithoutData("Menu",menuIDList.get(3)));
        subscriptionObject.put("fridayMenuID"   ,ParseObject.createWithoutData("Menu",menuIDList.get(4)));
        subscriptionObject.put("userID",ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));

        subscriptionObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG_PARSE, "Transaction Created Successfully");
                } else
                    Log.i(TAG_PARSE, e.getMessage());
            }
        });

    }

    public class SubscribedListAdapter extends BaseAdapter{

        private TextView day_name ,
                         item_name ;

        private List<String> dayList =  new ArrayList<String>(){
            {
                add("MONDAY");
                add("TUESDAY");
                add("WEDNESDAY");
                add("THURSDAY");
                add("FRIDAY");
            }
        };

        @Override
        public int getCount() {
            return dayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.card_subscribed_item, null);
            day_name = (TextView) view.findViewById(R.id.subscribed_day);
            item_name = (TextView) view.findViewById(R.id.subscribed_item_name);

            day_name.setText(dayList.get(position));
            if(subscriptionList.get(position) !=null) {
                 item_name.setText(SubscriptionListHandler.getSubscribedItemFromList(position).getSubscribed_item().getMenuName());
            }else {
                item_name.setTextColor(getResources().getColorStateList(R.color.LightGreyColor));
                item_name.setText("No Item was selected");
            }
            return view;
        }
    }


}
