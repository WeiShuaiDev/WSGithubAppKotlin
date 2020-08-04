package com.linwei.wsgithubappkotlin.di.module
import com.linwei.cams.di.component.BaseActivitySubComponent
import com.linwei.wsgithubappkotlin.mvvm.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
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
