package com.mexico750.doacao.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mexico750.doacao.R;

/**
 * Created by root on 08/06/14.
 */
public class InformationFragment extends Fragment{

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment ();
        return fragment;
    }

    public InformationFragment () { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }
}
