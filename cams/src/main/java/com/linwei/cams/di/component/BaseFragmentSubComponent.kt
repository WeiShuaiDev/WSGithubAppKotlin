package com.linwei.cams.di.component

import com.linwei.cams.base.fragment.BaseFragment
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseFragmentSubComponent :
    AndroidInjector<BaseFragment> {
    @Subcomponent.Factory
    interface Factory :
        AndroidInjector.Factory<BaseFragment>
}