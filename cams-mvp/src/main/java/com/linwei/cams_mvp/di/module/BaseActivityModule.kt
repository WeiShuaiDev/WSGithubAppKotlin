package com.linwei.cams_mvp.di.module

import android.app.Application
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.lifecycle.ActivityLifecycle
import com.linwei.cams.base.lifecycle.FragmentLifecycle
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvp.di.qualifier.ActivityRxLifecycleQualifier
import com.linwei.cams_mvp.di.qualifier.FragmentRxLifecycleQualifier
import com.linwei.cams_mvp.lifecycle.ActivityLifecycleForRxLifecycle
import com.linwei.cams_mvp.lifecycle.ActivityRxLifecycle
import com.linwei.cams_mvp.lifecycle.FragmentLifecycleForRxLifecycle
import com.linwei.cams_mvp.lifecycle.FragmentRxLifecycle
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

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