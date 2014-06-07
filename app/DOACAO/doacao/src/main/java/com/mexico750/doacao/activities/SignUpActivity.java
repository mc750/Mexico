package com.mexico750.doacao.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mexico750.doacao.R;
import com.mexico750.doacao.user.BloodType;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.DateUtils;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.GregorianCalendar;


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

    protected static User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        inputHeight = (EditText) findViewById(R.id.signup_height);
        btnAccept = (Button) findViewById(R.id.signup_accept);
        btnReject = (Button) findViewById(R.id.signup_reject);

        inputBirthday.setOnClickListener(new ChangeDate());
        inputBirthday.setOnFocusChangeListener(new ChangeDate());
        inputName.setOnFocusChangeListener(new InputChanger());
        inputEmail.setOnFocusChangeListener(new InputChanger());
        inputBirthday.setOnFocusChangeListener(new InputChanger());
        radioGender.setOnCheckedChangeListener(new RadioSelector());
        comboBlood.setOnItemSelectedListener(new ListSelector());
        inputWeight.setOnFocusChangeListener(new InputChanger());
        inputHeight.setOnFocusChangeListener(new InputChanger());
    }

    /**
     * Initialize date picker
     */
    protected void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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

            return new DatePickerDialog(getActivity(), this, DT.getYear(), DT.getMonthOfYear(), DT.getDayOfMonth());
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            DT = DateUtils.getDateFor(year, month + 1, day);
            inputBirthday.setText(DateUtils.toStr(DT));
            user.setBirthday(DT);
        }
    }

    /**
     * InputChanger
     * Controls behaviour to every TextView that changes its value
     */
    protected static class InputChanger implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View view, boolean inFocus) {
            if (!inFocus){
                EditText field = (EditText) view;

                switch(field.getId()){
                    case R.id.signup_name:
                        user.setName(field.getText().toString());
                        break;
                    case R.id.signup_email:
                        user.setEmail(field.getText().toString());
                        break;
                    case R.id.signup_height:
                        user.setHeight(Double.parseDouble(field.getText().toString()));
                        break;
                    case R.id.signup_weight:
                        user.setWeight(Double.parseDouble(field.getText().toString()));
                        break;
                }
            }
        }
    }

    protected static class RadioSelector implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch(radioGroup.getCheckedRadioButtonId()){
                case R.id.signup_female:
                    user.setGender(Gender.FEMALE);
                    break;
                case R.id.signup_male:
                    user.setGender(Gender.MALE);
                    break;
            }
        }
    }

    protected static class ListSelector implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedItem = adapterView.getSelectedItem().toString();
            user.setBloodType(BloodType.getByName(selectedItem));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
