package com.example.app.dagger

import android.util.Log
import com.example.app.net.AccountApi
import com.example.app.net.RxRetrofitServiceManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/14  11:13
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun providerAccountApi(retrofit: Retrofit): AccountApi {
        return AccountApi(retrofit)
    }

    @Provides
    fun providerRetrofit():Retrofit{
        Log.e("ApiModule","type1")
        return RxRetrofitServiceManager.getInstance().getmRetrofit()
    }



}