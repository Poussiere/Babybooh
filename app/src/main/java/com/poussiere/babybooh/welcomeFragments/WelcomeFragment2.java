package com.poussiere.babybooh.welcomeFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poussiere.babybooh.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment2 extends Fragment {
   
    
    private ImageView welcomeLogo2;
    
    public WelcomeFragment2() {
        // Required empty public constructor
    }

  
    public static WelcomeFragment2 newInstance() {
        WelcomeFragment2 fragment = new WelcomeFragment2();
  
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
        View layoutView = inflater.inflate(R.layout.fragment_welcome_fragment2, container, false);
        
        welcomeLogo2=(ImageView)layoutView.findViewById(R.id.welcome_logo2);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double heightD = width*0.65; // 0,56 est le ratio de l'image
        int height=(int)heightD;
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        welcomeLogo2.setLayoutParams(parms);

        return layoutView;
        
    }

}
