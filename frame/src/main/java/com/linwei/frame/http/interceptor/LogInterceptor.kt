package com.linwei.frame.http.interceptor

import android.util.Log
import com.linwei.frame.http.RetrofitFactory
import com.socks.library.KLog
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(request)
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val mediaType = response.body()?.contentType()
        val content = response.body()?.string()

        val requestBody = request.body()

        var body = ""

        if (requestBody != null) {
            try {
                if (requestBody !is MultipartBody) {
                    body = URLDecoder.decode(bodyToString(requestBody))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //请求数据
        val requestFormat = "| Request: method：[%s] url：[%s] params：[%s]"
        KLog.e(RetrofitFactory.TAG, String.format(requestFormat, request.method(), request.url(), body))

        //响应数据
        val responseFormat = "|response:[%s]"
        KLog.e(RetrofitFactory.TAG, String.format(responseFormat, content))

        Log.e(RetrofitFactory.TAG, "----------Request End:" + duration + "毫秒----------")
        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content))
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