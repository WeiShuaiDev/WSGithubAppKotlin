package com.linwei.cams_aac.di.component

import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.ApplicationScope
import com.linwei.cams_aac.di.module.AacModelModule
import com.linwei.cams_aac.di.module.AacViewModelModule
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
    modules = [AndroidInjectionModule::class, AacViewModelModule::class, AacModelModule::class]
)
interface AacComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): AacComponent
    }
}