package com.linwei.cams_aac.base

import androidx.lifecycle.ViewModelProvider
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams_aac.aac.IView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `AAC` 架构 `Activity` 基类
 *-----------------------------------------------------------------------
 *
 */
abstract class BaseAacActivity<VM : BaseViewModel> : BaseActivity(), HasAndroidInjector, ILoading,
    IView<VM> {


    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory


    override fun setUpActivityComponent(appComponent: AppComponent?) {


    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector

}