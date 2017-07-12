package com.poussiere.babybooh.welcomeFragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.poussiere.babybooh.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WelcomeFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WelcomeFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment1 extends Fragment {

    private ImageView welcomeLogo;


    public WelcomeFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment WelcomeFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static android.app.Fragment newInstance() {
        WelcomeFragment1 fragment = new WelcomeFragment1();
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
        View layoutView=inflater.inflate(R.layout.fragment_welcome_fragment1, container, false);
        welcomeLogo=(ImageView)layoutView.findViewById(R.id.welcome_logo);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double heightD = width*0.65; // 0,56 est le ratio de l'image
        int height=(int)heightD;
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        welcomeLogo.setLayoutParams(parms);

        return layoutView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
