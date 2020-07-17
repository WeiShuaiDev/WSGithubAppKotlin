package com.linwei.cams_aac.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.ext.obtainAppComponent
import com.linwei.cams_aac.di.component.AacComponent
import com.linwei.cams_aac.di.component.DaggerAacComponent
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/10
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppLifecycleAacImpl : AppLifecycles {

    private lateinit var mAacComponent: AacComponent

    override fun attachBaseContext(context: Context) {
        Timber.i("AppLifecycleAacImpl to attachBaseContext!")
    }

    override fun onCreate(application: Application) {
        this.mAacComponent = DaggerAacComponent
            .builder()
            .appComponent(obtainAppComponent())
            .build()
        mAacComponent.inject(this)
    }

    override fun onTerminate(application: Application) {
        Timber.i("AppLifecycleAacImpl to onTerminate!")
    }

    override fun onLowMemory(application: Application) {
        Timber.i("AppLifecycleAacImpl to onLowMemory!")
    }

    override fun onTrimMemory(level: Int) {
        Timber.i("AppLifecycleAacImpl to onTrimMemory!")
    }
}