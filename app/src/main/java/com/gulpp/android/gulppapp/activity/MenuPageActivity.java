package com.gulpp.android.gulppapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.customUtils.PriceHandler;
import com.gulpp.android.gulppapp.customView.FoodInfoDialog;
import com.gulpp.android.gulppapp.handlers.ParseManager;
import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.gulpp.android.gulppapp.interfaces.DialogOnClickListener;
import com.gulpp.android.gulppapp.interfaces.UserLoginListener;
import com.gulpp.android.gulppapp.itemanimator.ItemAnimatorFactory;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MenuPageActivity extends BaseActivity implements  ParseManager.OnFinishListener , UserLoginListener , DialogOnClickListener {

    private static String TAG_MAIN = "MenuPageActivity" ;
    private static String TAG_PARSE = "PARSE_HANDLER" ;
    private static String TAG_SP = "TYPE_NAME" ;

    private RecyclerAdapter recyclerAdapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout ;
    private NavigationView navigationView;
    private int mContentViewHeight;


    private TextView plateBadge ;

    private List<com.gulpp.android.gulppapp.model.Menu> menuList;


    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        initialiseViews();

    }


    private void initialiseViews(){

        intialiseMenuList();
        initialiseNavDrawer();
        initialiseNavigationView();
        initialiseRecyclerView();
        initialiseToolBar();

    }

    private void intialiseMenuList() {
        menuList = getGlobal().getMenuList();
    }

    /**
     * INITIALISING VIEWS
     **/

    private void initialiseNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_action_list,
                0,
                0){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void initialiseNavigationView(){
        setupNavigationHeader();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.profile:
                        showUserProfile();
                        return true;

                    case R.id.aboutus:
                        //goToActivity();
                        menuItem.setChecked(false);
                        return true;

                    default:
                        return true;
                }
            }
        });

    }

    private void setupNavigationHeader() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View view = this.navigationView.getHeaderView(0);
        TextView navView_UserName  = (TextView) view.findViewById(R.id.navigation_view_userName) ;
        TextView navView_UserEmail = (TextView) view.findViewById(R.id.navigation_view_userEmail);


        if(isUserSignedIn()){
            view.findViewById(R.id.navigation_view_userLogin).setVisibility(View.GONE);
            navView_UserName.setText(ParseUser.getCurrentUser().getUsername());
            navView_UserEmail.setText(ParseUser.getCurrentUser().getEmail());
            view.findViewById(R.id.navigation_view_userDetailsLayout).setVisibility(View.VISIBLE);

        }
        else{
            view.findViewById(R.id.navigation_view_userLogin).setVisibility(View.VISIBLE);
            view.findViewById(R.id.navigation_view_userLogin_btn).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    validateUserSignIn(Constant.LOGIN_USER_FOR_MENUPAGE);
                }
            });
            view.findViewById(R.id.navigation_view_userDetailsLayout).setVisibility(View.GONE);
        }
    }


    private void initialiseToolBar() {

        toolbar = (Toolbar) findViewById(R.id.main_Toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_list);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        toolbar.getViewTreeObserver().removeOnPreDrawListener(this);
                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

                        toolbar.measure(widthSpec, heightSpec);
                        mContentViewHeight = toolbar.getHeight();
                        collapseToolbar();
                        return true;
                    }
                });

    }

    private void initialiseRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menuPage_recycler_view);
        recyclerView.setItemAnimator(ItemAnimatorFactory.slidein());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(this, menuList);
        recyclerView.setAdapter(recyclerAdapter);
    }


    /**
     * FUNCTIONS
     */


    private void showPlateActivity(){
        validateUserSignIn(Constant.LOGIN_USER_FOR_CART);
    }

    private void showUserProfile() { validateUserSignIn(Constant.LOGIN_USER_FOR_PROFILE); }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_page, menu);
        setCartIcon(menu);
        return super.onCreateOptionsMenu(menu);


        // return true;
    }

    private void setCartIcon(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_plate);
        MenuItemCompat.setActionView(item, R.layout.layout_cart_badge);
        View view = MenuItemCompat.getActionView(item);
        plateBadge = (TextView) view.findViewById(R.id.plateImage_badge);
        plateBadge.setVisibility(View.GONE);
        ImageView plateIcon = (ImageView) view.findViewById(R.id.cartImage);
        plateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlateActivity();
            }
        });

        setCartBadge();
    }

    private void setCartBadge() {

        if (PlateItemListHandler.getPlateItemCount() < 1 && PlateItemListHandler.getPlateItemList().size() == 0)
            plateBadge.setVisibility(View.GONE);
        else {
            plateBadge.setVisibility(View.VISIBLE);
            plateBadge.setText(PlateItemListHandler.getPlateItemCount() + "");
        }

    }

    private void collapseToolbar(){

        int toolBarHeight;
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        toolBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        ValueAnimator valueHeightAnimator = ValueAnimator.ofInt(mContentViewHeight, toolBarHeight);
        valueHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
                lp.height = (Integer) animation.getAnimatedValue();
                toolbar.setLayoutParams(lp);
            }
        });

        valueHeightAnimator.start();
        valueHeightAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(id == R.id.action_plate){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onUserLoggingIn() {

        setupNavigationHeader();

    }

    @Override
    public void onDialogButtonClick() {
        showPlateActivity();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.i(TAG_MAIN , "MenuPage has been restarted");
        initialiseViews();
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private static final int VIEW_TYPE_FIRST  = 0;
        private static final int VIEW_TYPE_SECOND = 1;
        private static final int VIEW_TYPE_THIRD = 2;

        private List<com.gulpp.android.gulppapp.model.Menu> menuList;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {

            //Subscription Card
            public LinearLayout subscription_card ;

            //Menu items
            public  View        view;
            private TextView    menuItem_ComboName;
            private TextView    menuItem_price ;
            private ImageView   menuItem_imageView;
            private ImageButton menuItem_addbutton ;
            private ImageButton menuItem_removeBtn ;
            private TextView    menuItem_itemCount ;
            private TextView    hoverTextView ;
            private BlurLayout  sampleLayout;

            public ViewHolder(View v , int type) {
                super(v);

                if (type == VIEW_TYPE_THIRD) {
                    menuItem_ComboName = (TextView) v.findViewById(R.id.menuItem_ComboName);
                    menuItem_price     = (TextView) v.findViewById(R.id.menuItem_Price);
                    menuItem_imageView = (ImageView) v.findViewById(R.id.menuItem_imageview);
                    menuItem_addbutton = (ImageButton) v.findViewById(R.id.menuItem_addToCart);
                    menuItem_removeBtn = (ImageButton) v.findViewById(R.id.menuItem_removeFromCart);
                    menuItem_itemCount = (TextView) v.findViewById(R.id.menuItem_itemCount);
                    sampleLayout  = (BlurLayout) v.findViewById(R.id.blurlayout);
                    View hover    = LayoutInflater.from(context).inflate(R.layout.layout_hover, null);
                    hoverTextView = (TextView) hover.findViewById(R.id.textView_menuItemList);
                    sampleLayout.setHoverView(hover);
                    sampleLayout.setBlurDuration(550);
                    sampleLayout.addChildAppearAnimator(hover, R.id.textView_menuItemList, Techniques.FlipInX);
                    sampleLayout.addChildDisappearAnimator(hover, R.id.textView_menuItemList, Techniques.FadeOut);
                    view = v;
                }
                else if(type == VIEW_TYPE_FIRST){
                    subscription_card= (LinearLayout) v.findViewById(R.id.subscription_card);
                }

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public RecyclerAdapter(Context context, List<com.gulpp.android.gulppapp.model.Menu> items) {
            this.context = context;
            this.menuList = items;

        }

        @Override
        public int getItemViewType(int position) {

            if(position == 0)
                return VIEW_TYPE_FIRST;
            else if(position == 1)
                return VIEW_TYPE_SECOND;

            return VIEW_TYPE_THIRD;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            switch (viewType){
                case VIEW_TYPE_FIRST :
                    View subscriptionView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.card_subscribe_now, parent, false);
                    return new ViewHolder(subscriptionView,VIEW_TYPE_FIRST);

                case VIEW_TYPE_SECOND :
                    View separatorView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.layout_separator, parent, false);
                    return new ViewHolder(separatorView,VIEW_TYPE_SECOND);

                default :
                    View menuItemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.card_menu_item, parent, false);
                    return new ViewHolder(menuItemView,VIEW_TYPE_THIRD);

            }
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if(getItemViewType(position)== VIEW_TYPE_THIRD) {
                holder.menuItem_ComboName.setText(this.menuList.get(position).getMenuName());
                holder.menuItem_price.setText(PriceHandler.addCurrencySymbol(this.menuList.get(position).getMenuPrice()));
                holder.hoverTextView.setText(getTitleList(this.menuList.get(position).getMenuItems()));
                holder.menuItem_itemCount.setText(String.valueOf(PlateItemListHandler.getQtyForItem(menuList.get(position))));

                holder.menuItem_addbutton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Log.i("WADDUP", "WADDUP");
                        PlateItemListHandler.addItemToPlate(menuList.get(position));
                        holder.menuItem_itemCount.setText(String.valueOf(PlateItemListHandler.getQtyForItem(menuList.get(position))));
                        final FoodInfoDialog openDialog = new FoodInfoDialog(context);
                        setCartBadge();
                        openDialog.show();
                    }
                });
                Picasso.with(context).load(menuList.get(position).getMenuImageURL()).into(holder.menuItem_imageView);

                holder.menuItem_removeBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                       if(PlateItemListHandler.isItemPresentInList(menuList.get(position))){
                          PlateItemListHandler.reduceItemQtyInList(menuList.get(position));
                          holder.menuItem_itemCount.setText(String.valueOf(PlateItemListHandler.getQtyForItem(menuList.get(position))));
                          setCartBadge();
                       }
                    }
                });

            } else if(getItemViewType(position)== VIEW_TYPE_FIRST){
                holder.subscription_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToActivity(SubscriptionActivity.class);
                    }
                });
            }

        }


        private String getTitleList(List<String> items){

            String listString = new String();

            for(int i=0 ; i<items.size() ; i++){
                String tempString = items.get(i);
                if(i != items.size()-1)
                    tempString = tempString.concat(" , ");

                listString = listString.concat(tempString);
            }

            return listString;
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            Log.i(TAG_PARSE, String.valueOf(this.menuList.size()));
            return this.menuList.size();
        }

    }


}




