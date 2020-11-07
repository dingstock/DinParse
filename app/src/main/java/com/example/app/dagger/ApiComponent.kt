package com.example.app.dagger

import com.example.app.MainActivity
import com.example.app.MainActivity2
import dagger.Component
import javax.inject.Singleton

/**
 * @author WhenYoung
 *  CreateAt Time 2020/10/14  11:15
 */
@Singleton
@Component(modules = [ApiModule::class])
public interface ApiComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(mainActivity: MainActivity2)



}