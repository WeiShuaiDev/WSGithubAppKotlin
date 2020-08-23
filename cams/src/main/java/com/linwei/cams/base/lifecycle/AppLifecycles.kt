package com.linwei.cams.base.lifecycle

import android.app.Application
import android.content.Context
import com.linwei.cams.base.delegate.AppDelegate

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface AppLifecycles {

    /**
     * @param context [Context]
     */
    fun attachBaseContext(context: Context)

    /**
     * 初始化
     * @param application [Application]
     */
    fun onCreate(application: Application,appDelegate: AppDelegate?)

    /**
     * 回收资源时执行
     * @param application [Application]
     */
    fun onTerminate(application: Application)

    /**
     * 低内存时执行
     * @param application [Application]
     */
    fun onLowMemory(application: Application)

    /**
     * 清理内存时执行
     * @param level [Int]
     */
    fun onTrimMemory(level: Int)
}