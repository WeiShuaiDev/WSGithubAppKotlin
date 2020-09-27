package com.linwei.cams_mvvm.global

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.linwei.cams.di.scope.ActivityScope
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `ActivityLifecycle` 生命周期增加到 `ActivityLifecycleCallbacks` 回调，
 *                进而注入到 `Activity` 整个生命周期中。
 *-----------------------------------------------------------------------
 */
class ActivityLifecycleMvvmImpl : ActivityLifecycleCallbacks {

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivitySaveInstanceState!")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityCreated!")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityResumed!")
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityStarted!")
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityPaused!")
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityStopped!")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.i("ActivityLifecycleMvvmImpl to onActivityDestroyed!")
    }
}