package com.material.design.mvpdemo.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;

/**
 * Created by chan on 12/18/17.
 */

public class ViewUtils {


    public static int dpTopx(int dp){
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }

    /**
     * Get circle shape drawable with random colors
     * @return
     */
    public static GradientDrawable getRoundDrawable(int color){

        GradientDrawable gd = new GradientDrawable();
        //gd.setColor(Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
        gd.setColor(Color.HSVToColor(new float[]{color,1f,0.95f}));
        gd.setShape(GradientDrawable.OVAL);
        return gd;

    }


    public static GradientDrawable getStrokeDrawable(int strokeColor){
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(dpTopx(1),strokeColor);
        return gd;
    }


    /**
     * Get dynamic button coloring by state,normal and press state
     * @param normalColor
     * @param pressedColor
     * @return
     */
    public static StateListDrawable getStateListDrawable(int normalColor, int pressedColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressedColor));
        stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(normalColor));
        return stateListDrawable;
    }


    public static int[] getLocation(View v){
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        location[0] += v.getWidth();
        location[1] -= v.getHeight()/2;
        return location;
    }

}
