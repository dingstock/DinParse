package com.example.app.callback;

public interface ILoginCallback {

    void onLoginSucceed();

    void onLoginFailed(String errorCode, String errorMsg);

}
