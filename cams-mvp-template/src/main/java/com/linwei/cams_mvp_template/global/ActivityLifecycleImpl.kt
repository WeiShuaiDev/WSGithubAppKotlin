package com.linwei.cams_mvp_template.global

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.linwei.cams.di.scope.ActivityScope
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/6
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Timber.i("ActivityLifecycleImpl to onActivitySaveInstanceState!")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("ActivityLifecycleImpl to onActivityCreated!")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.i("ActivityLifecycleImpl to onActivityResumed!")
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.i("ActivityLifecycleImpl to onActivityStarted!")
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.i("ActivityLifecycleImpl to onActivityPaused!")
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.i("ActivityLifecycleImpl to onActivityStopped!")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.i("ActivityLifecycleImpl to onActivityDestroyed!")
    }
}