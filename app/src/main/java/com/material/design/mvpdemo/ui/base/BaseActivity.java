package com.material.design.mvpdemo.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.utils.CommonUtils;

/**
 * Created by chan on 12/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView{

    protected ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());


        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);

        //create typeface
        if(headerLayout != null) {
            TextView tvHeaderFirst = (TextView) headerLayout.getChildAt(0);
            TextView tvHeaderSecond = (TextView) headerLayout.getChildAt(1);

            Typeface typeface = CommonUtils.getHeaderTypeface(this);
            tvHeaderFirst.setTypeface(typeface);
            tvHeaderSecond.setTypeface(typeface);
        }
    }


    @Override
    public void showLoadingProgress(String message) {
        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void hideLoadingProgress() {
        if(dialog != null){
            dialog.dismiss();
        }
    }


    @Override
    public void navigateToHome() {

    }


    @Override
    public void showInputErrors(String message) {

    }


    @Override
    public Context getAppContext() {
        return this;
    }

    /**
     * get layout resource file based on child activities
     * @return
     */
    protected abstract int getLayoutResourceId();
}
