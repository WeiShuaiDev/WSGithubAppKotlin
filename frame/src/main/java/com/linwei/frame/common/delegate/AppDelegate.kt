package com.linwei.frame.common.delegate

import android.app.Application
import android.content.Context
import com.linwei.frame.common.lifecyclecallback.AppLifecycle
import dagger.internal.DaggerCollections

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppDelegate : AppLifecycle {
    override fun attachBaseContext(context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate(application: Application) {
        TODO("Not yet implemented")
    }
}