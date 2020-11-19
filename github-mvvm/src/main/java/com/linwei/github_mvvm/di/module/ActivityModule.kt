package com.linwei.github_mvvm.di.module

import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvvm.di.component.BaseActivitySubComponent
import com.linwei.github_mvvm.WelcomActivity
import com.linwei.github_mvvm.di.module.logic.MainModule
import com.linwei.github_mvvm.di.module.logic.ReposDetailModule
import com.linwei.github_mvvm.mvvm.ui.module.issue.IssueDetailActivity
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposDetailActivity
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
     * [WelcomActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeSplashActivityInjector(): WelcomActivity

    /**
     * [UserActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeLoginActivityInjector(): UserActivity

    /**
     * [MainActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivityInjector(): MainActivity

    /**
     * [ReposDetailActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [ReposDetailModule::class])
    fun contributeReposDetailActivityInjector(): ReposDetailActivity

    /**
     * [IssueDetailActivity] 注入 `Dagger`
     */
    @ActivityScope
    @ContributesAndroidInjector
    fun contributeIssueDetailActivityInjector(): IssueDetailActivity

}
