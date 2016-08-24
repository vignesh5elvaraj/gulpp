package com.gulpp.android.gulppapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.model.ComboList;
import com.gulpp.android.gulppapp.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String TAG = "MENUPAGE_FRAGMENT";

    private ComboList comboList;

    private View fragmentView ;

    private OnFragmentInteractionListener mListener;

    List<Menu> plateList ;


    public static MenuPageFragment newInstance(ComboList comboList) {
        MenuPageFragment fragment = new MenuPageFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("combo", comboList);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plateList = new ArrayList<>();
        if (getArguments() != null) {
            comboList = (ComboList) getArguments().getParcelableArrayList("combo");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_menu_page, container, false);
        comboList = (ComboList) getArguments().getParcelableArrayList("combo");
        Log.i(TAG,comboList.getComboType());

        initialiseViews();
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initialiseViews() {
        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.menuPage_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Log.i("BEFORE CALL", String.valueOf(comboList.getComboType()));

    }
    

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void addItemToPlate(Menu menuItem);
    }


}