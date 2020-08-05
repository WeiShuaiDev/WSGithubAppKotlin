package com.linwei.cams_mvvm_template.di.module

import com.linwei.cams.di.component.BaseActivitySubComponent
import com.linwei.cams_mvvm_template.mvvm.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module(subcomponents = [BaseActivitySubComponent::class])
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity


}
