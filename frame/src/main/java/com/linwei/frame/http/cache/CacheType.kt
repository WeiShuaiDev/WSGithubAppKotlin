package com.linwei.frame.http.cache

import android.content.Context
import com.linwei.frame.ext.getCalculateCacheSize

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Cache内存接口，提供一些常用内存操作
 *-----------------------------------------------------------------------
 */
interface CacheType {
    companion object {
        const val RETROFIT_SERVICE_CACHE_TYPE_ID: Int = 0
        const val CACHE_SERVICE_CACHE_TYPE_ID: Int = 1
        const val EXTRAS_TYPE_ID: Int = 2
        const val ACTIVITY_CACHE_TYPE_ID: Int = 3
        const val FRAGMENT_CACHE_TYPE_ID: Int = 4

    }

    /**
     * cacheTypeId:ACTIVITY_CACHE_TYPE_ID
     * maxSize:80
     * multiplierSize:0.0008f
     * Activity内存容器
     */
    val activityCacheType: CacheType
        get() = object : CacheType {

            override fun getCacheTypeId(): Int = ACTIVITY_CACHE_TYPE_ID

            override fun calculateCacheSize(context: Context): Int {
                return context.getCalculateCacheSize(80, 0.0008f)
            }
        }


    /**
     * cacheTypeId:FRAGMENT_CACHE_TYPE_ID
     * maxSize:80
     * multiplierSize:0.0008f
     * Fragment内存容器
     */
    val fragmentCacheType: CacheType
        get() = object : CacheType {

            override fun getCacheTypeId(): Int = FRAGMENT_CACHE_TYPE_ID

            override fun calculateCacheSize(context: Context): Int {
                return context.getCalculateCacheSize(80, 0.0008f)
            }
        }


    /**
     * cacheTypeId:EXTRAS_TYPE_ID
     * maxSize:500
     * multiplierSize:0.005f
     * Extras内存容器
     */
    val extrasCacheType: CacheType
        get() = object : CacheType {

            override fun getCacheTypeId(): Int = EXTRAS_TYPE_ID

            override fun calculateCacheSize(context: Context): Int {
                return context.getCalculateCacheSize(500, 0.005f)
            }
        }


    /**
     * cacheTypeId:CACHE_SERVICE_CACHE_TYPE_ID
     * maxSize:150
     * multiplierSize:0.002f
     * Cache Service模块内存容器
     */
    val cacheServiceCacheType: CacheType
        get() = object : CacheType {

            override fun getCacheTypeId(): Int = CACHE_SERVICE_CACHE_TYPE_ID

            override fun calculateCacheSize(context: Context): Int {
                return context.getCalculateCacheSize(150, 0.002f)
            }
        }

    /**
     * cacheTypeId:RETROFIT_SERVICE_CACHE_TYPE_ID
     * maxSize:150
     * multiplierSize:0.002f
     * Retrofit Service模块内存容器
     */
    val retrofitServiceCacheType: CacheType
        get() = object : CacheType {

            override fun getCacheTypeId(): Int = RETROFIT_SERVICE_CACHE_TYPE_ID

            override fun calculateCacheSize(context: Context): Int {
                return context.getCalculateCacheSize(150, 0.002f)
            }
        }

    /**
     * 获取对应模块缓存类型标识
     */
    fun getCacheTypeId(): Int

    /**
     * 计算对应模块需要的缓存大小
     */
    fun calculateCacheSize(context: Context): Int

}