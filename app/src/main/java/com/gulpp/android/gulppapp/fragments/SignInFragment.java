package com.gulpp.android.gulppapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dd.CircularProgressButton;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.interfaces.LoginActivityFragmentInterface;
import com.gulpp.android.gulppapp.model.UserData;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "SignInFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LoginActivityFragmentInterface goBackBtnListener ;
    private OnFragmentInteractionListener mListener ;

    private View fragmentView ;

    CircularProgressButton loginBtn;
    //Button loginBtn;
    Button signUpBtn;
    EditText emailField ;
    EditText passwordField ;

    UserData data ;


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SignInFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeView();
        return fragmentView ;
    }

    private void initializeView() {

        emailField = (EditText) fragmentView.findViewById(R.id.userSignIn_emailAddress);
        passwordField = (EditText) fragmentView.findViewById(R.id.userSignIn_password);
        loginBtn = (CircularProgressButton) fragmentView.findViewById(R.id.userSignIn_LoginBtn);
        loginBtn.setIndeterminateProgressMode(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 setuserdata();
            }
        });

        signUpBtn =  (Button) fragmentView.findViewById(R.id.go_back_button);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackBtnListener.showIntroFragment();
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            goBackBtnListener = (LoginActivityFragmentInterface) activity;
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


    public void setuserdata() {

        loginBtn.setProgress(1);

        Log.i(TAG, "inside SetUserData");

        signinUser();
    }

    public void signinUser() {

        ParseUser.logInInBackground(emailField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    if(user.getBoolean("emailVerified") == true) {
                        Log.i(TAG, "Signed In");
                        mListener.signInDone();
                    }
                    else{
                        ParseUser.logOut();
                        mListener.showSignInError("Email not Verified");}

                    loginBtn.setProgress(0);

                } else {
                    Log.i(TAG, "SignIn Failed");
                    loginBtn.setProgress(0);
                    mListener.showSignInError(e.getMessage());
                }
            }
        });


    }

    public interface OnFragmentInteractionListener {
        void showSignInError(String message);
        void signInDone();
    }

}
