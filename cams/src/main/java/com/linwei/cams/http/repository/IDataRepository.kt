package com.linwei.cams.http.repository

import android.app.Application

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface IDataRepository {

    /**
     * 根据 [serviceClass] 从 `Cache` 获取 `Retrofit` 接口对象
     * @param serviceClass [Class]
     * @return [T]
     */
    fun <T> obtainRetrofitService(serviceClass: Class<T>): T

    /**
     * 根据 [serviceClass] 从 `RxCache` 获取 `Retrofit` 接口对象
     * @param serviceClass [Class]
     * @return [T]
     */
    fun <T> obtainRxCacheService(serviceClass: Class<T>): T


    /**
     * 清除 `RxCache` 中数据
     */
    fun clearAllRxCache()

    /**
     * 返回 [Application]
     */
    fun fetchApplication(): Application

}