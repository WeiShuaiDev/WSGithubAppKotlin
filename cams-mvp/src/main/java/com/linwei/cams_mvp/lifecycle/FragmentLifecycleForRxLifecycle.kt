package com.linwei.cams_mvp.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class FragmentLifecycleForRxLifecycle :
    FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        super.onFragmentAttached(fragmentManager, fragment, context)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fragmentManager, fragment, savedInstanceState)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
    }

    override fun onFragmentActivityCreated(
        fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fragmentManager, fragment, savedInstanceState)
    }

    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStarted(fragmentManager, fragment)
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fragmentManager, fragment)
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentPaused(fragmentManager, fragment)
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStopped(fragmentManager, fragment)
    }

    override fun onFragmentSaveInstanceState(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fragmentManager, fragment, outState)
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentViewDestroyed(fragmentManager, fragment)
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDestroyed(fragmentManager, fragment)
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDetached(fragmentManager, fragment)
    }
}