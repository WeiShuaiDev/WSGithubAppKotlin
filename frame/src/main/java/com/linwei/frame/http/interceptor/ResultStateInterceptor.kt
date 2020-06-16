package com.linwei.frame.http.interceptor

import com.alibaba.fastjson.JSON
import com.linwei.frame.ext.isEmptyParameter
import com.linwei.frame.ext.showLongSafe
import com.linwei.frame.http.config.NetWorkStateCode
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
class ResultStateInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val content = response.body()?.string()
        val mediaType = response.body()?.contentType()
        /*
        val code: String = JSON.parseObject(content).run {
            var code: String = "-1"
            if (this.isNotEmpty() && this.containsKey("code")) {
                code = this.getString("code")
                this.getString("message")
            }
            code
        }*/
        val result = JSON.parseObject(content)
        if (result.isNotEmpty() && result.containsKey("code") && result.containsKey("message")) {
            val code = result.getString("code")
            val message = result.getString("message")
            if (!isEmptyParameter(code, message)) {
                responseCodeToast(code, message)
            }
        }

        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content))
            .build()
    }

    /**
     * 后台处理响应码提示
     */
    private fun responseCodeToast(code: String, message: String) {
        if (NetWorkStateCode().isExistByCode(code)) {
            message.showLongSafe()
        }
    }

    /**
     *本地处理响应码提示
     */
    private fun responseCodeToast(code: String) {
        val message: Int = NetWorkStateCode().getMessageByCode(code)
        if (message > 0) {
            message.showLongSafe()
        }
    }
}