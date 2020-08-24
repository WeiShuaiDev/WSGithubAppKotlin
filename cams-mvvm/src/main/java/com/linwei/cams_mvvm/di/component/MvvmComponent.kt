package com.linwei.cams_mvvm.di.component

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.scope.ApplicationScope
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.manager.*
import com.linwei.cams_mvvm.MvvmApplication
import com.linwei.cams_mvvm.di.module.MvvmModelModule
import com.linwei.cams_mvvm.di.module.MvvmViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `Application` 依赖注入Component,内部提供 [MvvmViewModelModule]、[MvvmModelModule] 模块。
 *-----------------------------------------------------------------------
 */
@ApplicationScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class, MvvmViewModelModule::class, MvvmModelModule::class]
)
interface MvvmComponent {
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


    /**
     * @return [ViewModelProvider.Factory]
     */
    fun viewModelFactory(): ViewModelProvider.Factory

    /**
     * 注入 [MvvmApplication] 对象
     */
    fun inject(application: MvvmApplication)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): MvvmComponent
    }
}