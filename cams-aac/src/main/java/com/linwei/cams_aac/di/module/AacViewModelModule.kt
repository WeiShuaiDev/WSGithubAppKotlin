package com.linwei.cams_aac.di.module

import androidx.lifecycle.ViewModelProvider
import com.linwei.cams_aac.aac.ViewModelFactory
import com.linwei.cams_aac.di.scope.ViewModelScope
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module
interface AacViewModelModule {

    @ViewModelScope
    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}