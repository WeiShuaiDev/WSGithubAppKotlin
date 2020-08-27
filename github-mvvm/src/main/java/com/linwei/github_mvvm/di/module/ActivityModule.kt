package com.linwei.github_mvvm.di.module

import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvvm.di.component.BaseActivitySubComponent
import com.linwei.github_mvvm.SplashActivity
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
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
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeMainActivityInjector(): MainActivity

    /**
     * [UserActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeLoginActivityInjector():UserActivity

    /**
     * [SplashActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeSplashActivityInjector(): SplashActivity

}
