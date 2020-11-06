package com.linwei.cams.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/23
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface GlobalHttpHandler {

    companion object {
        /**
         * 初始化 [GlobalHttpHandler] 接口，方法空实现
         * @param Emtry [GlobalHttpHandler]
         */
        val Emtry: GlobalHttpHandler
            get() = object : GlobalHttpHandler {
                override fun onHttpRequestBefore(
                    chain: Interceptor.Chain,
                    request: Request
                ): Request {
                    return request
                }

                override fun onHttpResultResponse(
                    chain: Interceptor.Chain,
                    httpResult: String,
                    request:Request,
                    response: Response
                ): Response {
                    return response
                }
            }
    }

    /**
     * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 [token] 或者 [header] 以及参数加密等操作
     * @param chain [Interceptor.Chain]
     * @param request [Request]
     * @return [Request]
     */
    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     * @param httpResult [String]
     * @param chain [Interceptor.Chain]
     * @param response [Response]
     * @return [Response]
     */
    fun onHttpResultResponse(
        chain: Interceptor.Chain,
        httpResult: String,
        request: Request,
        response: Response
    ): Response
}
