package com.linwei.github_mvvm.global

import android.app.Application
import android.content.Context
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.github_mvvm.di.component.GithubComponent
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppLifecycleImpl : AppLifecycles {

    private lateinit var mGithubComponent: GithubComponent

    override fun attachBaseContext(context: Context) {
//        this.mGithubComponent = DaggerGithubComponent.builder().build()
//        mGithubComponent.inject(this)
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