package com.linwei.cams_mvp.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.ActivityScope
import com.linwei.cams_mvp.di.qualifier.ActivityRxLifecycleQualifier
import com.linwei.cams_mvp.di.qualifier.FragmentRxLifecycleQualifier
import com.trello.rxlifecycle4.android.ActivityEvent
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Singleton
class ActivityLifecycleForRxLifecycle : ActivityLifecycleCallbacks {

    @Inject
    @FragmentRxLifecycleQualifier
    lateinit var mFragmentRxLifecycle: FragmentManager.FragmentLifecycleCallbacks


    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        obtainSubject(activity)?.onNext(ActivityEvent.CREATE)

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                mFragmentRxLifecycle,
                true
            )
        }
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