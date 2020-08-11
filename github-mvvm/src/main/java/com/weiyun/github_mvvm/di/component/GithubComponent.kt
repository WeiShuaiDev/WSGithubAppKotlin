package com.weiyun.github_mvvm.di.component

import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.weiyun.github_mvvm.di.module.ActivityModule
import com.weiyun.github_mvvm.di.module.FragmentModule
import com.weiyun.github_mvvm.di.module.ViewModelModule
import com.weiyun.github_mvvm.di.scope.GithubScope
import dagger.Component
import dagger.android.AndroidInjectionModule


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@GithubScope
@Component(
    dependencies = [MvvmComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class]
)
interface GithubComponent {

//    /**
//     * 注入 [AppLifecycles] 对象
//     * @param appLifecycles [AppLifecycles]
//     */
//    fun inject(appLifecycles: AppLifecycles)


}