package com.linwei.frame.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [CommonInterceptor] 拦截器，主要用于配置 `Retrofit` 网络请求内核 `OKHttp` 配置。
 * 该拦截器主要处理通用网络请求配置，比如请求头配置，这里根据传输协议，配置了 `Content_Type`、
 * `charset`
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