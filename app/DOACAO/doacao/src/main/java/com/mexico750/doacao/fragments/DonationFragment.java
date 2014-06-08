package com.mexico750.doacao.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mexico750.doacao.R;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Years;


public class DonationFragment extends Fragment {
    private static final String DONATION_PAGE = "PAGE";
    private static final String DONATION_USER = "USER";

    private static Integer currentPage;
    private static User user;

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

    public static DonationFragment newInstance(Integer page, User user) {
        DonationFragment fragment = new DonationFragment();
        Bundle args = new Bundle();
        args.putInt(DONATION_PAGE, page);
        args.putString(DONATION_USER, JsonUtils.getJson(user));
        fragment.setArguments(args);
        return fragment;
    }

    public DonationFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            currentPage = getArguments().getInt(DONATION_PAGE, 0);
            user = JsonUtils.getObject(getArguments().getString(DONATION_USER, "{}"), User.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        switch (currentPage){
            case 2:
                view = inflater.inflate(R.layout.fragment_donate_1, container, false);
                inputAge = (EditText) view.findViewById(R.id.donate_age);
                inputGender = (EditText) view.findViewById(R.id.donate_gender);
                inputAge.setText(Years.yearsBetween(user.getBirthday(), new DateTime()).getYears() + " anos");
                inputGender.setText(user.getGender().getGender());

                if (user.getGender() == Gender.MALE){
                    LinearLayout pregnancyGroup = (LinearLayout) view.findViewById(R.id.donate_pregancy_group);
                    pregnancyGroup.setVisibility(View.GONE);
                }
                break;
            default:
                view = inflater.inflate(R.layout.fragment_donate_start, container, false);
                break;
        }

        return view;
    }
}
