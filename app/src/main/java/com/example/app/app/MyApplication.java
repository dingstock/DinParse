package com.example.app.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.app.BuildConfig;
import com.example.app.dagger.ApiComponent;
import com.example.app.dagger.DaggerApiComponent;
import com.example.app.user.DCUser;
import com.example.app.utils.HttpsCertUtils;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author WhenYoung
 * CreateAt Time 2020/10/12  17:13
 */
public class MyApplication extends Application {
    private static final int CONNECT_TIMEOUT = 15; // client请求超时时间
    private static final int READ_TIMEOUT = 30; // 读超时
    private static final int WRITE_TIMEOUT = 30; // 写超时
    private static OkHttpClient mDefaultClient = null;
    private static String serverUrl = "https://api.dingstock.net";

    private static ApiComponent apiComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
        initInjectApi();
    }
    private static OkHttpClient.Builder getHttpClientBuilder() {
        HttpsCertUtils.SSLParams sslParams = HttpsCertUtils.getSSLParams(HttpsCertUtils.Protocol.SSL,
                null, null, null);
        return new OkHttpClient().newBuilder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false);
    }

    public void init(Context context) {
        if (BuildConfig.DEBUG) {
            SharedPreferences share = context.getSharedPreferences("parse_env", Context.MODE_PRIVATE);
            serverUrl = share.getString("env", "https://9ff0f7477c056646c6028db121642b2f.dingstock.net");
        }

        ParseUser.registerSubclass(DCUser.class);
        Parse.enableLocalDatastore(context);
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId("K2Dw31SA6bpDIDWEQQe409F8Bkp1PuPYnFqDiHDL")
                .maxRetries(0)
                .clientBuilder(getHttpClientBuilder())
                .clientKey("")
                .server(serverUrl)
                .build()
        );
        Parse.checkInit();

    }

    public void initInjectApi(){
        apiComponent = DaggerApiComponent.create();
    }

    public static ApiComponent getApiComponent() {
        return apiComponent;
    }
}
