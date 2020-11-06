package com.linwei.cams.http.interceptor

import com.google.gson.Gson
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.showLongSafe
import com.linwei.cams.ext.string
import com.linwei.cams.http.GlobalHttpHandler
import com.linwei.cams.http.config.NetWorkStateCode
import com.linwei.cams.http.model.BaseResponse
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:  [HttpResponseInterceptor] 拦截器，主要用于配置 `Retrofit` 网络请求内核 `OKHttp` 配置。
 * 该拦截器主要处理每次响应报文，根据不同的状态码，状态信息，通过  [ToastUtils] 提示用户。
 *-----------------------------------------------------------------------
 */
class HttpResponseInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var mGlobalHttpHandler: GlobalHttpHandler

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response: Response = chain.proceed(request)
        val content: String? = response.body?.string()

        //拦截请求响应状态码，错误状态码显示 `Toast` 信息
        val code: String = response.code.toString()
        val message: String = response.message
        if (!isEmptyParameter(code, message)) {
            responseCodeToast(code, message)
        }

        val httpResult: String = content ?: ""

        //提供给开发者扩展网路请求后配置
        response = mGlobalHttpHandler.onHttpResultResponse(chain, httpResult, request, response)

        return response

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