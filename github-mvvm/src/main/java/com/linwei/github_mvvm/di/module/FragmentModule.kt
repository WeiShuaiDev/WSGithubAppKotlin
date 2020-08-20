package com.linwei.github_mvvm.di.module

import com.linwei.cams.di.component.BaseFragmentSubComponent
import com.linwei.github_mvvm.mvvm.ui.module.main.DynamicFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import com.linwei.github_mvvm.mvvm.ui.module.main.MineFragment
import com.linwei.github_mvvm.mvvm.ui.module.main.RecommendedFragment
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
     * [RecommendedFragment] 注入 `Dagger`
     */
    @ContributesAndroidInjector
    fun contributeRecommendedFragmentInjector(): RecommendedFragment

    /**
     * [DynamicFragment] 注入 `Dagger`
     */
    @ContributesAndroidInjector
    fun contributeDynamicFragmentInjector(): DynamicFragment

    /**
     * [MineFragment] 注入 `Dagger`
     */
    @ContributesAndroidInjector
    fun contributeMineFragmentInjector(): MineFragment

}
