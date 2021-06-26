package com.linwei.cams_mvvm.di.component

import androidx.databinding.ViewDataBinding
import com.linwei.cams.base.activity.BaseActivity
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvvm.base.BaseMvvmActivity
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
 * @Description: `MVVM`架构 `Activity` 依赖注入Component
 *-----------------------------------------------------------------------
 */
@ActivityScope
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivitySubComponent :
    AndroidInjector<BaseMvvmActivity<BaseViewModel, ViewDataBinding>> {
    @Subcomponent.Factory
    interface Factory :
        AndroidInjector.Factory<BaseMvvmActivity<BaseViewModel, ViewDataBinding>>
}