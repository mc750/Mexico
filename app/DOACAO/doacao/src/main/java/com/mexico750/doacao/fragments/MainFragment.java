package com.mexico750.doacao.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mexico750.doacao.R;
import com.mexico750.doacao.activities.MainActivity;
import com.mexico750.doacao.user.User;
import com.mexico750.doacao.utils.JsonUtils;

/**
 * Created by root on 08/06/14.
 */
public class MainFragment extends Fragment{

    private static final String USER_ARG = "USER";
    private User user;

    public static MainFragment newInstance(User user) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        if (user != null) {
            args.putString(USER_ARG, JsonUtils.getJson(user));
        }

        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            user = JsonUtils.getObject(getArguments().getString(USER_ARG, "{}"), User.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (user != null && user.getName() != null) {
            TextView userNameView = (TextView) view.findViewById(R.id.home_user_name);
            userNameView.setText(user.getName().split(" ")[0]);
        }

        return view;
    }
}
