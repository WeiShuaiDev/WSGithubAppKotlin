package com.linwei.cams_mvvm_template.di.component

import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.cams_mvvm.di.module.MvvmModelModule
import com.linwei.cams_mvvm.di.module.MvvmViewModelModule
import com.linwei.cams_mvvm.di.module.ViewModelFactoryModule
import com.linwei.cams_mvvm_template.TemplateApplication
import com.linwei.cams_mvvm_template.di.module.ActivityModule
import com.linwei.cams_mvvm_template.di.module.FragmentModule
import com.linwei.cams_mvvm_template.di.module.ViewModelModule
import com.linwei.cams_mvvm_template.di.scope.TemplateScope
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构依赖注入Component,内部提供 [ViewModelModule]、[ActivityModule]、[FragmentModule] 模块。
 *-----------------------------------------------------------------------
 */
@TemplateScope
@Component(
    dependencies = [MvvmComponent::class],
    modules = [AndroidInjectionModule::class, ViewModelModule::class, ActivityModule::class,
        FragmentModule::class, ViewModelFactoryModule::class]
)
interface TemplateComponent {

    /**
     * 注入 [TemplateApplication] 对象
     * @param application [TemplateApplication]
     */
    fun inject(application: TemplateApplication)


    @Component.Builder
    interface Builder {
        fun appComponent(mvvmComponent: MvvmComponent): Builder

        fun build(): TemplateComponent
    }


}