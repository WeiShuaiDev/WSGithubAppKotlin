package com.linwei.frame.base.delegate

import android.app.Application
import android.content.Context
import com.linwei.frame.base.App
import com.linwei.frame.base.lifecycle.AppLifecycles
import com.linwei.frame.di.component.AppComponent
import javax.inject.Inject
import javax.inject.Named

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppDelegate : AppLifecycles, App {

    @Inject
    @Named("ActivityLifecycle")
    lateinit var mActivityLifecycle: Application.ActivityLifecycleCallbacks

    override fun attachBaseContext(context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate(application: Application) {
        TODO("Not yet implemented")
    }

    override fun getAppComponent(): AppComponent {
        TODO("Not yet implemented")
    }
}