package com.linwei.cams_mvp.di.module

import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvp.di.qualifier.FragmentRxLifecycleQualifier
import com.linwei.cams_mvp.global.FragmentLifecycleMvpImpl
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  `MVP` 架构 `Fragment` 中 `Module`模块,提供生命周期注入。
 *-----------------------------------------------------------------------
 */
@Module(includes = [MvpFragmentModule.Bindings::class])
object MvpFragmentModule {

    @Module
    interface Bindings {

        /**
         * 框架全局 `Fragment` 生命周期回调配置
         * @param fragmentRxLifecycle [FragmentLifecycleMvpImpl]
         * @return [FragmentManager.FragmentLifecycleCallbacks]
         */
        @FragmentRxLifecycleQualifier
        @FragmentScope
        @Binds
        fun bindFragmentRxLifecycle(fragmentRxLifecycle: FragmentLifecycleMvpImpl): FragmentManager.FragmentLifecycleCallbacks
    }

}