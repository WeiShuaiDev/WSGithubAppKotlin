package com.linwei.cams.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkInfo.State
import com.linwei.cams.ext.ctx

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/28
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: NetworkUtils工具类
 * ---------------------------------------------------------------------
 */
object NetworkUtils {

    private val mConnectManager: ConnectivityManager by lazy {
        ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * 判断当前网络状态
     * @return 状态 [Boolean] true:网络可用  false:网络不可用
     */
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
    fun isNetworkAvailable(): Boolean {
        val networkInfo: NetworkInfo? = mConnectManager.activeNetworkInfo
        if (networkInfo != null) {
            if (ConnectivityManager.TYPE_MOBILE == networkInfo.type) {
                val stateMobile: State =
                    mConnectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state
                if (stateMobile == State.CONNECTED) {
                    return networkInfo.isAvailable
                }
            }
            if (ConnectivityManager.TYPE_WIFI == networkInfo.type) {
                val stateMobile: State =
                    mConnectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state
                if (stateMobile == State.CONNECTED) {
                    return networkInfo.isAvailable
                }
            }
        }
        return false
    }
}