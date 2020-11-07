package com.example.app.net

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import com.example.app.BaseActivity
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/14  10:20
 */
open class BaseApi<T> {
    val service: T

    @Inject
    constructor(retrofit: Retrofit) {
        val type = (javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments
        val clasz = type?.get(0) as Class<T>
        service = retrofit.create(clasz)
    }

}

@SuppressLint("CheckResult")
fun <T> Flowable<T>.bindDialog(activity: BaseActivity): Flowable<T> {
    if(Looper.getMainLooper().thread.id == Thread.currentThread().id){
        activity.showPb()
    }else{
        activity.runOnUiThread { activity.showPb() }
    }
    Thread.sleep(4000)
    return observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                activity.dismissPb()
            }

}

