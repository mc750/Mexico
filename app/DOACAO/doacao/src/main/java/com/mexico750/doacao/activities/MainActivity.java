package com.mexico750.doacao.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;

import com.mexico750.doacao.fragments.DonationFragment;
import com.mexico750.doacao.fragments.EvaluationFragment;
import com.mexico750.doacao.fragments.InformationFragment;
import com.mexico750.doacao.fragments.MainFragment;
import com.mexico750.doacao.fragments.NavigationDrawerFragment;
import com.mexico750.doacao.R;
import com.mexico750.doacao.user.UserHealth;
import com.mexico750.doacao.utils.DateUtils;
import com.mexico750.doacao.utils.JsonUtils;
import com.mexico750.doacao.preferences.Pref;
import com.mexico750.doacao.user.User;

import org.joda.time.DateTime;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks, EvaluationFragment.OnEvaluationListener {

    private static final String MAIN_STACK = "MAIN";
    private static final String EVALUATION_STACK = "EVALUATION";
    private static final String DONATION_STACK = "DONATION";
    private static final String INFORMATION_STACK = "INFORMATION";

    private static User user;
    private static UserHealth health;
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
        openFragment(MainFragment.newInstance(user), MAIN_STACK);
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
            return false;
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

        health = JsonUtils.getObject(
                sharedPreferences.getString(Pref.USER_HEALTH.getField(), "{}"),
                UserHealth.class
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

    private void openFragment(Fragment frag, String stack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, frag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (stack != null && !stack.isEmpty()) {
            transaction.addToBackStack(stack);
        }
        transaction.commit();
    }

    public void goEvaluation(View v){
        openFragment(EvaluationFragment.newInstance(user, health), EVALUATION_STACK);
    }

    public void rejectDonationFirstTest(View v){
        currentDonationPage = 2;
        user.getConfiguration().setSkipFirstTest(Boolean.TRUE);
        openFragment(DonationFragment.newInstance(currentDonationPage, user), EVALUATION_STACK);
    }

    public void startDonation(View v){
        currentDonationPage = 1;
        if (user.getConfiguration().getSkipFirstTest()){
            currentDonationPage = 2;
        }

        openFragment(DonationFragment.newInstance(currentDonationPage, user), DONATION_STACK);
    }

    public void cancelEvaluation(View v){
        openFragment(MainFragment.newInstance(user), null);
    }

    @Override
    public void onEvaluationIsFinished(UserHealth userHealth) {
        user.getConfiguration().setIsNewUser(Boolean.FALSE);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DateTime nextDonation = userHealth.calculateNextDonation();
        String message;
        if (nextDonation == null){
            message = "Infelizmente existe uma grande possibilidade de você não poder ser doador. Clique ir e veja o resultado da sua avaliação.";
        } else if (nextDonation.isAfterNow()) {
            message = "Infelizmente, no momento, você não está apto a realizar uma doação. " +
                    "A data para o qual esperamos que você esteja preparado para realizar a doação é :" +
                    DateUtils.toStr(nextDonation);
        } else {
            message = "Parabéns! Você está apto a realizar uma doação. Veja a seguir instruções para a realização da doação.";
        }

        builder.setTitle("Resultado da Avaliação")
                .setMessage(message)
                .setNeutralButton("Ir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (nextDonation == null){

                        } else if (nextDonation.isAfterNow()){

                        } else {
                            openFragment(new InformationFragment(), INFORMATION_STACK);
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Context context = getApplicationContext();
        SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(Pref.USER_HEALTH.getPreferenceName(), MODE_PRIVATE).edit();
        sharedPreferencesEditor.putString(Pref.USER_HEALTH.getField(), JsonUtils.getJson(userHealth));
        sharedPreferencesEditor.commit();
    }
}
