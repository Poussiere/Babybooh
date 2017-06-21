package com.poussiere.babybooh.welcomeFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment6 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WelcomeFragment6() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment6 newInstance() {
        WelcomeFragment6 fragment = new WelcomeFragment6();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_fragment6, container, false);
    }

}
