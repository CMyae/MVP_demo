package com.material.design.mvpdemo.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.ui.base.BaseActivity;
import com.material.design.mvpdemo.ui.login.LoginActivity;
import com.material.design.mvpdemo.ui.main.MainActivity;
import com.material.design.mvpdemo.utils.Constants;
import com.material.design.mvpdemo.utils.ViewUtils;

public class SignUpActivity extends BaseActivity implements SignupView{

    private EditText etName,etEmail,etPwd;
    private SignupMvpPresenter singupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //navigate to home if user already log in
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return;
        }

        etName = (EditText) findViewById(R.id.name_text_input);
        etEmail = (EditText) findViewById(R.id.email_text_input);
        etPwd = (EditText) findViewById(R.id.password_text_input);

        TextView btnSignUp = (TextView) findViewById(R.id.btnSignUp);
        btnSignUp.setBackground(ViewUtils.getStateListDrawable(
                Constants.colorNormal, Constants.colorPress
        ));

        singupPresenter = new SignupPresenter(this);


    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sing_up;
    }


    /**
     * OnClick event of sing up btn
     * @param view
     */
    public void onSingUpBtnClick(View view) {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPwd.getText().toString();

        singupPresenter.onSignupBtnClick(name,email,password);

    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showInputErrors(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getAppContext() {
        return this;
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onTvLoginClick(View view) {
        singupPresenter.onTvLoginClick();
    }
}
