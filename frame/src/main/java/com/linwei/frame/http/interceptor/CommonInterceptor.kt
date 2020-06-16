package com.linwei.frame.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class CommonInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content_Type", "application/json")
            .addHeader("charset", "UTF-8").build()
        return chain.proceed(request)
    }
}