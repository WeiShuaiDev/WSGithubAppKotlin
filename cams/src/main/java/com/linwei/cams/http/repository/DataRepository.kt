package com.linwei.cams.http.repository

import android.app.Application
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.http.RetrofitServiceProxyHandler
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.lang.reflect.Proxy
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class DataRepository @Inject constructor() : IDataRepository {

    @Inject
    lateinit var mRetrofit: Retrofit

    @Inject
    lateinit var mApplication: Application

    @Inject
    lateinit var mRxCache: RxCache

    @Inject
    lateinit var mCacheFactory: Cache.Factory

    @Inject
    lateinit var mRetrofitServiceDelegate: ClientModule.RetrofitServiceDelegate

    /**
     * `RetrofitService` 对象缓存存储
     */
    private lateinit var mRetrofitServiceCache: Cache<String, Any>

    /**
     * `CacheService` 对象缓存存储
     */
    private lateinit var mCacheServiceCache: Cache<String, Any>

    /**
     * 根据 [serviceClass] 获取接口对象，根据 `serviceClass`作为`key`，在 [mRetrofitServiceCache] 获取接口对象,
     * [mRetrofitServiceDelegate] 回调接口，提供给开发者扩展功能
     * @param serviceClass [Class] `Retrofit` 接口 Class 对象
     * @return 接口对象
     */
    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T {
        mRetrofitServiceCache = mCacheFactory.build(CacheType.retrofitServiceCacheType)

        var retrofitService: Any? =
            mRetrofitServiceCache.get(serviceClass.canonicalName ?: serviceClass.simpleName)

        if (retrofitService == null) {
            //提供给开发者，扩展获取 `Retrofit Service` 回调
            retrofitService =
                mRetrofitServiceDelegate.createRetrofitService(mRetrofit, serviceClass)

            if (retrofitService == null) {
                retrofitService = Proxy.newProxyInstance(
                    serviceClass.classLoader, arrayOf(serviceClass),
                    RetrofitServiceProxyHandler(mRetrofit, serviceClass)
                )
            }

            //保存 `Retrofit Service` 对象到 `Cache` 中
            mRetrofitServiceCache.put(
                serviceClass.canonicalName ?: serviceClass.simpleName,
                retrofitService!!
            )
        }
        return retrofitService as T
    }


    /**
     * 根据 [serviceClass] 获取接口对象，根据 `serviceClass`作为`key`，在 [mCacheServiceCache]、[mRxCache] 获取接口对象
     * @param serviceClass [Class] `Retrofit` 接口 Class 对象
     * @return 接口对象
     */
    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <T> obtainRxCacheService(serviceClass: Class<T>): T {
        mCacheServiceCache = mCacheFactory.build(CacheType.cacheServiceCacheType)

        var retrofitService: Any? =
            mCacheServiceCache.get(serviceClass.canonicalName ?: serviceClass.simpleName)
        if (retrofitService == null) {

            retrofitService = mRxCache.using(serviceClass) as Any

            //保存 `Retrofit Service` 对象到 `Cache` 中
            mCacheServiceCache.put(
                serviceClass.canonicalName ?: serviceClass.simpleName,
                retrofitService
            )
        }

        return retrofitService as T
    }

    /**
     * 清除 `RxCache` 中所有数据
     */
    override fun clearAllRxCache() {
        mRxCache.evictAll().subscribe()
    }

    /**
     * Application 对象
     */
    override fun fetchApplication(): Application = mApplication
}