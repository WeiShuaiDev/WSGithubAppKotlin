package com.linwei.github_mvvm.di.component

import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.component.AppComponent
import com.linwei.github_mvvm.di.module.ActivityModule
import com.linwei.github_mvvm.di.module.FragmentModule
import com.linwei.github_mvvm.di.module.ViewModelModule
import com.linwei.github_mvvm.di.scope.GithubScope
import com.linwei.github_mvvm.global.AppLifecycleImpl
import dagger.Component
import dagger.android.AndroidInjectionModule


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@GithubScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class]
)
interface GithubComponent {

    /**
     * 注入 [AppLifecycles] 对象
     * @param appLifecycles [AppLifecycles]
     */
    fun inject(appLifecycles: AppLifecycles)


    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): GithubComponent
    }

}