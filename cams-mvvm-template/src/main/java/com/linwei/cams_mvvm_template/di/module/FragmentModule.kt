package com.linwei.cams_mvvm_template.di.module

import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvvm.di.component.BaseFragmentSubComponent
import com.linwei.cams_mvvm_template.mvvm.ui.module.login.LoginFragment
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
@Module(subcomponents = [BaseFragmentSubComponent::class])
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun contributeLoginFragmentInjector(): LoginFragment


}
