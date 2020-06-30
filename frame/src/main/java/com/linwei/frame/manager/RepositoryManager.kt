package com.linwei.frame.manager

import android.app.Application
import com.linwei.frame.http.cache.Cache
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/29
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RepositoryManager {

    @Inject
    lateinit var mRetrofit: Retrofit

    @Inject
    lateinit var mApplication: Application

    @Inject
    lateinit var mRxCache: RxCache

    @Inject
    @field:Named("RetrofitServiceCache")
    lateinit var mRetrofitServiceCache: Cache<String, Any>

    @Inject
    @field:Named("CacheServiceCache")
    lateinit var mCacheServiceCache: Cache<String, Any>

    /**
     * 根据传进来 [service] `Class`对象，获取具体实例化对象。
     * @param service [Class]
     * @return [T]
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}