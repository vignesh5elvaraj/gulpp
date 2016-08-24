package com.gulpp.android.gulppapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.activity.LoginActivity;
import com.gulpp.android.gulppapp.model.OrderHistory;
import com.gulpp.android.gulppapp.model.UserData;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserInfoFragment extends Fragment {

    private static final String ARG_PARAM_NUMBER = "userPhoneNumber";
    private static final String ARG_PARAM_OFFICE = "userOfficeName";
    private static final String TAG = "UserInfoFragment";
    private OnFragmentInteractionListener mListener;

    private Button userInfo_logout,
                   userInfo_saveButton,
                   userInfo_editButton;

    private String userPhoneNumber,
                   userOfficeName;

    private EditText userInfo_location,
                     userInfo_phoneNumber ;

    private View fragmentView;

    LinearLayout rootLayout ;

    public UserInfoFragment() { }

    public static UserInfoFragment newInstance(Number userNumber , String userOfficeName) {

        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_NUMBER, String.valueOf(userNumber));
        args.putString(ARG_PARAM_OFFICE, userOfficeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_user_info, container, false);
        intialiseViews();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userPhoneNumber = getArguments().getString(ARG_PARAM_NUMBER);
            userOfficeName = getArguments().getString(ARG_PARAM_OFFICE);
        }
    }

    private void intialiseViews() {

        setupUserInfo();
        setupEditButton();
        setupSaveButton();
        setupLogoutButton();
    }

    private void setupUserInfo() {

     //    rootLayout = (LinearLayout) fragmentView.findViewById(R.id.rootLayout);

        Log.i(TAG,"INSIDE USER PROFILE");
        userInfo_phoneNumber = (EditText)fragmentView.findViewById(R.id.input_phoneNumber);
        userInfo_phoneNumber.setText(userPhoneNumber);
//        userInfo_phoneNumber.setFocusable(false);
//        userInfo_phoneNumber.setClickable(false);
        userInfo_phoneNumber.setKeyListener(null);

        userInfo_location = (EditText)fragmentView.findViewById(R.id.input_location);
        userInfo_location.setText(userOfficeName);
//        userInfo_location.setFocusable(false);
//        userInfo_location.setClickable(false);
        userInfo_location.setKeyListener(null);


    }


    private void setupLogoutButton() {

        userInfo_logout = (Button)fragmentView.findViewById(R.id.userInfo_logoutBtn);
        userInfo_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoggingOut();
            }
        });

    }

    private void setupEditButton() {

        userInfo_editButton = (Button)fragmentView.findViewById(R.id.userInfo_editBtn);
        userInfo_editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToEditMode();
            }
        });

    }
    private void setupSaveButton() {
        userInfo_saveButton = (Button)fragmentView.findViewById(R.id.userInfo_saveBtn);
        userInfo_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNonEditMode();
            }
        });
    }

    private void switchToNonEditMode() {

        userInfo_editButton.setVisibility(View.VISIBLE);
        userInfo_saveButton.setVisibility(View.GONE);

        ParseUser parseUser = ParseUser.getCurrentUser();

        userInfo_location.setKeyListener(null);
        parseUser.put("officeName", userInfo_location.getText().toString());
//        userInfo_location.setFocusable(false);
//        userInfo_location.setClickable(false);


        userInfo_phoneNumber.setKeyListener(null);
        parseUser.put("phone", Long.parseLong(userInfo_phoneNumber.getText().toString()));
//        userInfo_phoneNumber.setFocusable(false);
//        userInfo_phoneNumber.setClickable(false);

        parseUser.saveInBackground();

    }


    private void switchToEditMode() {

        userInfo_editButton.setVisibility(View.GONE);
        userInfo_saveButton.setVisibility(View.VISIBLE);

//        userInfo_location.setFocusable(true);
//        userInfo_location.setClickable(true);
//        userInfo_location.setFocusableInTouchMode(true);
        userInfo_location.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());

//        userInfo_phoneNumber.setFocusable(true);
//        userInfo_phoneNumber.setClickable(true);
//        userInfo_phoneNumber.setFocusableInTouchMode(true);
        userInfo_phoneNumber.setKeyListener(new EditText(getActivity().getApplicationContext()).getKeyListener());

//        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInputFromWindow(rootLayout.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLoggingOut();
    }

}
