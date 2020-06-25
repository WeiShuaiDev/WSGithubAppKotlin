package com.linwei.frame.http.interceptor

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.linwei.frame.ext.isEmptyParameter
import com.linwei.frame.ext.showLongSafe
import com.linwei.frame.http.config.NetWorkStateCode
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:  [ResultStateInterceptor] 拦截器，主要用于配置 `Retrofit` 网络请求内核 `OKHttp` 配置。
 * 该拦截器主要处理每次响应报文，根据不同的状态码，状态信息，通过  [ToastUtils] 提示用户。
 *-----------------------------------------------------------------------
 */
class ResultStateInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        val content: String? = response.body()?.string()
        val mediaType: MediaType? = response.body()?.contentType()

        val result: JSONObject = JSON.parseObject(content)
        if (result.isNotEmpty() && result.containsKey("code") && result.containsKey("message")) {
            val code: String = result.getString("code")
            val message: String = result.getString("message")
            if (!isEmptyParameter(code, message)) {
                responseCodeToast(code, message)
            }
        }

        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content?:""))
            .build()
    }

    /**
     * 不会干预后台响应码信息提示，接口请求返回信息，直接通过 [ToastUtils] 提示用户。
     * @param code [String] 响应码
     * @param message [String] 响应码描述信息
     */
    private fun responseCodeToast(code: String, message: String) {
        if (NetWorkStateCode().isExistByCode(code)) {
            message.showLongSafe()
        }
    }

    /**
     *后台返回响应码需要本地校验，校验成功后，根据响应码 [code] 获取本地存储信息，并通过 [ToastUtils] 提示用户。
     * @param code [String] 响应码
     */
    private fun responseCodeToast(code: String) {
        val message: Int = NetWorkStateCode().getMessageByCode(code)
        if (message > 0) {
            message.showLongSafe()
        }
    }
}