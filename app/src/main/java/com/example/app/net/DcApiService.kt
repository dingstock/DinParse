package com.example.app.net

import com.example.app.entity.DcLoginUser
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/13  17:35
 */
interface DcApiService {

    @POST("/users")
    fun login(@Body jsonStr : RequestBody) : Flowable<DcLoginUser>

    @POST("functions/userFollowList")
    fun getFllowList(@Body jsonStr : RequestBody) : Flowable<Any>



}