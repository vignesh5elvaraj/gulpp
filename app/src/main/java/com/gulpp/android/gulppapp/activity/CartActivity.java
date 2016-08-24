package com.gulpp.android.gulppapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.customUtils.PriceHandler;
import com.gulpp.android.gulppapp.adapters.LocationSpinnerAdapter;
import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.customView.CustomListView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements  AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private static String TAG_MAIN = "CartActivity";
    private static String TAG_PARSE = "PARSE_HANDLER";
    private CustomListView itemListView;
    private ItemListAdapter itemListAdapter;
    private NestedScrollView cartScrollView;
    private LinearLayout cartEmptyListView;
    private Spinner locationSpinner ;
    private LocationSpinnerAdapter locationSpinnerAdapter ;
    private FloatingActionButton checkout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart_items);
        initializeViews();
    }

    private boolean checkIfItemsPresent() {

        if (PlateItemListHandler.getPlateItemCount() > 0) {
            showCartWithItems();
            return true;
        } else {
            showEmptyCart();
            return false;
        }

    }


    private void initializeViews() {
        setupFAB();
        setupToolbar();
        setupItemsList();
        setupDeliveryLocation();
        setupTotalPrice();
        checkIfItemsPresent();
    }

    private void setupTotalPrice() {

        TextView totalBill = (TextView)findViewById(R.id.cart_totalPrice);
        totalBill.setText(PriceHandler.addCurrencySymbol(String.valueOf(PlateItemListHandler.getTotalBillAmount())));

    }

    private void setupDeliveryLocation() {

        List<String> locations = getITParkNames();
        locationSpinner = (Spinner)findViewById(R.id.cart_deliveryLocationSpinner);
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , locations);
        LocationSpinnerAdapter locationSpinnerAdapter = new LocationSpinnerAdapter(getApplicationContext() , R.layout.layout_location_spinner_item , locations);
        locationSpinner.setAdapter(locationSpinnerAdapter);
        locationSpinner.setSelection(0);
        locationSpinner.setOnItemSelectedListener(this);


    }


    private void setupFAB() {

        checkout_btn = (FloatingActionButton) findViewById(R.id.cartActivity_checkout_btn);
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // goToActivityWithIntent(CheckoutActivity.class, Constant.CHECKOUT_BODY_CART);
                 goToActivityWithIntent(CartActivity.this,CheckoutActivity.class , Constant.CHECKOUT_BODY_CART);
            }
        });
    }


    private void setupItemsList() {

        cartScrollView = (NestedScrollView) findViewById(R.id.cart_nestedScrollView);
        cartEmptyListView = (LinearLayout) findViewById(R.id.cart_emptyCartList);
        itemListView = (CustomListView) findViewById(R.id.cart_itemList);
        itemListAdapter = new ItemListAdapter();
        itemListView.setAdapter(itemListAdapter);

        Button clearPlateBtn = (Button) findViewById(R.id.cart_ClearBtn);
        clearPlateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmptyCart();
                if (PlateItemListHandler.clearPlateItems()) {
                    itemListAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.cartToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My plate");
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void showEmptyCart() {

        checkout_btn.setVisibility(View.GONE);
        cartScrollView.setVisibility(View.GONE);
        cartEmptyListView.setVisibility(View.VISIBLE);
    }

    private void showCartWithItems() {

        checkout_btn.setVisibility(View.VISIBLE);
        cartScrollView.setVisibility(View.VISIBLE);
        cartEmptyListView.setVisibility(View.GONE);

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
//
//       locationSpinnerAdapter.notifyDataSetChanged();
        ITparks.add("PARK A");
        ITparks.add("PARK B");
        ITparks.add("PARK C");

        return ITparks;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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

        if (id == android.R.id.home) {
            goToActivity(MenuPageActivity.class);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class ItemListAdapter extends BaseAdapter {

        Holder itemHolder;


        @Override
        public int getCount() {
            return PlateItemListHandler.getPlateItemList().size();
        }

        public ItemListAdapter() {
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView itemCount;
            TextView itemName;
            TextView itemPrice;
            Button addItem;
            Button minusItem;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            itemHolder = new Holder();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.layout_cart_item_new, null);
            initialiseViews(itemHolder, rowView);
            setupViews(position, itemHolder);

            return rowView;
        }

        private void setupViews(final int position, final Holder holder) {

            holder.itemName.setText(PlateItemListHandler.getPlateItemList().get(position).getMenuName());
            holder.itemPrice.setText(PriceHandler.addCurrencySymbol(PlateItemListHandler.getPlateItemList().get(position).getMenuPrice()));
            holder.itemCount.setText(String.valueOf(PlateItemListHandler.getPlateItemList().get(position).getMenuQty()));
            holder.addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG_MAIN, "increase item number");
                    increaseItemQty(position, holder);
                }
            });

            holder.minusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG_MAIN, "decrease item number");
                    decreaseItemQty(position, holder);
                }
            });
        }

        private void decreaseItemQty(int position, Holder holder) {

            com.gulpp.android.gulppapp.model.Menu item = PlateItemListHandler.getPlateItem(position);
            PlateItemListHandler.reduceItemQtyInList(item);

            if (PlateItemListHandler.isItemPresentInList(item) && PlateItemListHandler.getQtyForItem(item) > 0) {
                holder.itemCount.setText(String.valueOf(PlateItemListHandler.getQtyForItem(item)));
            } else if (PlateItemListHandler.getPlateItemCount() == 0) {
                showEmptyCart();
            }
            setupTotalPrice();
            notifyDataSetChanged();

        }

        private void increaseItemQty(int position, Holder holder) {

            com.gulpp.android.gulppapp.model.Menu item = PlateItemListHandler.getPlateItem(position);
            PlateItemListHandler.increaseItemQtyInList(item);
            setupTotalPrice();
            Log.i(TAG_MAIN, "item count number after inc " + PlateItemListHandler.getPlateItemCount());
            holder.itemCount.setText(String.valueOf(PlateItemListHandler.getPlateItem(position).getMenuQty()));
        }

        private void initialiseViews(Holder holder, View view) {

            holder.itemName = (TextView) view.findViewById(R.id.cartItem_name);
            holder.itemPrice = (TextView) view.findViewById(R.id.cartItem_price);
            holder.itemCount = (TextView) view.findViewById(R.id.cartItem_count);
            holder.addItem = (Button) view.findViewById(R.id.cartItem_plus);
            holder.minusItem = (Button) view.findViewById(R.id.cartItem_minus);


        }
    }

}
