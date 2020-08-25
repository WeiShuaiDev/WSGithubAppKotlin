package com.linwei.github_mvvm.di.component

import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.cams_mvvm.di.module.ViewModelFactoryModule
import com.linwei.github_mvvm.GithubApplication
import com.linwei.github_mvvm.di.module.ActivityModule
import com.linwei.github_mvvm.di.module.FragmentModule
import com.linwei.github_mvvm.di.module.ViewModelModule
import com.linwei.github_mvvm.di.scope.GithubScope
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
    dependencies = [MvvmComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class, ViewModelFactoryModule::class]
)
interface GithubComponent {

    /**
     * 注入 [GithubApplication] 对象
     * @param application [GithubApplication]
     */
    fun inject(application: GithubApplication)

    @Component.Builder
    interface Builder {
        fun appComponent(mvvmComponent: MvvmComponent): Builder

        fun build(): GithubComponent
    }

}