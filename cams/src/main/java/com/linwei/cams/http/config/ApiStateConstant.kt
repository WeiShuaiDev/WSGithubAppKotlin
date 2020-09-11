package com.linwei.cams.http.config

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 网络状态常量
 *-----------------------------------------------------------------------
 */
object ApiStateConstant {
    //网络状态值
    const val REQUEST_SUCCESS: String = "200"
    const val REQUEST_FAILURE: String = "0"
}

object HttpConstant {
    const val API_URL: String = "https://api.github.com/"

    const val CONNECT_TIME_OUT: Long = 10
    const val WRITE_TIME_OUT: Long = 10
    const val READ_TIME_OUT: Long = 10
}

