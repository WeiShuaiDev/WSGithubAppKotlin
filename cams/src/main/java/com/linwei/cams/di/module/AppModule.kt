package com.linwei.cams.di.module

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linwei.cams.base.lifecycle.ActivityLifecycle
import com.linwei.cams.base.lifecycle.FragmentLifecycle
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.http.repository.DataRepository
import com.linwei.cams.http.repository.IDataRepository
import com.linwei.cams.manager.*
import com.linwei.cams.utils.FileUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 提供一些框架必须的实例
 *-----------------------------------------------------------------------
 */

@Module(includes = [AppModule.Bindings::class])
object AppModule {

    /**
     * 通过 [GsonBuilder] 创建,并初始化配置，创建 [Gson] 对象,同时提供给开发者扩展配置接口 [configuration],
     * 在 [GlobalConfigModule] 的 `Module` 进行依赖注入。
     * @param application [Application]
     * @param configuration [GsonConfiguration] 扩张配置接口
     * @return [Gson] 初始化配置后 `Gson` 对象
     */
    @Singleton
    @Provides
    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
        val builder = GsonBuilder()
        configuration?.configGson(application, builder)
        return builder.create()
    }

    /**
     * 创建 `LruCache` 单例对象,用于数据内存存储(根据内存存储，进行gc优化)。
     * `Cache<String,Any>` 存储范围:全局共有数据存储。
     * @param cacheFactory [Cache.Factory] 存储参数类型
     * @return 返回 `Cache` 对象
     */
    @Singleton
    @Provides
    fun provideExtrasCache(cacheFactory: Cache.Factory): Cache<String, Any> {
        return cacheFactory.build(CacheType.extrasCacheType)
    }

    /**
     * 创建 `DiskStorageManager` 单例对象,用于数据磁盘存储(根据数据存储时间，进行gc优化)。
     * @param cacheDirectory [File] 缓存文件
     * @return 指定 [DiskStorageManager] 缓存目录，同时创建单例 `DiskStorageManager` 对象。
     */
    @Singleton
    @Provides
    fun provideDiskStorageManager(@Named("DiskCacheDirectory") cacheDirectory: File): DiskStorageManager {
        return DiskStorageManager.getInstance(cacheDirectory)
    }

    /**
     * 创建 `MemoryStorageManager` 单例对象,用于数据内存存储(不根据内存存储，进行gc优化)。
     * @return 返回`MemoryStorageManager` 对象
     */
    @Singleton
    @Provides
    fun provideMemoryStorageManager(): MemoryStorageManager {
        return MemoryStorageManager.getInstance()
    }

    /**
     * 创建 `EventBusManager` 单例对象,用于事件处理。
     * @return 返回 `EventBusManager` 对象
     */
    @Singleton
    @Provides
    fun provideEventBusManager(): EventBusManager {
        return EventBusManager.getInstance()
    }

    /**
     * 创建 `HandlerManager` 单例对象,用于线程处理。
     * @return 返回 `HandlerManager` 对象
     */
    @Singleton
    @Provides
    fun provideHandlerManager(): HandlerManager {
        return HandlerManager.getInstance()
    }

    /**
     * 创建 `RepositoryManager` 单例对象,用于网络请求。
     * @return 返回 `RepositoryManager` 对象
     */
    @Singleton
    @Provides
    fun provideRepositoryManager(): RepositoryManager {
        return RepositoryManager.getInstance()
    }

    /**
     * 创建 `PermissionManager` 单例对象,用于权限处理。
     * @return 返回 `PermissionManager` 对象
     */
    @Singleton
    @Provides
    fun providePermissionManager(): PermissionManager {
        return PermissionManager.getInstance()
    }

    /**
     * 创建 `DeviceManager` 单例对象,用于设备信息处理。
     * @return 返回 `DeviceManager` 对象
     */
    @Singleton
    @Provides
    fun provideDeviceManager(): DeviceManager {
        return DeviceManager.getInstance()
    }

    /**
     * 创建 `DiskCache` 缓存文件
     * @param cacheDir [File] 缓存文件
     * @return [File] 指定 [cacheDir] 路径,生成文件名 `DiskCache` 文件
     */
    @Singleton
    @Provides
    @Named("DiskCacheDirectory")
    fun provideDiskCacheDirectory(cacheDir: File): File =
        FileUtils.makeDirs(File(cacheDir, "DiskCache"))


    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }

    @Module
    interface Bindings {
        /**
         * 框架全局 `Activity` 生命周期回调配置
         * @param activityLifecycle [ActivityLifecycle]
         * @return [Application.ActivityLifecycleCallbacks]
         */
        @Singleton
        @Binds
        fun bindActivityLifecycle(activityLifecycle: ActivityLifecycle): Application.ActivityLifecycleCallbacks

        /**
         * 框架全局 `Fragment` 生命周期回调配置
         * @param fragmentLifecycle [FragmentLifecycle]
         * @return [FragmentManager.FragmentLifecycleCallbacks]
         */
        @Singleton
        @Binds
        fun bindFragmentLifecycle(fragmentLifecycle: FragmentLifecycle): FragmentManager.FragmentLifecycleCallbacks

        /**
         * 框架全局网络数据源 `dataRepository`
         * @param dataRepository [DataRepository]
         * @return [IDataRepository]
         */
        @Singleton
        @Binds
        fun bindDataRepository(dataRepository: DataRepository): IDataRepository
    }
}