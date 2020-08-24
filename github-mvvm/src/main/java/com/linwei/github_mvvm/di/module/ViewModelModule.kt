package com.linwei.github_mvvm.di.module

import com.linwei.cams_mvvm.di.scope.ViewModelKey
import com.linwei.github_mvvm.di.scope.GithubScope
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import com.linwei.github_mvvm.mvvm.viewmodel.main.DynamicViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.MineViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.RecommendedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module
interface ViewModelModule {
    /**
     * [RecommendedViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @GithubScope
    @ViewModelKey(RecommendedViewModel::class)
    fun bindRecommendedViewModel(viewModel: RecommendedViewModel): RecommendedContract.ViewModel

    /**
     * [DynamicViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @GithubScope
    @ViewModelKey(DynamicViewModel::class)
    fun bindDynamicViewModel(viewModel: DynamicViewModel): DynamicContract.ViewModel

    /**
     * [MineViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @GithubScope
    @ViewModelKey(MineViewModel::class)
    fun bindMineViewModel(viewModel: MineViewModel): MineContract.ViewModel

}
