package com.linwei.cams_mvp.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.FragmentScope
import com.trello.rxlifecycle4.android.FragmentEvent
import io.reactivex.subjects.Subject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@FragmentScope
class FragmentLifecycleForRxLifecycle :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        obtainSubject(fragment)?.onNext(FragmentEvent.ATTACH)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        obtainSubject(fragment)?.onNext(FragmentEvent.CREATE)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        obtainSubject(fragment)?.onNext(FragmentEvent.CREATE_VIEW)
    }


    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.START)
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.RESUME)
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.PAUSE)
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.STOP)
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.DESTROY_VIEW)
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.DESTROY)
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        obtainSubject(fragment)?.onNext(FragmentEvent.DETACH)
    }

    /**
     * 父类 [BaseMvpFragment] 集成 [FragmentRxLifecycle],并重写 `provideLifecycleSubject` 方法
     * @param fragment [Fragment]
     * @return Subject<ActivityEvent>?
     */
    private fun obtainSubject(fragment: Fragment): Subject<FragmentEvent>? {
        if (fragment is FragmentRxLifecycle) {
            return fragment.provideLifecycleSubject()
        }
        return null
    }
}