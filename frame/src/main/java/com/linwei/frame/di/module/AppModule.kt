package com.linwei.frame.di.module

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linwei.frame.base.lifecycle.ActivityLifecycle
import com.linwei.frame.base.lifecycle.FragmentLifecycle
import com.linwei.frame.http.cache.Cache
import com.linwei.frame.http.cache.CacheType
import com.linwei.frame.manager.DiskStorageManager
import com.linwei.frame.manager.MemoryStorageManager
import com.linwei.frame.utils.FileUtils
import com.squareup.otto.Produce
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

    @Singleton
    @Provides
    fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
        val builder = GsonBuilder()
        configuration?.configGson(application, builder)
        return builder.create()
    }

    @Singleton
    @Provides
    fun provideFragmentLifecycleLists(): List<FragmentLifecycle> {
        return listOf()
    }

    @Singleton
    @Provides
    fun provideExtras(cacheFactory: Cache.Factory): Cache<String, Any> {
        return cacheFactory.build(CacheType.extrasCacheType)
    }

    /**
     * 创建 `DiskStorageManager` 对象,用户数据磁盘存储
     * @param cacheDirectory [File] 缓存文件
     * @return 指定 [DiskStorageManager] 缓存目录，同时创建单例 `DiskStorageManager` 对象。
     */
    @Singleton
    @Provides
    fun provideDiskStorageManager(@Named("DiskCacheDirectory") cacheDirectory: File): DiskStorageManager {
        return DiskStorageManager.getInstance(cacheDirectory)
    }

    /**
     * 创建 `MemoryStorageManager` 单例对象,用于数据内存存储。
     * @return 返回`MemoryStorageManager` 对象
     */
    @Singleton
    @Provides
    fun provideMemoryStorageManager(): MemoryStorageManager {
        return MemoryStorageManager.getInstance()
    }

    /**
     * 创建 `DiskCache` 缓存文件
     * @param cacheDir [File] 缓存文件
     * @return [File] 指定 [cacheDir] 路径,生成文件名 `DiskCache` 文件
     */
    @Singleton
    @Produce
    @Named("DiskCacheDirectory")
    fun provideDiskCacheDirectory(cacheDir: File): File =
        File(FileUtils.makeDirs(cacheDir), "DiskCache")


    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }

    @Module
    interface Bindings {
        @Singleton
        @Binds
        fun bindActivityLifecycle(activityLifecycle: ActivityLifecycle): Application.ActivityLifecycleCallbacks

        @Singleton
        @Binds
        fun bindFragmentLifecycle(fragmentLifecycle: FragmentLifecycle): FragmentManager.FragmentLifecycleCallbacks
    }
}