package com.linwei.frame.base

import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.linwei.frame.base.delegate.AppDelegate
import com.linwei.frame.config.LibConfig
import com.linwei.frame.di.component.AppComponent
import com.linwei.frame.utils.AppLanguageUtils

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: BaseApplication基类
 */
class BaseApplication() : MultiDexApplication(), App {
    private lateinit var mAppDelegate: AppDelegate

    companion object {
        lateinit var mContext: Context
        lateinit var mInstance: BaseApplication
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(context, LibConfig.LANGUAGE))
        MultiDex.install(this)
        mAppDelegate = AppDelegate(context)
        mAppDelegate.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        mContext = this
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
        LibConfig.initLib(this)

        mAppDelegate.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }

    override fun getAppComponent(): AppComponent? = mAppDelegate.getAppComponent()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
    }


}