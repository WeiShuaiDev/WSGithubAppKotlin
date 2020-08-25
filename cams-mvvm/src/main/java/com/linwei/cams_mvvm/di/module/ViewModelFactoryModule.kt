package com.linwei.cams_mvvm.di.module

import androidx.lifecycle.ViewModelProvider
import com.linwei.cams_mvvm.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/25
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `ViewModel` 中 ` ViewModelFactory` 创建工厂
 *-----------------------------------------------------------------------
 */
@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}