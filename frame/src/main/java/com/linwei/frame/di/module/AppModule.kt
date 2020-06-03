package com.linwei.frame.di.module

import android.app.Application
import com.linwei.frame.base.lifecycle.ActivityLifecycle
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module
abstract class AppModule {

    @Binds
    @Named("ActivityLifecycle")
    abstract fun bindActivityLifecycle(activityLifecycle: ActivityLifecycle): Application.ActivityLifecycleCallbacks
}