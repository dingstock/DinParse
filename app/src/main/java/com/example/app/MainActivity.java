package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.app.app.MyApplication;
import com.example.app.dagger.DaggerApiComponent;
import com.example.app.entity.DcLoginUser;
import com.example.app.net.AccountApi;
import com.example.app.net.DcApiService;
import com.example.app.net.RxRetrofitServiceManager;
import com.example.app.net.RxSchedulers;
import com.google.gson.Gson;
import com.parse.DcParse;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.delegate.ParseDelegate;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.internal.DaggerCollections;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity {

    @Inject
    AccountApi accountApi;

    @Inject
    AccountApi accountApi1;


    DcLoginUser dcLoginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DaggerApiComponent.create().inject(this);
        MyApplication.getApiComponent().inject(this);
//        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                login();
//                showPb();
//                login2();
//            }
//        });
//        findViewById(R.id.get_user).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                DcParse.testPrint();
////                Map<String,Object> params = new HashMap<>();
////                if(dcLoginUser==null){
////                    return;
////                }
////                params.put("user",dcLoginUser.getId());
////                params.put("app","android1.6.8(20200928)");
////                params.put("version","1.6.6");
////                ParseCloud.callFunctionInBackground("userFollowList", params, new FunctionCallback<Object>() {
////                    @Override
////                    public void done(Object object, ParseException e) {
////                        String s = new Gson().toJson(object);
////                        Log.e("tag","????");
////                    }
////                });
//                Map<String,Object> params = new HashMap<>();
//                params.put("user",dcLoginUser.getId());
//                params.put("app","android1.6.8(20200928)");
//                params.put("version","1.6.6");
//                String s = new Gson().toJson(params);
//                showPb();
//                Disposable disposable =  accountApi.getFllowList(s)
//                        .compose(RxSchedulers.netio_main())
//                        .subscribe(
//                                (Consumer<Object>) o -> Log.e("","")
//                                ,(error)-> {
//                                    Log.e("error","ssfa");
//                                });
//
//            }
//        });
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        Log.e("MainActivityAddress","ma1"+accountApi);
        Log.e("MainActivityAddress","ma1"+accountApi1);
    }

    public void login2(){
        String str = "{\"authData\":{\"sms\":{\"mobilePhoneNumber\":\"19180463647\",\"zone\":\"+86\",\"id\":\"+8619180463647\",\"code\":\"729514\"}}}";
        Disposable disposable =  accountApi.login(str)
                .compose(RxSchedulers.netio_main())
                .flatMap((Function<DcLoginUser, Publisher<DcLoginUser>>) dcLoginUser -> Flowable.create(new FlowableOnSubscribe<DcLoginUser>() {
                    @Override
                    public void subscribe(FlowableEmitter<DcLoginUser> e) throws Exception {
                        dismissPb();
                        e.onNext(dcLoginUser);
                    }
                }, BackpressureStrategy.BUFFER))
                .subscribe(
                        (Consumer<DcLoginUser>) o -> dcLoginUser = o
                        ,(error)-> {
                            Log.e("error","ssfa");
                        });
    }

    private void login() {
        String str = "{\"authData\":{\"sms\":{\"mobilePhoneNumber\":\"19180463647\",\"zone\":\"+86\",\"id\":\"+8619180463647\",\"code\":\"729514\"}}}";
        RequestBody requestBody = RequestBody.Companion.create(str, MediaType.get("application/json"));

        Disposable disposable = RxRetrofitServiceManager.getInstance()
                .create(DcApiService.class)
                .login(requestBody)
                .compose(RxSchedulers.netio_main())
                .subscribe(
                        (Consumer<DcLoginUser>) o -> dcLoginUser = o
                        ,(error)-> {
                            Log.e("error","ssfa");
                });



    }

    private void initParse() {
        Parse.setParseDelegate(new ParseDelegate() {
            @Override
            public String getSession() {
                String token = dcLoginUser.getSessionToken();//eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYUUxWGpiQWF3IiwiaWF0IjoxNjAyNTc2NTg3fQ.M9h07bDB9nTsn3NdTHI7_utn1X9lF4b7rRVvlip_LYfs7LnywqtXiZXJ1UVhBAB6Bnjv8aZ41nJbGMIhCdio9A
                token = "111";
                return token;
            }
        });

    }


}
