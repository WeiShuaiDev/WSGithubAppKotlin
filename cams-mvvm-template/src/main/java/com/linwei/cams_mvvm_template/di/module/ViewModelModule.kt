package com.linwei.cams_mvvm_template.di.module

import androidx.lifecycle.ViewModel
import com.linwei.cams_mvvm.di.scope.ViewModelKey
import com.linwei.cams_mvvm_template.mvvm.viewmodel.login.LoginViewModel
import com.linwei.cams_mvvm_template.mvvm.viewmodel.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `ViewModelModule` 中 `Module`模块,提供注入 `ViewModel` 类。
 *-----------------------------------------------------------------------
 */
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}
