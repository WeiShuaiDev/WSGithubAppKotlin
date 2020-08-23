package com.linwei.cams.base.delegate

import android.app.Application
import android.content.Context
import com.linwei.cams.base.App
import com.linwei.cams.base.global.CacheConstant
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.global.ManifestParser
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.di.component.DaggerAppComponent
import com.linwei.cams.di.module.GlobalConfigModule
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: [BaseApplication] 代理类，主要对框架内部 Activity 生命周期 [mActivityLifecycle]、开发者扩展 Activity 生命周期
 * [mActivityLifecycles] ，同时对开发者扩展 Application 生命周期 [mAppLifecycles] 进行注册。开发者扩展配置通过解析 AndroidManifest.xml
 * 清单文件，并把 [mConfigModuleLists] 配置信息，存储到内存中。
 *-----------------------------------------------------------------------
 */

class AppDelegate constructor(
    private val mContext: Context,
    mManifestParser: ManifestParser = ManifestParser.getInstance(mContext)
) : AppLifecycles, App {

    @Inject
    lateinit var mActivityLifecycle: Application.ActivityLifecycleCallbacks

    //Application lifecycle 数据集
    private val mAppLifecycles: MutableList<AppLifecycles> = mutableListOf()

    //Activity lifecycle 数据集
    private val mActivityLifecycles: MutableList<Application.ActivityLifecycleCallbacks> =
        mutableListOf()

    //解析 AndroidManifest.xml 中 'meta-data' 的ConfigModule数据
    private val mConfigModuleLists: MutableList<ConfigModule> = mManifestParser.obtainConfigModule()

    private lateinit var mApplication: Application

    private lateinit var mAppComponent: AppComponent

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
        //回调开发者扩展的 `AppLifecycles.attachBaseContext()` 方法
        mAppLifecycles.forEach {
            it.attachBaseContext(context)
        }
    }


    override fun onCreate(application: Application, appDelegate: AppDelegate?) {
        this.mApplication = application
        this.mAppComponent = DaggerAppComponent
            .builder()
            .application(mApplication) //提供application
            .globalConfigModule(getGlobalConfigModule(mApplication, mConfigModuleLists)) //全局配置
            .build()
        mAppComponent.inject(this)

        //根据 `CacheConstant.CACHE_CONFIG_MODULE` 标识，保存list<ConfigModule>数据
        mAppComponent.extras().put(CacheConstant.CACHE_CONFIG_MODULE, mConfigModuleLists)

        //注册框架内部已实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle)

        //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
        //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
        //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)
        mActivityLifecycles.forEach {
            mApplication.registerActivityLifecycleCallbacks(it)
        }

        //回调开发者扩展的 `AppLifecycles.onCreate()` 方法
        mAppLifecycles.forEach {
            it.onCreate(application, this)
        }
    }

    override fun onTerminate(application: Application) {
        mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle)

        mActivityLifecycles.forEach {
            mApplication.unregisterActivityLifecycleCallbacks(it)
        }
        //回调开发者扩展的 `AppLifecycles.onTerminate()` 方法
        mAppLifecycles.forEach {
            it.onTerminate(application)
        }
    }

    override fun onLowMemory(application: Application) {

    }

    override fun onTrimMemory(level: Int) {

    }

    /**
     * 通过解析 AndroidManifest.xml 中 'meta-data' 的ConfigModule数据，通过回调 [ConfigModule.applyOptions] 方法，给开发者扩张 `Builder` 配置，
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

    override fun getAppComponent(): AppComponent {
        return mAppComponent
    }

    override fun getApplication(): Application {
        return mApplication
    }
}