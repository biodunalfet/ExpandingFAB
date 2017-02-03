package com.hamza.expandingfab;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Hamza Fetuga on 2/3/2017.
 */

public class FastInSlowOutInterpolator implements Interpolator {

    private FastOutSlowInInterpolator interpolator;

    public FastInSlowOutInterpolator(){
        interpolator = new FastOutSlowInInterpolator();
    }

    @Override
    public float getInterpolation(float v) {
        return 1 - interpolator.getInterpolation(v);
    }
}
