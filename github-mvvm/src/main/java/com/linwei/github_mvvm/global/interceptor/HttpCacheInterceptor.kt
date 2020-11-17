package com.linwei.github_mvvm.global.interceptor

import com.linwei.cams.ext.no
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams.utils.NetworkUtils
import com.linwei.github_mvvm.mvvm.model.api.Api
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/28
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [HttpCacheInterceptor] 拦截器，主要用于配置 `Retrofit` 网络请求内核 `OKHttp` 配置。
 * 该拦截器主要处理网络请求缓存数据处理。
 *-----------------------------------------------------------------------
 */
class HttpCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = NetworkUtils.isNetworkAvailable().no {
//            request.newBuilder()
//                .cacheControl(CacheControl.FORCE_CACHE)
//                .build()
            request
        }.otherwise {
            request.url.queryParameter(Api.FORCE_NETWORK)?.toBoolean()?.let {
                it.yes {
                    //request.newBuilder().cacheControl(CacheControl.Builder().noCache().build()).build()
                    request.newBuilder().cacheControl(
                        CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
                    ).build()
                }.otherwise {
                    request
                }
            } ?: request
        }
        request = request.newBuilder()
            .url(request.url.newBuilder().removeAllQueryParameters(Api.FORCE_NETWORK).build())
            .build()

        return chain.proceed(request)
    }
}