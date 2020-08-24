package com.linwei.github_mvvm.di.module

import com.linwei.cams_mvvm.di.component.BaseActivitySubComponent
import com.linwei.github_mvvm.SplashActivity
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module(subcomponents = [BaseActivitySubComponent::class])
interface ActivityModule {
    /**
     * [MainActivity] 注入 `Dagger`
     */
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    /**
     * [SplashActivity] 注入 `Dagger`
     */
    @ContributesAndroidInjector
    fun contributeSplashActivityInjector(): SplashActivity

}
