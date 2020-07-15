package com.linwei.cams_aac.di.module

import androidx.lifecycle.ViewModelProvider
import com.linwei.cams_aac.acc.ViewModelFactory
import com.linwei.cams_aac.di.scope.ViewModelScope
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module
interface ViewModelFactoryModule {

    @ViewModelScope
    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


}