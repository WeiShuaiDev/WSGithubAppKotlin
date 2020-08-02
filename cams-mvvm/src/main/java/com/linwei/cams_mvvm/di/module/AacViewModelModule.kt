package com.linwei.cams_mvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linwei.cams_mvvm.mvvm.ViewModelFactory
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.cams_mvvm.di.scope.ViewModelKey
import com.linwei.cams_mvvm.di.scope.ViewModelScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
abstract class AacViewModelModule {

    @ViewModelScope
    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    internal abstract fun bindBaseViewModel(baseViewModel: BaseViewModel): ViewModel


    @ViewModelScope
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}