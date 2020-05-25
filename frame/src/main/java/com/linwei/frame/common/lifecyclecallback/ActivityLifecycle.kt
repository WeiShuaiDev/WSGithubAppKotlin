package com.linwei.frame.common.lifecyclecallback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.linwei.frame.common.delegate.ActivityDelegate
import com.linwei.frame.common.delegate.ActivityDelegateImpl

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
class ActivityLifecycle : Application.ActivityLifecycleCallbacks {

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        fetchActivityDelegate(activity).onCreate(savedInstanceState)
    }


    override fun onActivityStarted(activity: Activity) {
        fetchActivityDelegate(activity).onStart()
    }


    override fun onActivityResumed(activity: Activity) {
        fetchActivityDelegate(activity).onResume()
    }


    override fun onActivityPaused(activity: Activity) {
        fetchActivityDelegate(activity).onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        fetchActivityDelegate(activity).onStop()
    }

    override fun onActivityDestroyed(activity: Activity) {
        fetchActivityDelegate(activity).onDestroy()
    }

    private fun fetchActivityDelegate(activity: Activity): ActivityDelegate {
        return ActivityDelegateImpl(activity)
    }

}