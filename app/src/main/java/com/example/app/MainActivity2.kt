package com.example.app

import android.os.Bundle
import android.util.Log
import com.example.app.app.MyApplication
import com.example.app.dagger.ApiComponent
import com.example.app.dagger.DaggerApiComponent
import com.example.app.entity.DcLoginUser
import com.example.app.net.*
import com.google.gson.Gson
import com.parse.Parse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_main2.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.*
import javax.inject.Inject

class MainActivity2 : BaseActivity() {

    @Inject
    lateinit var accountApi: AccountApi

    @Inject
    lateinit var accountApi1: AccountApi

    var dcLoginUser: DcLoginUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
//        val component = DaggerApiComponent.create();
//        DaggerApiComponent.create().inject(this)
        MyApplication.getApiComponent().inject(this)
        login.setOnClickListener {
            login2()
        }
        get_user.setOnClickListener {
            val params: MutableMap<String, Any?> = HashMap()
            params["user"] = dcLoginUser!!.id
            params["app"] = "android1.6.8(20200928)"
            params["version"] = "1.6.6"
            val s = Gson().toJson(params)
            Flowable.just("1")
                    .compose(RxSchedulers.io())
                    .subscribe {
                        val disposable = accountApi.getFllowList(s)
                                .bindDialog(this)
                                .compose(RxSchedulers.netio_main())
                                .subscribe(object : Subscriber<Any> {
                                    override fun onNext(t: Any?) {
                                    }

                                    override fun onError(t: Throwable?) {

                                    }

                                    override fun onComplete() {
                                    }

                                    override fun onSubscribe(s: Subscription?) {
                                    }
                                })
                    }
        }
        send.setOnClickListener {
            sendEvent()
        }
        initParse()
        Log.e("MainActivityAddress","ma2"+accountApi)
        Log.e("MainActivityAddress","ma2"+accountApi1)
    }

    private fun sendEvent() {
        Flowable.just("1", "2")
                .subscribe(object : Subscriber<String> {
                    override fun onComplete() {

                    }

                    override fun onNext(t: String) {
                        Log.e("sendEvent", "$t")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onSubscribe(s: Subscription?) {
                        s?.request(Long.MAX_VALUE)
                    }
                })
//                .subscribe {
//                    Log.e("sendEvent","$it")
//                }
    }

    fun login2() {
        val str = "{\"authData\":{\"sms\":{\"mobilePhoneNumber\":\"19180463647\",\"zone\":\"+86\",\"id\":\"+8619180463647\",\"code\":\"729514\"}}}"
        val disposable = accountApi!!.login(str)
                .compose(RxSchedulers.netio_main())
                .bindDialog(this)
                .flatMap(Function<DcLoginUser, Publisher<DcLoginUser>> { dcLoginUser: DcLoginUser? ->
                    Flowable.create({ e ->
                        dismissPb()
                        e.onNext(dcLoginUser!!)
                    }, BackpressureStrategy.BUFFER)
                })
                .subscribe(
                        {

                        },
                        {

                        })
    }

    private fun login() {
        val str = "{\"authData\":{\"sms\":{\"mobilePhoneNumber\":\"19180463647\",\"zone\":\"+86\",\"id\":\"+8619180463647\",\"code\":\"729514\"}}}"
        val toRequestBody = str.toRequestBody("application/json".toMediaType())
        val disposable = RxRetrofitServiceManager.getInstance()
                .create(DcApiService::class.java)
                .login(toRequestBody)
                .compose(RxSchedulers.netio_main())
                .subscribe(
                        { o: DcLoginUser ->
                            dcLoginUser = o
                        },
                        { _: Throwable? ->
                            Log.e("error", "ssfa")
                        })
    }

    private fun initParse() {
        Parse.setParseDelegate {
            var token = dcLoginUser!!.sessionToken //eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYUUxWGpiQWF3IiwiaWF0IjoxNjAyNTc2NTg3fQ.M9h07bDB9nTsn3NdTHI7_utn1X9lF4b7rRVvlip_LYfs7LnywqtXiZXJ1UVhBAB6Bnjv8aZ41nJbGMIhCdio9A
            token = "111"
            token
        }
    }


}


