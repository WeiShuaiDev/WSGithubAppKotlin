package com.linwei.wsgithubappkotlin.di.component

import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.scope.ApplicationScope
import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.wsgithubappkotlin.di.module.ActivityModule
import com.linwei.wsgithubappkotlin.di.module.FragmentModule
import com.linwei.wsgithubappkotlin.di.module.ViewModelModule
import com.linwei.wsgithubappkotlin.di.scope.TemplateScope
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@TemplateScope
@Component(
    dependencies = [MvvmComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class]
)
interface TemplateComponent {

    /**
     * 注入 [AppLifecycles] 对象
     * @param appLifecycles [AppLifecycles]
     */
    fun inject(appLifecycles: AppLifecycles)

    @Component.Builder
    interface Builder {
        fun mvvmComponent(mvvmComponent: MvvmComponent): Builder

        fun build(): TemplateComponent
    }
}