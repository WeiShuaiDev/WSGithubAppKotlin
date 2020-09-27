package com.linwei.cams.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import com.linwei.cams.base.delegate.AppDelegate
import com.linwei.cams.config.LibConfig
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.utils.AppLanguageUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [BaseApplication] 回调 `Application` 代理类 [AppDelegate],并在 `Application` 生命周期
 * 方法注入国际化处理。
 *----------------------------------------------------------------------
 */
open class BaseApplication : MultiDexApplication(), App{
    private lateinit var mAppDelegate: AppDelegate

    companion object {
        lateinit var mContext: Context
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(context, LibConfig.LANGUAGE))
        mContext = context
        mAppDelegate = AppDelegate(context)
        mAppDelegate.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
        mAppDelegate.onCreate(this, null)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mAppDelegate.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mAppDelegate.onTrimMemory(level)
    }

    override fun getAppComponent(): AppComponent = mAppDelegate.getAppComponent()

    override fun getApplication(): Application = mAppDelegate.getApplication()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
    }
}