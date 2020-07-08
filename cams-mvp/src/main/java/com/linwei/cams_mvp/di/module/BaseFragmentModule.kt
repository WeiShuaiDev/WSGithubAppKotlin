package com.linwei.cams_mvp.di.module

import android.app.Application
import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvp.di.qualifier.ActivityRxLifecycleQualifier
import com.linwei.cams_mvp.di.qualifier.FragmentRxLifecycleQualifier
import com.linwei.cams_mvp.lifecycle.ActivityLifecycleForRxLifecycle
import com.linwei.cams_mvp.lifecycle.FragmentLifecycleForRxLifecycle
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
@Module(includes = [BaseFragmentModule.Bindings::class])
object BaseFragmentModule {

    @Module
    interface Bindings {

        /**
         * 框架全局 `Fragment` 生命周期回调配置
         * @param fragmentRxLifecycle [FragmentLifecycleForRxLifecycle]
         * @return [FragmentManager.FragmentLifecycleCallbacks]
         */
        @FragmentRxLifecycleQualifier
        @FragmentScope
        @Binds
        fun bindFragmentRxLifecycle(fragmentRxLifecycle: FragmentLifecycleForRxLifecycle): FragmentManager.FragmentLifecycleCallbacks
    }

}