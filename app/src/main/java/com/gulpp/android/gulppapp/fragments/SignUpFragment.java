package com.gulpp.android.gulppapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dd.CircularProgressButton;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.interfaces.LoginActivityFragmentInterface;
import com.gulpp.android.gulppapp.model.UserData;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpFragment extends android.support.v4.app.Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SignUpFragment" ;

    private String mParam1;
    private String mParam2;

    private LoginActivityFragmentInterface goBackBtnListener ;
    private OnFragmentInteractionListener mListener;

    private View fragmentView ;

    private CircularProgressButton createNewUser ;
    private Button   orSignIn;
    private EditText userFullName ,
                     userEmailId ,
                     userMobileNumber,
                     userPassword ,
                     confirmUserPassword ;

    private UserData userData ;


    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public SignUpFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        userData = new UserData();
        initialiseView();
        return fragmentView;
    }

    private void initialiseView() {

        userFullName = (EditText)fragmentView.findViewById(R.id.userSignUp_firstName);
        userEmailId = (EditText)fragmentView.findViewById(R.id.userSignUp_emailAddress);
        userPassword = (EditText)fragmentView.findViewById(R.id.userSignUp_password);
        confirmUserPassword = (EditText)fragmentView.findViewById(R.id.userSignUp_confirmPassword);
        userMobileNumber = (EditText)fragmentView.findViewById(R.id.userSignUp_mobile);

        createNewUser = (CircularProgressButton)fragmentView.findViewById(R.id.userSignUp_createNewUserBtn);
        createNewUser.setIndeterminateProgressMode(true);
        orSignIn = (Button)fragmentView.findViewById(R.id.go_back_button);

        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfPasswordsMatch();
            }
        });

        orSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackBtnListener.showIntroFragment();
            }
        });

    }

    private void checkIfPasswordsMatch() {

        if(userPassword.getText().toString().equals(confirmUserPassword.getText().toString())) {
            createNewUser();
        }else
            mListener.showSignUpError(Constant.PASSWORD_MISMATCH);
    }


    public void createNewUser(){

        createNewUser.setProgress(1);

        ParseUser user = new ParseUser();
        user.setUsername(userFullName.getText().toString());
        user.setEmail(userEmailId.getText().toString());
        user.setPassword(userPassword.getText().toString());
        user.put("phone",Long.parseLong(userMobileNumber.getText().toString()));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG, "Signed Up");
                    //mListener.providePersonalDetails();
                    createNewUser.setProgress(0);
                    mListener.signUpDone();
                }
                else {
                    createNewUser.setProgress(0);
                    Log.i(TAG, "Signed Up failure");
                    mListener.showSignUpError(e.getMessage());
                }
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            goBackBtnListener =(LoginActivityFragmentInterface) activity;
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
        //public void onFragmentInteraction();
        void providePersonalDetails();
        void showSignUpError(String message);
        void signUpDone();
    }

}
