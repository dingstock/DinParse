package com.example.app.net;


import android.util.Log;

import com.example.app.utils.HttpsCertUtils;
import com.parse.Parse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;

    private RxRetrofitServiceManager(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间

        //添加证书
        HttpsCertUtils.SSLParams sslParams = HttpsCertUtils.getSSLParams(HttpsCertUtils.Protocol.SSL,
                null, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

//         添加公共参数拦截器
        Interceptor commonInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type","application/json")
                        .addHeader("x-parse-application-id","K2Dw31SA6bpDIDWEQQe409F8Bkp1PuPYnFqDiHDL")
                        .addHeader("x-parse-app-build-version","20200928")
                        .addHeader("x-parse-app-display-version","1.6.8")
                        .addHeader("x-parse-os-version","7.1.2")
                        .addHeader("x-parse-installation-id","dedff315-7cc1-4e25-be32-871b90d28285")
                        .build();
                Response response = chain.proceed(request);

                /*这里可以获取响应体 */
                ResponseBody responseBody = response.peekBody(1024 * 1024);
                String result = responseBody.string();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(result));
                    int code = jsonObject.getInt("code");
                    Log.e("wenyLog",code+"=======================");
                    throw new IOException(code+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("wenyLog",result);
                return response;
            }
        };
        builder.addInterceptor(commonInterceptor);

//        CallAdapter callAdapter = new CallAdapter() {
//            @Override
//            public Type responseType() {
//                return null;
//            }
//
//            @Override
//            public Object adapt(Call call) {
//                return null;
//            }
//        };

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("www.baidu.com")
                .build();
    }

    //用的是静态内部类方式实现的懒汉线程安全单列
    private static class SingletonHolder{
        private static final RxRetrofitServiceManager INSTANCE = new RxRetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RxRetrofitServiceManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }
}