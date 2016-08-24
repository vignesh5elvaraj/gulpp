package com.gulpp.android.gulppapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.model.OrderHistory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserOrderHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserOrderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrderHistoryFragment extends Fragment {

    private static final String ARG_PARAM_ORDER_LIST = "param1";
    private static final String TAG = "UserOrderHistoryFragment" ;

    private View fragmentView ;
    private List<OrderHistory> orderHistoryList;
    private OnFragmentInteractionListener mListener;

    public UserOrderHistoryFragment() {
        // Required empty public constructor
    }

    public static UserOrderHistoryFragment newInstance(List<OrderHistory> orderHistoryList) {

        UserOrderHistoryFragment fragment = new UserOrderHistoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM_ORDER_LIST, (ArrayList<? extends Parcelable>) orderHistoryList);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderHistoryList = new ArrayList<>();
            orderHistoryList = getArguments().getParcelableArrayList(ARG_PARAM_ORDER_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView =  inflater.inflate(R.layout.fragment_user_order_history, container, false);
        initialiseViews();
        return fragmentView ;
    }

    private void initialiseViews() {

        setupRecyclerView();

    }

    private void setupRecyclerView(){

        RecyclerView orderHistoryRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.orderHistory_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        orderHistoryRecyclerView.setLayoutManager(layoutManager);

        orderHistoryRecyclerView.setAdapter(new OrderHistoryRecyclerAdapter());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.OrderHistoryViewHolder>{

        public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
            public TextView orderDate ;
            public TextView itemList ;

            public OrderHistoryViewHolder(View itemView) {
                super(itemView);
                itemList = (TextView) itemView.findViewById(R.id.orderHistory_item);
                orderDate = (TextView) itemView.findViewById(R.id.orderHistory_date);
            }

        }

        @Override
        public OrderHistoryRecyclerAdapter.OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View orderHistoryItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_order_history_item, parent, false);
            return new OrderHistoryViewHolder(orderHistoryItem);

        }

        @Override
        public void onBindViewHolder(OrderHistoryRecyclerAdapter.OrderHistoryViewHolder holder, int position) {
             holder.orderDate.setText(orderHistoryList.get(position).getOrderDate());
             holder.itemList.setText(getTitleList(orderHistoryList.get(position).getOrderItems()));
        }

        @Override
        public int getItemCount() {
            return orderHistoryList.size();
        }

        private String getTitleList(List<String> items){

            Log.i(TAG, "Inside getTitleList ");

            String listString = new String();

            if(items != null) {
                for (int i = 0; i < items.size(); i++) {
                    String tempString = items.get(i);
                    if (i != items.size() - 1)
                        tempString = tempString.concat(", ");

                    listString = listString.concat(tempString);
                }
            }
            Log.i(TAG, "The item String " + listString);
            return listString;
        }
    }



}
