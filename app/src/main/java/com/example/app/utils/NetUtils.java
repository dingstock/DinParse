package com.example.app.utils;

import androidx.annotation.NonNull;

import com.example.app.callback.ILoginCallback;
import com.example.app.user.DCUser;
import com.parse.ParseUser;
import com.parse.boltsinternal.Continuation;
import com.parse.boltsinternal.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WhenYoung
 * CreateAt Time 2020/10/12  17:35
 */
public class NetUtils {
    /**
     * 手机号码直接登录
     */
    public static void loginWithSms(final String areaCode,
                             final String phoneNum,
                             final String code,
                             @NonNull final ILoginCallback callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("id", "+" + areaCode + phoneNum);
        params.put("zone", "+" + areaCode);
        params.put("mobilePhoneNumber", phoneNum);
        params.put("code", code);
        DCUser.logInWithInBackground("sms", params).
                continueWith(new Continuation<ParseUser, Object>() {
                    @Override
                    public Object then(Task<ParseUser> task) throws Exception {
                        Exception error = task.getError();
                        if (null != error) {
                            callback.onLoginFailed(error.getLocalizedMessage(), error.getMessage());
                            return null;
                        }
                        ParseUser result = task.getResult();
                        if (null == result) {
                            callback.onLoginFailed("-1", "result empty");
                            return null;
                        }
                        callback.onLoginSucceed();
                        //存储手机号
                        return null;
                    }
                });
    }
}
