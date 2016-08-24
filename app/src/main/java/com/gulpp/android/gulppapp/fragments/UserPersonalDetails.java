package com.gulpp.android.gulppapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gulpp.android.gulppapp.handlers.ParseManager;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;


public class UserPersonalDetails extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static String TAG = "UserPersonalDetails" ;

    private OnFragmentInteractionListener mListener;

    private Button proceedBtn ;
    private View fragmentView ;

    private Spinner locationSpinner ,ITSpinner ,companiesSpinner ;


    // TODO: Rename and change types and number of parameters
    public static UserPersonalDetails newInstance(String param1, String param2) {
        UserPersonalDetails fragment = new UserPersonalDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public UserPersonalDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_user_personal_details, container, false);
        initialiseViews();
        return fragmentView;
    }

    private void initialiseViews() {

        initialiseLocationSpinner();
        ITSpinner = (Spinner)fragmentView.findViewById(R.id.userDetails_ITPark);
        companiesSpinner = (Spinner)fragmentView.findViewById(R.id.userDetails_CompanyName);
        proceedBtn = (Button)fragmentView.findViewById(R.id.userDetails_ProceedButton);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

    }

    private void initialiseLocationSpinner() {
        locationSpinner = (Spinner)fragmentView.findViewById(R.id.userDetails_Location);

         List<String> cityList = new ArrayList<>();
         ParseManager parseManager = ((LoginActivity) getActivity()).getActivityParseHandler();
         cityList = parseManager.getCityList();

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cities");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null) {
//                    Log.i(TAG, "Object retrieval success");
//                    for (int i = 0; i < objects.size(); i++) {
//                        cityList.add(objects.get(i).getString("cityName"));
//                        Log.i(TAG, objects.get(i).getString("cityName"));
//                    }
//                }
//                else{
//                    Log.i(TAG,"Object retrieval Failure");
//
//                }
//            }
//        });
//        cityList.add("Chennai");
//        cityList.add("Bangalore");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplication().getApplicationContext(),
                android.R.layout.simple_spinner_item, cityList);
        locationSpinner.setAdapter(adapter);


    }

    private void validateFields() {
        ((LoginActivity) getActivity()).openOrderConfirmedActivity();

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

    }

}
