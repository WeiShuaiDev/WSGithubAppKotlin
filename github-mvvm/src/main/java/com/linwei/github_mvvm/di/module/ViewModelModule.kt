package com.linwei.github_mvvm.di.module

import androidx.lifecycle.ViewModel
import com.linwei.cams_mvvm.di.scope.ViewModelKey
import com.linwei.github_mvvm.mvvm.viewmodel.event.issue.IssueDetailViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.event.person.PersonViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.event.push.PushDetailViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.login.AccountLoginViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.login.OAuthLoginViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.DynamicViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.MainViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.MineViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.RecommendedViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposDetailViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposReadmeViewModel
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
     * [MainViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    /**
     * [RecommendedViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(RecommendedViewModel::class)
    fun bindRecommendedViewModel(viewModel: RecommendedViewModel): ViewModel

    /**
     * [DynamicViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(DynamicViewModel::class)
    fun bindDynamicViewModel(viewModel: DynamicViewModel): ViewModel

    /**
     * [MineViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    fun bindMineViewModel(viewModel: MineViewModel): ViewModel

    /**
     * [AccountLoginViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(AccountLoginViewModel::class)
    fun bindAccountLoginViewModel(viewModel: AccountLoginViewModel): ViewModel

    /**
     * [OAuthLoginViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(OAuthLoginViewModel::class)
    fun bindOAuthLoginViewModel(viewModel: OAuthLoginViewModel): ViewModel

    /**
     * [ReposDetailViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(ReposDetailViewModel::class)
    fun bindReposDetailViewModel(viewModel: ReposDetailViewModel): ViewModel

    /**
     * [IssueDetailViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(IssueDetailViewModel::class)
    fun bindIssueDetailViewModel(viewModel: IssueDetailViewModel): ViewModel

    /**
     * [PersonViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    fun bindPersonViewModel(viewModel: PersonViewModel): ViewModel

    /**
     * [PushDetailViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(PushDetailViewModel::class)
    fun bindPushDetailViewModel(viewModel: PushDetailViewModel): ViewModel

    /**
     * [ReposReadmeViewModel] 注入 `Dagger`
     */
    @Binds
    @IntoMap
    @ViewModelKey(ReposReadmeViewModel::class)
    fun bindReposReadmeViewModel(viewModel: ReposReadmeViewModel): ViewModel


}
