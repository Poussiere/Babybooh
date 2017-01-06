package com.poussiere.babybooh.mainFragment3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by poussiere on 2/7/16.
 */
public class ParallaxMonster extends ScrollView {

        public interface OnScrollChangedListener
        {public void onScrollChanged(int deltaX, int deltaY);}


        private OnScrollChangedListener onScrollChangedListener ;

        public ParallaxMonster (Context context)
        {super (context);}

        public ParallaxMonster (Context context, AttributeSet attrs)
        {super (context, attrs);}

        public ParallaxMonster (Context context, AttributeSet attrs, int defStyle)
        {super(context, attrs, defStyle);}

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(l - oldl, t - oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        onScrollChangedListener = listener;
    }
}




