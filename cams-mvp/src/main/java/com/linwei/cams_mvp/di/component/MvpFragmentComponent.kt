package com.linwei.cams_mvp.di.component

import android.app.Application
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.FragmentScope
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.manager.*
import com.linwei.cams_mvp.di.module.MvpActivityModule
import com.linwei.cams_mvp.di.module.MvpFragmentModule
import dagger.Component

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 `Fragment` 依赖注入Component,内部提供 [MvpFragmentModule] 模块。
 *-----------------------------------------------------------------------
 */
@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [MvpFragmentModule::class])
interface MvpFragmentComponent {

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
     * 用于数据磁盘存储
     * @return [DiskStorageManager]
     */
    fun diskStorageManager(): DiskStorageManager

    /**
     * 用于数据内存存储
     * @return [MemoryStorageManager]
     */
    fun memoryStorageManager(): MemoryStorageManager

    /**
     * 用于事件处理
     * @return [EventBusManager]
     */
    fun eventBusManager(): EventBusManager

    /**
     * 用于线程处理
     * @return [HandlerManager]
     */
    fun handlerManager(): HandlerManager

    /**
     * 用于网络请求
     * @return [RepositoryManager]
     */
    fun repositoryManager(): RepositoryManager

    /**
     * 用于权限处理
     * @return [PermissionManager]
     */
    fun permissionManager(): PermissionManager


    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent?): Builder

        fun build(): MvpFragmentComponent
    }

}