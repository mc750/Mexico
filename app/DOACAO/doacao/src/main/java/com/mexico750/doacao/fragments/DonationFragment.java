package com.mexico750.doacao.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mexico750.doacao.R;
import com.mexico750.doacao.user.Gender;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;
import org.joda.time.Years;


public class DonationFragment extends Fragment {
    private static final String DONATION_PAGE = "PAGE";
    private static final String DONATION_USER = "USER";

    private static Integer currentPage;
    private static User user;

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
            default:
                view = inflater.inflate(R.layout.fragment_donate_first, container, false);
                break;
        }

        return view;
    }
}
