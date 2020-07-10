package com.linwei.cams_mvp.di.module
import android.app.Application
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvp.di.qualifier.ActivityRxLifecycleQualifier
import com.linwei.cams_mvp.lifecycle.ActivityLifecycleForRxLifecycle
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module(includes = [BaseActivityModule.Bindings::class])
object BaseActivityModule {


    @Module
    interface Bindings {
        /**
         * 框架全局 `Activity` 生命周期回调配置
         * @param activityRxLifecycle [ActivityLifecycleForRxLifecycle]
         * @return [Application.ActivityLifecycleCallbacks]
         */
        @ActivityRxLifecycleQualifier
        @ActivityScope
        @Binds
        fun bindActivityRxLifecycle(activityRxLifecycle: ActivityLifecycleForRxLifecycle): Application.ActivityLifecycleCallbacks

    }
}