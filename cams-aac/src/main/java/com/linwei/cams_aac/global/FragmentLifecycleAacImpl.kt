package com.linwei.cams_aac.global

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
 * @Description:
 *-----------------------------------------------------------------------
 */
@FragmentScope
class FragmentLifecycleAacImpl :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentAttached!")
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentCreated!")
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentViewCreated!")
    }


    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentStarted!")
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentResumed!")
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentPaused!")
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentStopped!")
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentViewDestroyed!")
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentDestroyed!")
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        Timber.i("FragmentLifecycleAacImpl to onFragmentDetached!")
    }

}