package com.linwei.cams_mvvm_template

import com.linwei.cams_mvvm.MvvmApplication
import com.linwei.cams_mvvm.di.component.MvvmComponent
import com.linwei.cams_mvvm_template.di.component.DaggerTemplateComponent
import com.linwei.cams_mvvm_template.di.component.TemplateComponent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/26
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [TemplateApplication] 继承基类 [MvvmApplication],初始化 `Dagger`功能。
 *----------------------------------------------------------------------
 */
class TemplateApplication : MvvmApplication() {
    private lateinit var mTemplateComponent: TemplateComponent

    override fun setUpAppChildComponent(mvvmComponent: MvvmComponent?) {
        mvvmComponent?.let {
            this.mTemplateComponent = DaggerTemplateComponent
                .builder()
                .appComponent(mvvmComponent)
                .build()
            mTemplateComponent.inject(this)
        }
    }
}