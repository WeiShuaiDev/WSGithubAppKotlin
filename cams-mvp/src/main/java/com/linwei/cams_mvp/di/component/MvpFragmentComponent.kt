package com.linwei.cams_mvp.di.component

import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvp.di.module.MvpFragmentModule
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
@Component(dependencies = [AppComponent::class], modules = [MvpFragmentModule::class])
interface MvpFragmentComponent {


    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): MvpFragmentComponent
    }

}