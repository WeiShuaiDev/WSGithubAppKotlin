package com.linwei.cams_mvp.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.base.lifecycle.AppLifecycles

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 `Application` 回调。
 *-----------------------------------------------------------------------
 */
class AppLifecycleMvpImpl : AppLifecycles {

    override fun attachBaseContext(context: Context) {
    }

    override fun onCreate(application: Application,appDelegate: AppDelegate?) {
    }

    override fun onTerminate(application: Application) {
    }

    override fun onLowMemory(application: Application) {
    }

    override fun onTrimMemory(level: Int) {
    }
}