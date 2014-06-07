package com.mexico750.doacao.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mexico750.doacao.R;
import com.mexico750.doacao.utils.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SignUpActivity extends FragmentActivity{
    protected static EditText inputName;
    protected static EditText inputEmail;
    protected static TextView inputBirthdate;
    protected static RadioGroup radioGender;
    protected static Spinner comboBlood;
    protected static EditText inputWeight;
    protected static EditText inputHeight;
    protected static Button btnAccept;
    protected static Button btnReject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        initializeElements();
    }

    protected void initializeElements(){
        /*inputName = (EditText) findViewById(R.id.signup_name);
        inputEmail = (EditText) findViewById(R.id.signup_email);
        inputBirthdate = (TextView) findViewById(R.id.signup_birthday);
        radioGender = (RadioGroup) findViewById(R.id.signup_gender);
        comboBlood = (Spinner) findViewById(R.id.sigup);

        inputBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) { pickDate(); }
            }
        });
        inputBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });*/
    }

    public void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 18;
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month + 1, day);
            inputBirthdate.setText(DateUtils.toStr(cal));
        }
    }
}
