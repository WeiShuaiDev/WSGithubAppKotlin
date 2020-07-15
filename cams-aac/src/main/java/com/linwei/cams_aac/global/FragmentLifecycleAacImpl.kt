package com.linwei.cams_aac.global

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.cams.di.scope.FragmentScope

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
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
    }


    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
    }

}