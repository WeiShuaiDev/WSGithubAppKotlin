package com.linwei.frame.di.component

import android.app.Application
import com.linwei.frame.base.delegate.AppDelegate
import com.linwei.frame.di.module.AppModule
import com.linwei.frame.di.module.ClientModule
import com.linwei.frame.di.module.GlobalConfigModule
import com.linwei.frame.http.cache.Cache
import dagger.BindsInstance
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

    fun application(): Application

    /**
     * 用来存取一些整个 App 公用的数据, 切勿大量存放大容量数据, 这里的存放的数据和 [Application] 的生命周期一致
     *
     * @return [Cache]
     */
    fun extras(): Cache<String, Any>

    /**
     * 用于创建框架所需缓存对象的工厂
     *
     * @return [Cache.Factory]
     */
    fun cacheFactory(): Cache.Factory


    /**
     * 注入 [AppDelegate]
     */
    fun inject(appDelegate: AppDelegate)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun globalConfigModule(globalConfigModule: GlobalConfigModule): Builder

        fun build(): AppComponent
    }
}