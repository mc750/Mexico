package com.mexico750.doacao.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.mexico750.doacao.R;
import com.mexico750.doacao.preferences.Pref;
import com.mexico750.doacao.user.BloodType;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.DateUtils;
import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;


public class SignUpActivity extends FragmentActivity{
    protected static EditText inputName;
    protected static EditText inputEmail;
    protected static EditText inputBirthday;
    protected static RadioGroup radioGender;
    protected static Spinner comboBlood;
    protected static EditText inputWeight;
    protected static EditText inputHeight;
    protected static Button btnAccept;
    protected static Button btnReject;

    protected static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        initializeElements();
    }

    /**
     * Initialize screen elements
     */
    protected void initializeElements(){
        inputName = (EditText) findViewById(R.id.signup_name);
        inputEmail = (EditText) findViewById(R.id.signup_email);
        inputBirthday = (EditText) findViewById(R.id.signup_birthday);
        radioGender = (RadioGroup) findViewById(R.id.signup_gender);
        comboBlood = (Spinner) findViewById(R.id.signup_blood);
        inputWeight = (EditText) findViewById(R.id.signup_weight);
        btnAccept = (Button) findViewById(R.id.signup_accept);
        btnReject = (Button) findViewById(R.id.signup_reject);

        inputBirthday.setOnClickListener(new ChangeDate());
        inputBirthday.setOnFocusChangeListener(new ChangeDate());
        btnAccept.setOnClickListener(new AcceptHandler());
        btnReject.setOnClickListener(new RejectHandler());
    }

    /**
     * Initialize date picker
     */
    protected void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    protected void showAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opa! Você esqueceu de algo...")
                .setMessage(message)
                .setNeutralButton("Obrigado", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Change Date Class
     * Handle whether the user change its birthday.
     */
    protected class ChangeDate implements View.OnFocusChangeListener, View.OnClickListener{

        @Override
        public void onClick(View view) {
            pickDate();
        }

        @Override
        public void onFocusChange(View view, boolean inFocus) {
            if (inFocus) {
                pickDate();
            }
        }
    }


    /**
     * DatePicker Fragment
     * Display the date picker fragment to the user.
     */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static DateTime DT;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (DT == null) {
                DT = new DateTime();
            }

            return new DatePickerDialog(getActivity(), this, DT.getYear(), DT.getMonthOfYear() - 1, DT.getDayOfMonth());
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            DT = DateUtils.getDateFor(year, month + 1, day);
            inputBirthday.setText(DateUtils.toStr(DT));
        }
    }

    protected User createUser(){
        User newUser = new User();
        newUser.setName(inputName.getText().toString());
        newUser.setEmail(inputEmail.getText().toString());
        newUser.setBirthday(DateUtils.parseDate(inputBirthday.getText().toString()));
        if (radioGender.getCheckedRadioButtonId() == R.id.signup_male) {
            newUser.setGender(Gender.MALE);
        } else if (radioGender.getCheckedRadioButtonId() == R.id.signup_female) {
            newUser.setGender(Gender.FEMALE);
        }
        newUser.setBloodType(BloodType.getByName(comboBlood.getSelectedItem().toString()));
        newUser.setWeight(Double.parseDouble(inputWeight.getText().toString()));

        return newUser;
    }

    protected class AcceptHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            try {
                if (inputName.getText() == null || inputName.getText().toString().indexOf(" ") < 0){
                    showAlert("... seu nome é muito importante!");
                } else if (inputEmail.getText() == null || inputEmail.getText().toString().indexOf("@") < 0){
                    showAlert("... prometemos: nunca enviaremos mensagens indesejadas!");
                } else if (inputBirthday.getText() == null || DateUtils.parseDate(inputBirthday.getText().toString()).isAfterNow()){
                    showAlert("... seria um prazer poder lhe desejar um feliz aniversário!");
                } else if (radioGender.getCheckedRadioButtonId() < 1){
                    showAlert("... gostaríamos de saber se podemos o chamar por Sr. ou Sra.!");
                } else {
                    Context context = view.getContext();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Pref.USER_DATA.getPreferenceName(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    DateTime nextRequest = DateTime.now().plusYears(5);

                    editor.putString(Pref.USER_DATA.getField(), JsonUtils.getJson(createUser()));
                    editor.putLong(Pref.USER_SIGNUP.getField(), nextRequest.toInstant().getMillis());

                    editor.commit();

                    context.startActivity(new Intent(context, MainActivity.class));
                    activity.finish();
                }
            }catch(Exception e){
                showAlert("... ou será que nós nos esquecemos? " + e.getMessage());
            }
        }
    }

    protected class RejectHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Pref.USER_SIGNUP.getPreferenceName(), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            DateTime nextRequest = DateTime.now().plusDays(7);
            editor.putLong(Pref.USER_SIGNUP.getField(), nextRequest.toInstant().getMillis());
            editor.commit();

            context.startActivity(new Intent(context, MainActivity.class));
            activity.finish();
        }
    }
}
