package com.linwei.cams_mvvm.global

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.FragmentScope
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `FragmentLifecycle` 生命周期增加到 `FragmentLifecycleCallbacks` 回调，
 *                进而注入到 `Fragment` 整个生命周期中。
 *-----------------------------------------------------------------------
 */
class FragmentLifecycleMvvmImpl :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentAttached!")
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentCreated!")
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentViewCreated!")
    }


    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentStarted!")
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentResumed!")
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentPaused!")
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentStopped!")
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentViewDestroyed!")
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentDestroyed!")
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleMvvmImpl to onFragmentDetached!")
    }

}