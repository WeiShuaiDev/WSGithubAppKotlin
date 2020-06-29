package com.linwei.frame.manager

import retrofit2.Retrofit

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

    private lateinit var mRetrofit: Retrofit

    /**
     * 根据传进来 [service] `Class`对象，获取具体实例化对象。
     * @param service [Class]
     * @return [T]
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}