package com.gulpp.android.gulppapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.gulpp.android.gulppapp.customUtils.PriceHandler;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.handlers.SubscriptionListHandler;
import com.gulpp.android.gulppapp.model.SubscribedItem;
import com.gulpp.android.gulppapp.model.SubscriptionMenu;

import java.util.ArrayList;
import java.util.List;


public class SubscriptionFragment extends Fragment   {
    private static final String ARG_PARAM_FRAGMENT_POSITION = "pos" ;
    private static final String ARG_PARAM_DAY = "day";
    private static final String ARG_PARAM_MENU_LIST = "menuList";
    private static final String TAG_MAIN = "SubscriptionFragment";

    private int mParam_pos ;
    private String mParam_day;
    private List<SubscriptionMenu> mParam_menuListForDay;

    private OnFragmentInteractionListener mListener;

    private View fragmentView;

    public SubscriptionFragment() {
        // Required empty public constructor
    }

    public static SubscriptionFragment newInstance(String param1 , List<SubscriptionMenu> param2 , int param3) {
        SubscriptionFragment fragment = new SubscriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_DAY, param1);
        args.putParcelableArrayList(ARG_PARAM_MENU_LIST, (ArrayList<? extends Parcelable>) param2);
        args.putInt(ARG_PARAM_FRAGMENT_POSITION, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_pos = getArguments().getInt(ARG_PARAM_FRAGMENT_POSITION);
            mParam_day = getArguments().getString(ARG_PARAM_DAY);
            mParam_menuListForDay = getArguments().getParcelableArrayList(ARG_PARAM_MENU_LIST);
        }


    }

    private void addComboToSubscriptionList(SubscriptionMenu menuItem ){
        mListener.addItemToSubscription(SubscriptionListHandler.getNewSubscribedItemForDay(mParam_day,menuItem) , mParam_pos);
    }

    private void removeComboItemfromSubscriptioList(){
        mListener.removeItemFromSubscription(mParam_pos);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_subscription, container, false);
        initialiseViews();

        return fragmentView ;
    }

    private void initialiseViews() {

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.subcriptionFragment_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        MenuListAdapter menuListAdapter = new MenuListAdapter(mParam_menuListForDay);
        recyclerView.setAdapter(menuListAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void addItemToSubscription(SubscribedItem subscribedItem , int pos) ;
        void removeItemFromSubscription( int pos);
    }


    public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>{

        MenuViewHolder previousHolder,
                       currentHolder ;

        List<SubscriptionMenu> menuListForDay;
        View itemView ;

        public MenuListAdapter(){
        }

        public MenuListAdapter(List<SubscriptionMenu> menuList){

            this.menuListForDay = menuList ;
        }

        public class MenuViewHolder extends RecyclerView.ViewHolder{

            public int selectedPostion ;
            private TextView menuItem_ComboName;
            private TextView menuItem_price ;
            private TextView hoverTextView ;
            private BlurLayout sampleLayout;
            public ImageButton selectBtn ;
            public Boolean isComboSelected = false;
            public LinearLayout selectLayout ;

            public MenuViewHolder(View itemView) {
                super(itemView);

                menuItem_ComboName = (TextView) itemView.findViewById(R.id.menuItem_ComboName);
                menuItem_price = (TextView) itemView.findViewById(R.id.menuItem_Price);
                selectLayout = (LinearLayout) itemView.findViewById(R.id.menuItem_ItemSelectedLayout);
                selectBtn = (ImageButton) itemView.findViewById(R.id.menuItem_addToCart);
                sampleLayout = (BlurLayout) itemView.findViewById(R.id.blurlayout);
                View hover = LayoutInflater.from(getContext()).inflate(R.layout.layout_hover, null);
                hoverTextView = (TextView) hover.findViewById(R.id.textView_menuItemList);
                sampleLayout.setHoverView(hover);
                sampleLayout.setBlurDuration(550);
                sampleLayout.addChildAppearAnimator(hover, R.id.textView_menuItemList, Techniques.FlipInX);
                sampleLayout.addChildDisappearAnimator(hover, R.id.textView_menuItemList, Techniques.FadeOut);

            }
        }



        @Override
        public MenuListAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            itemView = LayoutInflater.from(getContext()).inflate(R.layout.card_menu_item, parent ,false);
            MenuViewHolder menuViewHolder = new MenuViewHolder(itemView);
            return menuViewHolder;
        }

        @Override
        public void onBindViewHolder(final MenuViewHolder holder, final int position) {

            holder.menuItem_ComboName.setText(this.menuListForDay.get(position).getMenuName());
            holder.menuItem_price.setText(PriceHandler.addCurrencySymbol(this.menuListForDay.get(position).getMenuPrice()));
            holder.hoverTextView.setText(getTitleList(this.menuListForDay.get(position).getMenuItems()));
            holder.selectedPostion = position;
            holder.selectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleSelectedLayout(holder);
                }
            });

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

        @Override

        public int getItemCount() {
            return menuListForDay.size();
        }


        public void toggleSelectedLayout(MenuViewHolder holder ) {

            currentHolder= holder ;

            if(currentHolder != previousHolder)
            {
                disablePreviousItem();
                enableCurrentItem();
            }
            else
            {
                disablePreviousItem();
                previousHolder = null;
            }

        }

        private void disablePreviousItem() {

             if ( previousHolder != null)
               if(previousHolder.selectLayout.getVisibility() == View.VISIBLE )
               previousHolder.selectLayout.setVisibility(View.GONE);

               previousHolder = currentHolder;
               removeComboItemfromSubscriptioList();
        }

        private void enableCurrentItem() {

            if(currentHolder.selectLayout.getVisibility() == View.GONE)
                currentHolder.selectLayout.setVisibility(View.VISIBLE);

            addComboToSubscriptionList(this.menuListForDay.get(currentHolder.selectedPostion));
        }

    }


}
