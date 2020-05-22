package com.linwei.frame.common.lifecyclecallback

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.frame.common.delegate.ActivityDelegate
import com.linwei.frame.common.delegate.ActivityDelegateImpl
import com.linwei.frame.common.delegate.FragmentDelegate
import com.linwei.frame.common.delegate.FragmentDelegateImpl

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: {@link Application.ActivityLifecycleCallbacks} 默认实现类
 *              通过 {@link FragmentDelegate} 管理 {@link Fragment}
 *-----------------------------------------------------------------------
 */
class FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        super.onFragmentAttached(fragmentManager, fragment, context)
        fetchFragmentDelegate(fragment).onAttach(context)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fragmentManager, fragment, savedInstanceState)
        fetchFragmentDelegate(fragment).onCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
        fetchFragmentDelegate(fragment).onCreateView(view, savedInstanceState)
    }

    override fun onFragmentActivityCreated(
        fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fragmentManager, fragment, savedInstanceState)
        fetchFragmentDelegate(fragment).onActivityCreate(savedInstanceState)
    }

    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStarted(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onStart()
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onResume()
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentPaused(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onPause()
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStopped(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onStop()
    }

    override fun onFragmentSaveInstanceState(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fragmentManager, fragment, outState)
        fetchFragmentDelegate(fragment).onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentViewDestroyed(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onDestroyView()
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDestroyed(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onDestroy()
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDetached(fragmentManager, fragment)
        fetchFragmentDelegate(fragment).onDetach()
    }

    private fun fetchFragmentDelegate(fragment: Fragment): FragmentDelegate {
        return FragmentDelegateImpl()
    }
}