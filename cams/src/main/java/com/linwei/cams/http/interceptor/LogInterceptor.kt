package com.linwei.cams.http.interceptor

import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.net.URLDecoder
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [LogInterceptor] 拦截器，主要用于配置 `Retrofit` 网络请求内核 `OKHttp` 配置。
 * 该拦截器主要处理每次请求报文、响应报文日志打印，开发者在开发过程中，方便对每次请求，进行调试。
 *-----------------------------------------------------------------------
 */
class LogInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val startTime: Long = System.currentTimeMillis()
        val response: Response = chain.proceed(request)
        val endTime: Long = System.currentTimeMillis()

        val duration: Long = endTime - startTime
        val mediaType: MediaType? = response.body?.contentType()
        val content: String? = response.body?.string()

        val requestBody: RequestBody? = request.body

        var body = ""

        if (requestBody != null) {
            try {
                @Suppress("DEPRECATION")
                if (requestBody !is MultipartBody) body =
                    URLDecoder.decode(bodyToString(requestBody))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val headers: String = request.headers.toMultimap().map {
            "${it.key}:${it.value.joinToString(",")}"
        }.joinToString(";")

        //请求数据
        val requestFormat = "| Request: method：[%s] url：[%s] headers：[%s] params：[%s] "
        Timber.i(requestFormat, request.method, request.url, headers, body)

        //响应数据
        val responseFormat = "| Response:[%s]"
        Timber.i(responseFormat, content ?: "")

        Timber.i("----------Request End: %s 毫秒----------", duration)

        return response.newBuilder()
            .body((content ?: "").toResponseBody(mediaType))
            .build()
    }

    /**
     * 转换为字符串
     */
    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }
    }
}