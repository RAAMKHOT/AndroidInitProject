package com.infinite.mysecurityfirst.ui.login;

import android.text.TextUtils;

import com.infinite.mysecurityfirst.ui.base.BaseViewModel;
import com.infinite.mysecurityfirst.utils.CommonUtils;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void login() {
        getNavigator().login();
    }
}
