package com.example.app.net

import com.example.app.BaseActivity
import com.example.app.entity.DcLoginUser
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/14  10:59
 */
class AccountApi @Inject constructor(retrofit: Retrofit) : BaseApi<DcApiService>(retrofit) {

    fun login(str: String): Flowable<DcLoginUser> {
        val create = RequestBody.create("application/json".toMediaType(), str)
        return service.login(create)
    }


    fun getFllowList(str: String): Flowable<Any> {
        val create = RequestBody.create("application/json".toMediaType(), str)
        return service.getFllowList(create)
    }

}



