package com.linwei.cams.di.component

import android.app.Application
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.di.module.AppModule
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.di.module.GlobalConfigModule
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.manager.*
import dagger.BindsInstance
import dagger.Component
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
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
     *  用于获取设备信息处理
     * @return [DeviceManager]
     */
    fun deviceManager(): DeviceManager

    /**
     * 网络请求对象
     * @return [Retrofit]
     */
    fun retrofit(): Retrofit

    /**
     * 缓存对象
     * @return [RxCache]
     */
    fun rxCache(): RxCache

    /**
     * Retrofit代理类
     * @return [ClientModule.RetrofitServiceDelegate]
     */
    fun retrofitServiceDelegate(): ClientModule.RetrofitServiceDelegate

    /**
     * 注入
     * @param [AppDelegate]
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