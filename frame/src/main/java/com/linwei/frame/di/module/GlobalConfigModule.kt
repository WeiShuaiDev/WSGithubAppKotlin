package com.linwei.frame.di.module
import android.app.Application
import com.linwei.frame.http.cache.Cache
import com.linwei.frame.http.cache.CacheType
import com.linwei.frame.http.cache.CacheType.Companion.ACTIVITY_CACHE_TYPE_ID
import com.linwei.frame.http.cache.CacheType.Companion.CACHE_SERVICE_CACHE_TYPE_ID
import com.linwei.frame.http.cache.CacheType.Companion.EXTRAS_TYPE_ID
import com.linwei.frame.http.cache.CacheType.Companion.FRAGMENT_CACHE_TYPE_ID
import com.linwei.frame.http.cache.CacheType.Companion.RETROFIT_SERVICE_CACHE_TYPE_ID
import com.linwei.frame.http.cache.kinds.LruCache
import dagger.Module
import dagger.Provides
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
@Module
class GlobalConfigModule(val mBuilder: Builder) {

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    @Singleton
    @Provides
    fun provideCacheFactory(application: Application): Cache.Factory {
        return mBuilder.cacheFactory ?: object : Cache.Factory {
            override fun <K, V> build(type: CacheType): Cache<K, V> {
                when (type.getCacheTypeId()) {
                    RETROFIT_SERVICE_CACHE_TYPE_ID,
                    CACHE_SERVICE_CACHE_TYPE_ID,
                    EXTRAS_TYPE_ID,
                    ACTIVITY_CACHE_TYPE_ID,
                    FRAGMENT_CACHE_TYPE_ID -> {
                        return LruCache(type.calculateCacheSize(application))
                    }
                    else -> {
                        return LruCache(type.calculateCacheSize(application))
                    }
                }
            }
        }
    }

    class Builder {
        var cacheFactory: Cache.Factory? = null

        fun cacheFactory(cacheFactory: Cache.Factory): Builder {
            this.cacheFactory = cacheFactory
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}