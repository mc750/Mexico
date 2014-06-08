package com.mexico750.doacao.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mexico750.doacao.R;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.user.UserHealth;
import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;
import org.joda.time.Years;

public class EvaluationFragment extends Fragment implements View.OnClickListener {
    private static final String EVALUATION_USER = "USER";
    private static final String EVALUATION_HEALTH = "USER_HEALTH";

    private User user;
    private UserHealth health;
    private OnEvaluationListener mListener;

    private static EditText inputAge;
    private static EditText inputGender;
    private static EditText inputWeight;
    private static Spinner selectPregnancy;
    private static CheckBox checkBreastFeeding;
    private static CheckBox checkCold;
    private static CheckBox checkTattoo;
    private static CheckBox checkStd;
    private static CheckBox checkViruses;
    private static CheckBox checkVaccine;
    private static CheckBox checkCirurgy;
    private static CheckBox checkBacteria;
    private static CheckBox checkHepatitis;
    private static Button btnAccept;

    public static EvaluationFragment newInstance(User user, UserHealth health) {
        EvaluationFragment fragment = new EvaluationFragment();
        Bundle args = new Bundle();
        args.putString(EVALUATION_USER, JsonUtils.getJson(user));
        args.putString(EVALUATION_HEALTH, JsonUtils.getJson(health));
        fragment.setArguments(args);
        return fragment;
    }

    public EvaluationFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            user = JsonUtils.getObject(getArguments().getString(EVALUATION_USER, "{}"), User.class);
            health = JsonUtils.getObject(getArguments().getString(EVALUATION_HEALTH, "{}"), UserHealth.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        if (health.getUser() == null) {
            view = inflater.inflate(R.layout.fragment_evaluation, container, false);
            inputAge = (EditText) view.findViewById(R.id.evaluation_age);
            inputGender = (EditText) view.findViewById(R.id.evaluation_gender);
            inputWeight = (EditText) view.findViewById(R.id.evaluation_weight);
            checkCold = (CheckBox) view.findViewById(R.id.evaluation_cold);
            checkBacteria = (CheckBox) view.findViewById(R.id.evaluation_bacterias);
            checkViruses = (CheckBox) view.findViewById(R.id.evaluation_viruses);
            checkVaccine = (CheckBox) view.findViewById(R.id.evaluation_vaccines);
            checkCirurgy = (CheckBox) view.findViewById(R.id.evaluation_cirurgy);
            checkTattoo = (CheckBox) view.findViewById(R.id.evaluation_tattoo);
            checkStd = (CheckBox) view.findViewById(R.id.evaluation_std);
            checkHepatitis = (CheckBox) view.findViewById(R.id.evaluation_hepatitis);

            inputAge.setText(Years.yearsBetween(user.getBirthday(), new DateTime()).getYears() + " anos");
            inputGender.setText(user.getGender().getGender());

            if (user.getWeight() != null && user.getWeight() > 0){
                inputWeight.setText(user.getWeight().toString());
            }
            if (user.getGender() == Gender.MALE) {
                LinearLayout pregnancyGroup = (LinearLayout) view.findViewById(R.id.donate_pregancy_group);
                pregnancyGroup.setVisibility(View.GONE);
            }

            btnAccept = (Button) view.findViewById(R.id.evaluation_accept);
            btnAccept.setOnClickListener(this);
        } else {
            view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEvaluationListener) activity;
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

    @Override
    public void onClick(View view) {
        UserHealth health = new UserHealth();
        health.setAge(Years.yearsBetween(user.getBirthday(), new DateTime()).getYears());
        health.setUser(user);
        health.setRecentCold(checkCold.isChecked());
        health.setRecentBacteria(checkBacteria.isChecked());
        health.setRecentViruses(checkViruses.isChecked());
        health.setRecentVaccine(checkVaccine.isChecked());
        health.setRecentCirurgy(checkCirurgy.isChecked());
        health.setRecentTattoo(checkTattoo.isChecked());
        health.setStdRisk(checkStd.isChecked());
        health.setHasHepatits(checkHepatitis.isChecked());
        health.setIsBreastFedder(checkBreastFeeding.isChecked());

        String pregnancy = selectPregnancy.getSelectedItem().toString();
        if (pregnancy.equalsIgnoreCase("não")) {
            health.setIsPregnant(Boolean.FALSE);
            health.setRecentPregnancy(Boolean.FALSE);
        } else if (pregnancy.startsWith("Sim, estou")){
            health.setIsPregnant(Boolean.TRUE);
        } else if (pregnancy.startsWith("Sim, estive")){
            health.setRecentPregnancy(Boolean.TRUE);
        }

        mListener.onEvaluationIsFinished(health);
    }

    public interface OnEvaluationListener {
        public void onEvaluationIsFinished(UserHealth userHealth);
    }

}
