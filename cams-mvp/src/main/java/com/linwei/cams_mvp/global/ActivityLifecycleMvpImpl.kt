package com.linwei.cams_mvp.global

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvp.lifecycle.ActivityRxLifecycle
import com.trello.rxlifecycle4.android.ActivityEvent
import io.reactivex.subjects.Subject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 `ActivityLifecycle` 生命周期增加到 `ActivityLifecycleCallbacks` 回调，
 *                进而注入到 `Activity` 整个生命周期中。
 *-----------------------------------------------------------------------
 */
class ActivityLifecycleMvpImpl : ActivityLifecycleCallbacks {

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        obtainSubject(activity)?.onNext(ActivityEvent.CREATE)
    }

    override fun onActivityResumed(activity: Activity) {
        obtainSubject(activity)?.onNext(ActivityEvent.RESUME)
    }

    override fun onActivityStarted(activity: Activity) {
        obtainSubject(activity)?.onNext(ActivityEvent.START)
    }

    override fun onActivityPaused(activity: Activity) {
        obtainSubject(activity)?.onNext(ActivityEvent.PAUSE)
    }

    override fun onActivityStopped(activity: Activity) {
        obtainSubject(activity)?.onNext(ActivityEvent.STOP)
    }

    override fun onActivityDestroyed(activity: Activity) {
        obtainSubject(activity)?.onNext(ActivityEvent.DESTROY)
    }

    /**
     * 父类 [BaseMvpActivity] 集成 [ActivityRxLifecycle],并重写 `provideLifecycleSubject` 方法
     * @param activity [Activity]
     * @return Subject<ActivityEvent>?
     */
    private fun obtainSubject(activity: Activity): Subject<ActivityEvent>? {
        if (activity is ActivityRxLifecycle) {
            return activity.provideLifecycleSubject()
        }
        return null
    }
}