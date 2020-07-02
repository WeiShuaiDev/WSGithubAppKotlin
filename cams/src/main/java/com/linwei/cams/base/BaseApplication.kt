package com.linwei.cams.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
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
class BaseApplication() : Application(), App {
    private lateinit var mAppDelegate: AppDelegate

    companion object {
        lateinit var mContext: Context
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(context, LibConfig.LANGUAGE))
        mAppDelegate = AppDelegate(context)
        mAppDelegate.attachBaseContext(context)
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
        mAppDelegate.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate.onTerminate(this)
    }

    override fun getAppComponent(): AppComponent = mAppDelegate.getAppComponent()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppLanguageUtils.setLanguage(this, LibConfig.LANGUAGE)
    }
}