package com.linwei.github_mvvm.di.module

import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvvm.di.component.BaseFragmentSubComponent
import com.linwei.github_mvvm.di.module.logic.ReposActionListModule
import com.linwei.github_mvvm.di.module.logic.ReposIssueListModule
import com.linwei.github_mvvm.mvvm.ui.module.issue.IssueDetailFragment
import com.linwei.github_mvvm.mvvm.ui.module.login.AccountLoginFragment
import com.linwei.github_mvvm.mvvm.ui.module.login.OAuthLoginFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.DynamicFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.MineFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.RecommendedFragment
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposActionListFragment
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposFileListFragment
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposIssueListFragment
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposReadmeFragment
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
@Module(subcomponents = [BaseFragmentSubComponent::class])
interface FragmentModule {

    /**
     * [AccountLoginFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeAccountLoginFragmentInjector(): AccountLoginFragment

    /**
     * [OAuthLoginFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeOAuthLoginFragmentInjector(): OAuthLoginFragment

    /**
     * [RecommendedFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeRecommendedFragmentInjector(): RecommendedFragment

    /**
     * [DynamicFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeDynamicFragmentInjector(): DynamicFragment

    /**
     * [MineFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeMineFragmentInjector(): MineFragment

    /**
     * [ReposActionListFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector(modules = [ReposActionListModule::class])
    fun contributeReposActionListFragmentInjector(): ReposActionListFragment

    /**
     * [ReposFileListFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeReposFileListFragmentInjector(): ReposFileListFragment

    /**
     * [ReposIssueListFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector(modules = [ReposIssueListModule::class])
    fun contributeReposIssueListFragmentInjector(): ReposIssueListFragment

    /**
     * [ReposReadmeFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeReposReadmeFragmentInjector(): ReposReadmeFragment

    /**
     * [IssueDetailFragment] 注入 `Dagger`
     */
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeIssueDetailFragmentInjector(): IssueDetailFragment

}
