package com.mexico750.doacao.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;

import com.mexico750.doacao.fragments.DonationFragment;
import com.mexico750.doacao.fragments.MainFragment;
import com.mexico750.doacao.fragments.NavigationDrawerFragment;
import com.mexico750.doacao.R;
import com.mexico750.doacao.utils.JsonUtils;
import com.mexico750.doacao.preferences.Pref;
import com.mexico750.doacao.user.User;

import org.joda.time.DateTime;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static User user;
    private static Integer currentDonationPage = 0;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPreferences();
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        openFragment(MainFragment.newInstance(user), true);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void loadPreferences(){
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Pref.USER_DATA.getPreferenceName(), MODE_PRIVATE);
        DateTime signupRequest = new DateTime(sharedPreferences.getLong(Pref.USER_SIGNUP.getField(), 0L));

        user = JsonUtils.getObject(
                sharedPreferences.getString(Pref.USER_DATA.getField(), "{}"),
                User.class
        );

        if (signupRequest.isBeforeNow() || (signupRequest.isAfterNow() && user == null)){
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        }
    }

    public void updateUser(User user){
        Context context = getApplicationContext();
        SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(Pref.USER_DATA.getPreferenceName(), MODE_PRIVATE).edit();
        sharedPreferencesEditor.putString(Pref.USER_DATA.getField(), JsonUtils.getJson(user));
        sharedPreferencesEditor.commit();
    }

    private void openFragment(Fragment frag, Boolean stack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, frag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (stack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void startDonation(View v){
        currentDonationPage = 1;
        openFragment(DonationFragment.newInstance(currentDonationPage, user), false);
    }

    public void nextDonationPage(View v){
        openFragment(DonationFragment.newInstance(++currentDonationPage, user), false);
    }

    public void cancelDonation(View v){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void previousDonationPage(View v){
        openFragment(DonationFragment.newInstance(--currentDonationPage, user), false);
    }

}
