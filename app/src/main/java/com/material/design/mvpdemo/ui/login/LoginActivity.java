package com.material.design.mvpdemo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.material.design.mvpdemo.R;
import com.material.design.mvpdemo.ui.base.BaseActivity;
import com.material.design.mvpdemo.ui.main.MainActivity;
import com.material.design.mvpdemo.ui.signup.SignUpActivity;
import com.material.design.mvpdemo.utils.Constants;
import com.material.design.mvpdemo.utils.ViewUtils;


public class LoginActivity extends BaseActivity implements LoginView{


    private EditText etEmail;
    private EditText etPwd;
    private LoginMvpPresenter loginPresenter;

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

        etEmail = (EditText) findViewById(R.id.email_text_input);
        etPwd = (EditText) findViewById(R.id.password_text_input);

        TextView btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnSignIn.setBackground(ViewUtils.getStateListDrawable(
                Constants.colorNormal, Constants.colorPress
        ));

        loginPresenter = new LoginPresenter(this);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }


    /**
     * OnClick event of login button
     * @param view
     */
    public void onLoginBtnClick(View view) {

        String email = etEmail.getText().toString();
        String password = etPwd.getText().toString();

        loginPresenter.onLoginBtnClick(email,password);

    }

    /**
     * OnClick event when click create new account
     * @param view
     */
    public void onCreateAccClick(View view) {
        loginPresenter.onCreateAccClick();
    }


    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showInputErrors(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void navigateToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }
}
