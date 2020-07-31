package com.linwei.cams_mvvm.di.component

import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/31
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@ApplicationScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class]
)
interface MvvmComponent {

    /**
     * 注入 [AppLifecycles] 对象
     * @param appLifecycles [AppLifecycles]
     */
    fun inject(appLifecycles: AppLifecycles)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): MvvmComponent
    }
}