package com.linwei.cams_mvvm.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.base.lifecycle.AppLifecycles
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/10
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `Application` 回调。
 *-----------------------------------------------------------------------
 */
class AppLifecycleMvvmImpl : AppLifecycles {

    override fun attachBaseContext(context: Context) {
        Timber.i("AppLifecycleMvvmImpl to attachBaseContext!")
    }

    override fun onCreate(application: Application,appDelegate: AppDelegate?) {
        Timber.i("AppLifecycleMvvmImpl to onCreate!")
    }

    override fun onTerminate(application: Application) {
        Timber.i("AppLifecycleMvvmImpl to onTerminate!")
    }

    override fun onLowMemory(application: Application) {
        Timber.i("AppLifecycleMvvmImpl to onLowMemory!")
    }

    override fun onTrimMemory(level: Int) {
        Timber.i("AppLifecycleMvvmImpl to onTrimMemory!")
    }
}