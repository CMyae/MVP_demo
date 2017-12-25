package com.material.design.mvpdemo.ui.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.material.design.mvpdemo.utils.ViewUtils;

/**
 * Created by chan on 12/18/17.
 */

public class PopupBox {

    private static final String MENU = "MENU_";

    private String[] menuItems;
    private float x;
    private float y;
    private int desiredWidth = ViewUtils.dpTopx(170);
    private PopupView popupView;

    private Context context;
    private OnMenuClickListener listener;


    public PopupBox(Context context){
        this.context = context;
    }


    public void showIn(String[] menuItems, ViewGroup parent) {

        //remove if there is already existing view
        if (popupView != null) {
            parent.removeView(popupView);
        }


        this.menuItems = menuItems;

        popupView = new PopupView(context);
        popupView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        parent.addView(popupView);

        popupView.setX(x - desiredWidth);
        popupView.setY(y);

        //animate view
        popupView.setScaleY(0f);
        popupView.setPivotY(0f);
        popupView.animate().scaleY(1f).setDuration(200).start();

    }

    public PopupBox x(float X) {
        x = X;
        return this;
    }

    public PopupBox y(float Y) {
        y = Y;
        return this;
    }


    public void hide(){

        if(popupView != null){
            popupView.animate().alpha(0).setDuration(50).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ViewGroup parent = (ViewGroup) popupView.getParent();
                    parent.removeView(popupView);
                    popupView = null;
                }
            });

        }
    }


    public class PopupView extends LinearLayout implements View.OnClickListener {

        public PopupView(Context context) {
            super(context);
            init();
        }

        //create menu item
        private void init() {

            setOrientation(VERTICAL);
            setBackgroundColor(Color.WHITE);
            setElevation(10f);

            for (int i = 0; i < menuItems.length; i++) {

                int padding = ViewUtils.dpTopx(20);

                TextView tvMenu = new TextView(getContext());

                tvMenu.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                tvMenu.setText(menuItems[i]);
                tvMenu.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                tvMenu.setGravity(Gravity.CENTER);
                tvMenu.setPadding(padding, padding, padding, padding);
                tvMenu.setTag(MENU + menuItems[i]);
                addView(tvMenu);

                tvMenu.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {

            String tag = v.getTag().toString();

            if (tag.equals(MENU + "Edit")) {
                listener.onEditMenuClick();

            } else if (tag.equals(MENU + "Delete")) {
                listener.onDeleteMenuClick();

            }
        }
    }


    public interface OnMenuClickListener {
        void onEditMenuClick();

        void onDeleteMenuClick();
    }


    public PopupBox setMenuClickListener(OnMenuClickListener listener) {
        this.listener = listener;
        return this;
    }
}
