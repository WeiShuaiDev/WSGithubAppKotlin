package com.linwei.cams_mvp.di.component
import android.app.Application
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvp.di.module.BaseActivityModule
import dagger.Component

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [BaseActivityModule::class])
interface BaseActivityComponent {

    @Component.Builder
    interface Builder {

        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): BaseActivityComponent
    }

}