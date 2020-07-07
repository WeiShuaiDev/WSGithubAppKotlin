package com.linwei.cams.base.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.activity.IActivity
import com.linwei.cams.base.delegate.ActivityDelegate
import com.linwei.cams.base.delegate.ActivityDelegateImpl
import com.linwei.cams.base.global.CacheConstant
import com.linwei.cams.http.cache.Cache
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: {@link Application.ActivityLifecycleCallbacks} 默认实现类
 *              通过 {@link ActivityDelegate} 管理 {@link Activity}
 *-----------------------------------------------------------------------
 */
@Singleton
class ActivityLifecycle @Inject constructor() : Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var mApplication: Application

    @Inject
    lateinit var mExtrasCache: Cache<String, Any>

    @Inject
    lateinit var mFragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks

    //Activity lifecycle 数据集
    private val mFragmentLifecycleLists: MutableList<FragmentManager.FragmentLifecycleCallbacks> =
        mutableListOf()

    private var mActivityDelegate: ActivityDelegate? = null

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mActivityDelegate = saveActivityDelegate(activity)
        mActivityDelegate?.onCreate(savedInstanceState)
        //FragmentLifecycle 生命周期注册到 ActivityLifecycle 中
        registerFragmentCallbacks(activity)
    }

    /**
     * 通过 [IActivity.useFragment] 状态绑定 [Fragment] 生命周期，绑定 [mFragmentLifecycle] 生命周期回调，是框架默认自定义回调。
     * [mFragmentLifecycles] 生命周期回调,是开发者扩展自定义回调，该回调通过 [mExtras] 缓存中获取 [ConfigModule.injectFragmentLifecycle],
     * [ConfigModule] 配置信息是通过解析 AndroidManifest.xml清单文件获得。
     * @param activity [Activity]
     */
    private fun registerFragmentCallbacks(activity: Activity) {
        val useFragment: Boolean = activity is IActivity && (activity as IActivity).useFragment()
        if (useFragment && activity is FragmentActivity) {
            activity.run {
                //默认 FragmentLifecycleCallbacks 注入
                supportFragmentManager.registerFragmentLifecycleCallbacks(
                    mFragmentLifecycle,
                    true
                )
                val configModule: Any? =
                    if (mExtrasCache.containsKey(CacheConstant.CACHE_CONFIG_MODULE))
                        mExtrasCache.get(CacheConstant.CACHE_CONFIG_MODULE) else null
                configModule?.let { module ->
                    if (module is MutableList<*>) {
                        val configModuleLists: MutableList<*> = module
                        configModuleLists.forEach { configModuleItem ->
                            if (configModuleItem is ConfigModule) {
                                configModuleItem.injectFragmentLifecycle(
                                    activity,
                                    mFragmentLifecycleLists
                                )
                            }
                        }
                    }
                    mExtrasCache.remove(CacheConstant.CACHE_CONFIG_MODULE)
                }

                // 开发者扩展 FragmentLifecycleCallbacks 注入
                mFragmentLifecycleLists.forEach { lifecycle ->
                    supportFragmentManager.registerFragmentLifecycleCallbacks(
                        lifecycle,
                        true
                    )
                }
            }
        }
    }


    override fun onActivityStarted(activity: Activity) {
        mActivityDelegate?.onStart()
    }


    override fun onActivityResumed(activity: Activity) {
        mActivityDelegate?.onResume()
    }


    override fun onActivityPaused(activity: Activity) {
        mActivityDelegate?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        mActivityDelegate?.onStop()
    }

    override fun onActivityDestroyed(activity: Activity) {
        mActivityDelegate?.onDestroy()
        //清除 Activity Cache 缓存数据
        getCacheFromActivity(activity)?.clear()
    }

    /**
     * 调用 [fetchActivityDelegate] 方法，根据 [ActivityDelegate.ACTIVITY_DELEGATE] 标识，获取 `Cache<String,Any>` 内存缓存数据。
     * 如果对象未创建，或者已经被jvm GC,则重新创建 [ActivityDelegateImpl] 对象，成功创建并缓存到内存中，缓存标识 [ActivityDelegate.ACTIVITY_DELEGATE]
     * @param activity [Activity]
     * @return delegate [ActivityDelegate]
     */
    private fun saveActivityDelegate(
        activity: Activity
    ): ActivityDelegate? {
        var delegate: ActivityDelegate? = fetchActivityDelegate(activity)
        if (delegate == null) {
            delegate = ActivityDelegateImpl(activity)
            getCacheFromActivity(activity)?.put(
                ActivityDelegate.ACTIVITY_DELEGATE,
                delegate
            )
        }
        return delegate
    }

    /**
     * 根据 [ActivityDelegate.ACTIVITY_DELEGATE] 标识，在 `Cache<String,Any>` 内存缓存中，获取 [ActivityDelegate] 对象。
     * 注意 `Kotlin` 中NULL对象，是无法强制转换，否则触发异常
     * @param activity [Activity]
     * @return delegate [ActivityDelegate]
     */
    private fun fetchActivityDelegate(activity: Activity): ActivityDelegate? {
        val delegate: Any? =
            getCacheFromActivity(activity)?.get(ActivityDelegate.ACTIVITY_DELEGATE)
        if (delegate != null)
            return delegate as ActivityDelegate
        return null
    }

    /**
     * 获取 [IActivity.provideCache] 中缓存对象 `Cache<String,Any>`
     * @param activity [Activity]
     * @return cache [CacheType.activityCacheType] 类型缓存对象
     */
    private fun getCacheFromActivity(activity: Activity): Cache<String, Any>? {
        if (activity is IActivity) {
            return (activity as IActivity).provideCache()
        }
        return null
    }
}

