package com.hamza.expandingfab;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Hamza Fetuga on 2/3/2017.
 */

public class FastInSlowOutInterpolator implements Interpolator {

    FastOutSlowInInterpolator interpolator;

    public FastInSlowOutInterpolator(){
        this.interpolator  = new FastOutSlowInInterpolator();
    }

    @Override
    public float getInterpolation(float t) {

        float t2 = t * t;
        float t3 = t2 * t;
        float mt = 1-t;
        float mt2 = mt * mt;
        float mt3 = mt2 * mt;
        return (float)(3* 0.5*mt2*t + 3*0.67*mt*t2 + t3);
//        return interpolator.getInterpolation(1 - v);
    }

}
