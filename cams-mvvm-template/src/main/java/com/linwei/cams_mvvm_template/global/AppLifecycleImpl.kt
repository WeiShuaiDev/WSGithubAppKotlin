package com.linwei.cams_mvvm_template.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams_mvvm_template.di.component.DaggerTemplateComponent
import com.linwei.cams_mvvm_template.di.component.TemplateComponent
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

    private lateinit var mTemplateComponent: TemplateComponent

    override fun attachBaseContext(context: Context) {
        this.mTemplateComponent = DaggerTemplateComponent.builder().build()
        mTemplateComponent.inject(this)
    }

    override fun onCreate(application: Application) {
        Timber.i("AppLifecycleImpl to attachBaseContext!")
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