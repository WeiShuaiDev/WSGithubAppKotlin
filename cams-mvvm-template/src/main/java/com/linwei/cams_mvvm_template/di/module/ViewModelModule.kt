package com.linwei.cams_mvvm_template.di.module
import com.linwei.cams_mvvm.di.scope.ViewModelKey
import com.linwei.cams_mvvm_template.mvvm.contract.MainContract
import com.linwei.cams_mvvm_template.mvvm.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): MainContract.ViewModel

}