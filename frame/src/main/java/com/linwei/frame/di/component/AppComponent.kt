package com.linwei.frame.di.component

import com.linwei.frame.common.BaseApp
import com.linwei.frame.common.delegate.AppDelegate
import com.linwei.frame.di.module.AppModule
import com.linwei.frame.di.module.ClientModule
import com.linwei.frame.di.module.GlobalConfigModule
import dagger.Component
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Singleton
@Component(modules = [AppModule::class, ClientModule::class, GlobalConfigModule::class])
interface AppComponent {


    fun inject(appDelegate: AppDelegate)

    @Component.Builder
    interface Builder {

        fun application(app: BaseApp): Builder

        fun globalConfigModule(globalConfigModule: GlobalConfigModule): Builder

        fun build(): AppComponent
    }

}