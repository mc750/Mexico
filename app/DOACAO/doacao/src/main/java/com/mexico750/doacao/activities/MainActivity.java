package com.mexico750.doacao.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.mexico750.doacao.fragments.MainFragment;
import com.mexico750.doacao.fragments.NavigationDrawerFragment;
import com.mexico750.doacao.R;
import com.mexico750.doacao.utils.JsonUtils;
import com.mexico750.doacao.preferences.Pref;
import com.mexico750.doacao.user.User;

import org.joda.time.DateTime;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadPreferences();
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit();
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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private void loadPreferences(){
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Pref.USER_DATA.getPreferenceName(), MODE_PRIVATE);
        DateTime signupRequest = new DateTime(sharedPreferences.getLong(Pref.USER_SIGNUP.getField(), 0L));

        this.user = JsonUtils.getObject(
                sharedPreferences.getString(Pref.USER_DATA.getField(), "{}"),
                User.class
        );

        if (signupRequest.isBeforeNow()){
            if (user.isEmpty()){
                startActivity(new Intent(this, SignUpActivity.class));
                finish();
            }
        }
    }

}
