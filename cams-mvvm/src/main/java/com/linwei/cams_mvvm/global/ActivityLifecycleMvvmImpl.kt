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
 * @Description:
 *-----------------------------------------------------------------------
 */
@ActivityScope
class ActivityLifecycleMvvmImpl : ActivityLifecycleCallbacks {

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.i("ActivityLifecycleAacImpl to onActivitySaveInstanceState!")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("ActivityLifecycleAacImpl to onActivityCreated!")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.i("ActivityLifecycleAacImpl to onActivityResumed!")
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("ActivityLifecycleAacImpl to onActivityStarted!")
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.i("ActivityLifecycleAacImpl to onActivityPaused!")
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.i("ActivityLifecycleAacImpl to onActivityStopped!")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.i("ActivityLifecycleAacImpl to onActivityDestroyed!")
    }
}