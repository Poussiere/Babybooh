package com.poussiere.babybooh.welcomeFragments;


import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.poussiere.babybooh.R;


public class WelcomeFragment3 extends Fragment {
  

    private ImageView welcomeLogo3;
    
    public WelcomeFragment3() {
        // Required empty public constructor
    }

    public static WelcomeFragment3 newInstance() {
        WelcomeFragment3 fragment = new WelcomeFragment3();
        
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
          View layoutView =  inflater.inflate(R.layout.fragment_welcome_fragment3, container, false);
   
        
        welcomeLogo3=(ImageView)layoutView.findViewById(R.id.welcome_logo3);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double heightD = width*0.65; // 0,56 est le ratio de l'image
        int height=(int)heightD;
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        welcomeLogo3.setLayoutParams(parms);

        return layoutView;
        
    }

     
}
