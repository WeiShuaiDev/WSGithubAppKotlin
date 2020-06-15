package com.linwei.frame.base.delegate

import android.app.Application
import android.content.Context
import com.linwei.frame.base.App
import com.linwei.frame.base.global.ConfigModule
import com.linwei.frame.base.global.ManifestParser
import com.linwei.frame.base.lifecycle.AppLifecycles
import com.linwei.frame.di.component.AppComponent
//import com.linwei.frame.di.component.DaggerAppComponent
import com.linwei.frame.di.module.GlobalConfigModule
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AppDelegate constructor(
    private val mContext: Context,
    mManifestParser: ManifestParser = ManifestParser.getInstance(mContext)
) : AppLifecycles, App {

    @Inject
    lateinit var mActivityLifecycle: Application.ActivityLifecycleCallbacks

    lateinit var mApplication: Application

    //Application lifecycle 数据集
    private val mAppLifecycles: MutableList<AppLifecycles> = mutableListOf()

    //Activity lifecycle 数据集
    private val mActivityLifecycles: MutableList<Application.ActivityLifecycleCallbacks> =
        mutableListOf()

    //解析 AndroidManifest.xml 中 'meta-data' 的ConfigModule数据
    private val mConfigModuleLists: MutableList<ConfigModule> = mManifestParser.fetchConfigModule()

    init {
        //读取 AndroidManifest.xml 中 'meta-data' 数据
        mConfigModuleLists.forEach {

            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            it.injectAppLifecycle(mContext, mAppLifecycles)

            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            it.injectActivityLifecycle(mContext, mActivityLifecycles)
        }

    }

    override fun attachBaseContext(context: Context) {
        mAppLifecycles.forEach {
            it.attachBaseContext(context)
        }
    }

    override fun onCreate(application: Application) {
        this.mApplication = application
//        val mAppComponent = DaggerAppComponent
//            .builder()
//            .application(mApplication) //提供application
//            .globalConfigModule(getGlobalConfigModule(mApplication, mConfigModuleLists)) //全局配置
//            .build()
//        mAppComponent.inject(this)

        mAppLifecycles.forEach {
            it.onCreate(application)
        }
    }

    /**
     * 通过解析 AndroidManifest.xml 中 'meta-data' 的ConfigModule数据，通过回调 [ConfigModule.applyOptions] 方法，
     * 获取最新配置信息 [GlobalConfigModule] ,如果解析ConfigModule数据为空，则返回默认配置信息 [GlobalConfigModule]
     */
    private fun getGlobalConfigModule(
        app: Application,
        configModuleLists: MutableList<ConfigModule>?
    ): GlobalConfigModule {
        val builder: GlobalConfigModule.Builder = GlobalConfigModule.builder()
        configModuleLists?.forEach {
            it.applyOptions(app, builder)
        }
        return builder.build()
    }

    override fun onTerminate(application: Application) {
        mAppLifecycles.forEach {
            it.onTerminate(application)
        }
    }

    override fun getAppComponent(): AppComponent? {
        return null
    }
}