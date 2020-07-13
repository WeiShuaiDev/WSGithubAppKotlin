package com.linwei.cams.di.component

import com.linwei.cams.base.activity.BaseActivity
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivitySubComponent :
    AndroidInjector<BaseActivity> {
    @Subcomponent.Factory
    interface Factory :
        AndroidInjector.Factory<BaseActivity>
}