package com.infinite.mysecurityfirst.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.infinite.mysecurityfirst.BR;
import com.infinite.mysecurityfirst.R;
import com.infinite.mysecurityfirst.databinding.ActivitySplashBinding;
import com.infinite.mysecurityfirst.ui.base.BaseActivity;
import com.infinite.mysecurityfirst.ui.login.LoginActivity;
import com.infinite.mysecurityfirst.ui.main.MainActivity;
/**
 * Created by Ramagouda Khot on 27-01-2022.
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    private SplashViewModel mSplashViewModel;
    Handler handler;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = new SplashViewModel();
        return mSplashViewModel;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSplashViewModel.setNavigator(this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              openLoginActivity();
            }
        }, 3000);
    }
}
