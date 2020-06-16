package com.linwei.frame.http.config

import android.content.Context
import android.net.ConnectivityManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
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
    var responseCode = mutableMapOf<String, Int>()


    /**
     * params:code状态值
     *  根据code状态值，获取描述信息
     */
    fun getMessageByCode(code: String): Int {
        val message = responseCode.let {
            var message = -1
            if (code.isNotBlank() && it.containsKey(code)) {
                message = it[code]!!
            }
            message
        }
        return message
    }


    /**
     * params:code状态值
     * 判断当前请求是否包含改状态值
     */
    fun isExistByCode(code: String?): Boolean {
        return responseCode.containsKey(code)
    }

    /**
     * 判断网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isAvailable
        }
        return false
    }
}


