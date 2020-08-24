package com.linwei.cams_mvvm.di.component

import com.linwei.cams.base.activity.BaseActivity
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
@Singleton
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivitySubComponent :
    AndroidInjector<BaseActivity> {
    @Subcomponent.Factory
    interface Factory :
        AndroidInjector.Factory<BaseActivity>
}