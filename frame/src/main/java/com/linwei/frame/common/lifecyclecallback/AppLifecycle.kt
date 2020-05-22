package com.linwei.frame.common.lifecyclecallback

import android.app.Application
import android.content.Context

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface AppLifecycle {

    fun attachBaseContext(context: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}