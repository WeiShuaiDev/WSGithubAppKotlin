package com.linwei.cams_mvvm_template.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.base.lifecycle.AppLifecycles
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppLifecycleImpl : AppLifecycles {

    override fun attachBaseContext(context: Context) {
        Timber.i("AppLifecycleImpl to attachBaseContext!")
    }

    override fun onCreate(application: Application, appDelegate: AppDelegate?) {
        Timber.i("AppLifecycleImpl to onCreate!")
    }

    override fun onTerminate(application: Application) {
        Timber.i("AppLifecycleImpl to onTerminate!")
    }

    override fun onLowMemory(application: Application) {
        Timber.i("AppLifecycleImpl to onLowMemory!")
    }

    override fun onTrimMemory(level: Int) {
        Timber.i("AppLifecycleImpl to onTrimMemory!")
    }
}