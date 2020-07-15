package com.linwei.cams_aac.di.component

import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.ApplicationScope
import com.linwei.cams_aac.di.module.BaseActivityModule
import com.linwei.cams_aac.di.module.BaseFragmentModule
import com.linwei.cams_aac.di.module.ViewModelFactoryModule
import dagger.BindsInstance
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
@ApplicationScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class, BaseActivityModule::class,
        BaseFragmentModule::class, ViewModelFactoryModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): com.linwei.cams_aac.di.component.AppComponent
    }
}