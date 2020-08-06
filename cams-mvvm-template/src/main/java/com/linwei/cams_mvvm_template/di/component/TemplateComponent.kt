package com.linwei.cams_mvvm_template.di.component

import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.cams_mvvm_template.di.module.ActivityModule
import com.linwei.cams_mvvm_template.di.module.FragmentModule
import com.linwei.cams_mvvm_template.di.module.ViewModelModule
import com.linwei.cams_mvvm_template.di.scope.MvvmScope
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@MvvmScope
@Component(
    dependencies = [MvvmComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class]
)
interface TemplateComponent {

    /**
     * 注入 [AppLifecycles] 对象
     * @param appLifecycles [AppLifecycles]
     */
    fun inject(appLifecycles: AppLifecycles)


}