package com.linwei.frame.config

import android.text.TextUtils
import com.linwei.frame.common.BaseApp
import java.io.BufferedReader
import java.io.FileReader

/**
 * @Author: ws
 * @Time: 2019/11/7
 * @Description:Library配置
 */
object LibConfig {
    var DEBUG: Boolean = false
    var APPLICATION_ID: String? = null  //包名
    var LANGUAGE = "vi" //语言
    var APP_VERSION: String? = null //APP的版本号
    var VERSION: String? = null //版本号
    var CHANNEL: String? = null //来源渠道
    var TIMESTAMP: String? = null //时间戳
    var APP_NAME: String? = null //应用名
    var BASE_URL: String? = null //域名
    var CASH_UNIT: String? = null //金额单位
    var ADJUST_APP_TOKEN: String? = null
    var ADJUST_APP_SECRETID: Int = 0
    var ADJUST_APP_INFO1: Int = 0
    var ADJUST_APP_INFO2: Int = 0
    var ADJUST_APP_INFO3: Int = 0
    var ADJUST_APP_INFO4: Int = 0
    var ADJUST_ENVIRONMENT: String? = null
    var ADJUST_APP_TRACKER: String? = null
    var ADJUST_GAID: String? = null
    var BUGLY_APP_ID: String? = null
    var UPLOAD_CALL_SMS: Boolean = false

    /**
     * 初始化第三方库
     */
    fun initLib(context: BaseApp) {

    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }


}