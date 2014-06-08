package com.mexico750.doacao.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.mexico750.doacao.R;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;
import org.joda.time.Years;

public class EvaluationFragment extends Fragment {
    private static final String EVALUATION_USER = "USER";

    private User user;
    private OnFragmentInteractionListener mListener;

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

    public static EvaluationFragment newInstance(User user) {
        EvaluationFragment fragment = new EvaluationFragment();
        Bundle args = new Bundle();
        args.putString(EVALUATION_USER, JsonUtils.getJson(user));
        fragment.setArguments(args);
        return fragment;
    }

    public EvaluationFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            user = JsonUtils.getObject(getArguments().getString(EVALUATION_USER, "{}"), User.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donate_1, container, false);
        inputAge = (EditText) view.findViewById(R.id.donate_age);
        inputGender = (EditText) view.findViewById(R.id.donate_gender);
        inputAge.setText(Years.yearsBetween(user.getBirthday(), new DateTime()).getYears() + " anos");
        inputGender.setText(user.getGender().getGender());

        if (user.getGender() == Gender.MALE){
            LinearLayout pregnancyGroup = (LinearLayout) view.findViewById(R.id.donate_pregancy_group);
            pregnancyGroup.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
