package com.infinite.mysecurityfirst.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.infinite.mysecurityfirst.BR;
import com.infinite.mysecurityfirst.R;
import com.infinite.mysecurityfirst.databinding.ActivityLoginBinding;
import com.infinite.mysecurityfirst.ui.base.BaseActivity;
import com.infinite.mysecurityfirst.ui.main.MainActivity;

/**
 * Created by Ramagouda Khot on 27-01-2022.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = new LoginViewModel();
        return mLoginViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
        String email = mActivityLoginBinding.etEmail.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();


        if (mLoginViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            openMainActivity();
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
        /*----Hardcode to dashboard-----*/
        openMainActivity();
        /*------------------------------*/
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
    }
}
