package com.linwei.cams.config

import android.text.TextUtils
import java.io.BufferedReader
import java.io.FileReader

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/2
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 数据配置中心，主要初始化配置，第三方库初始化
 *-----------------------------------------------------------------------
 */
object LibConfig {
    var DEBUG: Boolean = false
    var APPLICATION_ID: String? = null  //包名
    var LANGUAGE = "zh" //语言
    var VERSION: String? = null //版本号
    var TIMESTAMP: String? = null //时间戳
    var APP_NAME: String? = null //应用名
    var BASE_URL: String? = null //域名


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