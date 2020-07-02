package com.linwei.cams.http.config

import android.content.Context
import android.net.ConnectivityManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 网络状态处理
 *-----------------------------------------------------------------------
 */
enum class Status {
    RUNNNIG,
    SUCCESS,
    FAILED,
    HIDDEN
}

/**
 *Request网络状态
 */
data class NetworkState private constructor(
    val state: Status
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNNIG)
        val HIDDEN = NetworkState(Status.HIDDEN)
        val FAILED = NetworkState(Status.FAILED)
    }
}

/**
 * Response状态码
 */
class NetWorkStateCode {
    val responseCode: MutableMap<String, Int> = mutableMapOf()


    /**
     * 根据 [code] 状态值，先判断响应状态码不为空 `NULL`，是否包含在 [responseCode] 集合中，如何存在集合中，则根据 [code] 获取
     * 集合中 message=`Value` 值不存在 message=-1,最后返回 message
     * @params:code [String] 响应状态码
     * @return 返回该响应状态码，对应文体描述Id
     */
    fun getMessageByCode(code: String): Int {
        val message: Int = responseCode.let {
            var message = -1
            if (code.isNotBlank() && it.containsKey(code)) {
                message = it[code]!!
            }
            message
        }
        return message
    }


    /**
     * 判断当前响应状态码 [code] 是否包含在 [resposneCode] 服务器状态码集合中
     * @params:code [String] 响应状态码
     * @return:bool [Boolean] true:存在状态码；false:不存在状态码
     */
    fun isExistByCode(code: String?): Boolean {
        return responseCode.containsKey(code)
    }

    /**
     * 获取当前网络状态,根据网络状态，返回 [Boolean] 类型
     * @param context [Context] 上下文
     * @return bool [Boolean]  返回网络状态，false:网络繁忙，网络断开;true:网络连接中
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION") val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null) {
            @Suppress("DEPRECATION")
            return networkInfo.isAvailable
        }
        return false
    }
}


