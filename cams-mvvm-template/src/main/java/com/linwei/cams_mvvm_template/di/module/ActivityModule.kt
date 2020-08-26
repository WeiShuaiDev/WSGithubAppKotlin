package com.linwei.cams_mvvm_template.di.module

import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvvm.di.component.BaseActivitySubComponent
import com.linwei.cams_mvvm_template.SplashActivity
import com.linwei.cams_mvvm_template.mvvm.ui.module.main.MainActivity
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

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity


    /**
     * [SplashActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeSplashActivityInjector(): SplashActivity


}
