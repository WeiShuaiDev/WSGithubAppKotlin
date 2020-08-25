package com.linwei.cams_mvvm.di.component

import androidx.databinding.ViewDataBinding
import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@FragmentScope
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseFragmentSubComponent :
    AndroidInjector<BaseMvvmFragment<BaseViewModel, ViewDataBinding>> {
    @Subcomponent.Factory
    interface Factory :
        AndroidInjector.Factory<BaseMvvmFragment<BaseViewModel, ViewDataBinding>>
}