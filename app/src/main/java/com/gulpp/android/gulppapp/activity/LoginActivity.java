package com.gulpp.android.gulppapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gulpp.android.gulppapp.BaseActivity;
import com.gulpp.android.gulppapp.Constant;
import com.gulpp.android.gulppapp.customView.VerifyEmailDialog;
import com.gulpp.android.gulppapp.handlers.ParseManager;
import com.gulpp.android.gulppapp.handlers.PlateItemListHandler;
import com.gulpp.android.gulppapp.R;
import com.gulpp.android.gulppapp.fragments.LoginIntroFragment;
import com.gulpp.android.gulppapp.fragments.SignInFragment;
import com.gulpp.android.gulppapp.fragments.SignUpFragment;
import com.gulpp.android.gulppapp.fragments.UserPersonalDetails;
import com.gulpp.android.gulppapp.interfaces.LoginActivityFragmentInterface;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity implements  SignInFragment.OnFragmentInteractionListener ,
                                                            SignUpFragment.OnFragmentInteractionListener,
                                                            UserPersonalDetails.OnFragmentInteractionListener,
                                                            LoginIntroFragment.OnFragmentInteractionListener,
                                                            LoginActivityFragmentInterface
{

    FrameLayout inputFieldHolder ;

    LoginIntroFragment loginIntroFragment ;
        SignInFragment signInFragment ;
        SignUpFragment signUpFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intializeViews();
        initialiseFragments();
    }


    private void intializeViews() {

        inputFieldHolder = (FrameLayout) findViewById(R.id.inputFieldHolder);
        //initializeIntroFragment();
    }

    private void initialiseFragments(){
         loginIntroFragment = new LoginIntroFragment();
         signInFragment = new SignInFragment() ;
         signUpFragment = new SignUpFragment() ;

        showLoginIntro();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }



    public void saveUserDetails(){

        getGlobal().setUserId(ParseUser.getCurrentUser().getObjectId());

    }

    public ParseManager getActivityParseHandler(){
       return getParseHandler();
    }


    public void openOrderConfirmedActivity(){

        setUserLoginFlag(true);
        PlateItemListHandler.clearPlateItems();
        Intent intent = new Intent(this , CheckoutActivity.class);
        startActivity(intent);

    }

    private void showLoginIntro() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.inputFieldHolder, loginIntroFragment, "")
                           .addToBackStack(null)
                           .commit();

    }


    @Override
    public void showSignUpError(String message) {
        showSnackBar(this.findViewById(R.id.coordinatorLoginLayout).getRootView(), message);
    }

    @Override
    public void signUpDone() {
        setUserLoginFlag(true);

        VerifyEmailDialog emailDialog = new VerifyEmailDialog(this);
        emailDialog.show();

        //killActivity(this);
    }

    private void sendVerificationEmail() {

        ParseUser user = ParseUser.getCurrentUser() ;

    }


    @Override
    public void providePersonalDetails() {

        UserPersonalDetails userPersonalDetails =new UserPersonalDetails();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.inputFieldHolder, userPersonalDetails ,"")
                           .addToBackStack(null)
                           .commit();
    }


    @Override
    public void showSignInError(String message) {
        showSnackBar(this.findViewById(R.id.coordinatorLoginLayout).getRootView(), message);

    }

    @Override
    public void signInDone() {

        setUserLoginFlag(true);

        Intent intent = getIntent();
        if(intent.getStringExtra("value").equals(Constant.LOGIN_INTENT_FOR_CART))
            setResult(Constant.LOGIN_USER_FOR_CART);
        else if (intent.getStringExtra("value").equals(Constant.LOGIN_INTENT_FOR_SUBSCRIPTION))
            setResult(Constant.LOGIN_USER_FOR_SUBSCRIPTION);
        else if (intent.getStringExtra("value").equals(Constant.LOGIN_INTENT_FOR_PROFILE))
            setResult(Constant.LOGIN_USER_FOR_PROFILE);
        else if (intent.getStringExtra("value").equals(Constant.LOGIN_INTENT_FOR_PROFILE))
            setResult(Constant.LOGIN_USER_FOR_MENUPAGE);

        killActivity(this);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void showLoginFragment() {
        Log.i("INFO","SHOW SIGNIN");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.inputFieldHolder, signInFragment ,"")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void showSignUpFragment() {

        Log.i("INFO","SHOW SIGNUP");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.inputFieldHolder, signUpFragment, "")
                .addToBackStack(null)
                .commit();

    }


    public void logoutUser(){
        ParseUser.logOut();}

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() == 0) {
            killActivity(this);
        }
        else
        getFragmentManager().popBackStack();

    }

    @Override
    public void showIntroFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.inputFieldHolder, loginIntroFragment, "")
                .addToBackStack(null)
                .commit();

    }
}
