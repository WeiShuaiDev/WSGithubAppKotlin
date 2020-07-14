package com.linwei.cams_mvp.di.component

import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvp.di.module.BaseFragmentModule
import dagger.BindsInstance
import dagger.Component

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [BaseFragmentModule::class])
interface BaseFragmentComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): BaseFragmentComponent
    }

}