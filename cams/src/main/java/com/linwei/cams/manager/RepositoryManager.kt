package com.linwei.cams.manager

import android.app.Application
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.http.RetrofitServiceProxyHandler
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.http.repository.DataRepository
import com.linwei.cams.http.repository.IDataRepository
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.lang.reflect.Proxy
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/30
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RepositoryManager private constructor() : IDataRepository {

    @Inject
    lateinit var mDataRepository: DataRepository

    companion object {
        private var INSTANCE: RepositoryManager? = null

        @JvmStatic
        fun getInstance(): RepositoryManager {
            return INSTANCE
                ?: RepositoryManager().apply {
                    INSTANCE = this
                }
        }
    }

    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T =
        mDataRepository.obtainRetrofitService(serviceClass)


    override fun <T> obtainRxCacheService(serviceClass: Class<T>): T =
        mDataRepository.obtainRxCacheService(serviceClass)

    override fun clearAllRxCache() = mDataRepository.clearAllRxCache()

    override fun fetchApplication(): Application = mDataRepository.mApplication

}