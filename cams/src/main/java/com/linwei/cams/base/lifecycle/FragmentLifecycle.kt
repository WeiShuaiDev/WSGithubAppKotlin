package com.linwei.cams.base.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.delegate.FragmentDelegate
import com.linwei.cams.base.delegate.FragmentDelegateImpl
import com.linwei.cams.base.fragment.IFragment
import com.linwei.cams.http.cache.Cache
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: [FragmentManager.FragmentLifecycleCallbacks] 默认实现类,通过 [FragmentDelegateImpl] 类代理 [Fragment]
 * 各个生命周期，再通过  [FragmentManager.FragmentLifecycleCallbacks] 回调各个生命周期，[FragmentLifecycle] 对象最终是交给
 * [ActivityLifecycle] 管理并注入
 *
 *-----------------------------------------------------------------------
 */
@Singleton
class FragmentLifecycle @Inject constructor() : FragmentManager.FragmentLifecycleCallbacks() {

    private var mFragmentDelegate: FragmentDelegate? = null

    override fun onFragmentAttached(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        context: Context
    ) {
        super.onFragmentAttached(fragmentManager, fragment, context)
        mFragmentDelegate = saveFragmentDelegate(fragmentManager, fragment)
        mFragmentDelegate?.onAttach(context)
    }

    override fun onFragmentCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentCreated(fragmentManager, fragment, savedInstanceState)
        mFragmentDelegate?.onCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager, fragment: Fragment, view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
        mFragmentDelegate?.onCreateView(view, savedInstanceState)
    }

    override fun onFragmentActivityCreated(
        fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fragmentManager, fragment, savedInstanceState)
        mFragmentDelegate?.onActivityCreate(savedInstanceState)
    }

    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStarted(fragmentManager, fragment)
        mFragmentDelegate?.onStart()
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentResumed(fragmentManager, fragment)
        mFragmentDelegate?.onResume()
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentPaused(fragmentManager, fragment)
        mFragmentDelegate?.onPause()
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentStopped(fragmentManager, fragment)
        mFragmentDelegate?.onStop()
    }

    override fun onFragmentSaveInstanceState(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        outState: Bundle
    ) {
        super.onFragmentSaveInstanceState(fragmentManager, fragment, outState)
        mFragmentDelegate?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentViewDestroyed(fragmentManager, fragment)
        mFragmentDelegate?.onDestroyView()
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDestroyed(fragmentManager, fragment)
        mFragmentDelegate?.onDestroy()
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        super.onFragmentDetached(fragmentManager, fragment)
        mFragmentDelegate?.onDetach()
    }

    /**
     * 调用 [fetchFragmentDelegate] 方法，根据 [FragmentDelegate.FRAGMENT_DELEGATE] 标识，获取 `Cache<String,Any>` 内存缓存数据。
     * 如果对象未创建，或者已经被jvm GC,则重新创建 [FragmentDelegateImpl] 对象，成功创建并缓存到内存中，缓存标识 [FragmentDelegate.FRAGMENT_DELEGATE]
     * @param fragmentManager [FragmentManager]
     * @param fragment [Fragment]
     */
    private fun saveFragmentDelegate(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ): FragmentDelegate? {
        var delegate: FragmentDelegate? = fetchFragmentDelegate(fragment)
        if (delegate == null) {
            delegate = FragmentDelegateImpl(fragmentManager, fragment)
            getCacheFromFragment(fragment)?.put(
                FragmentDelegate.FRAGMENT_DELEGATE,
                delegate
            )
        }
        return delegate
    }

    /**
     * 根据 [FragmentDelegate.FRAGMENT_DELEGATE] 标识，在 `Cache<String,Any>` 内存缓存中，获取 [FragmentDelegate] 对象。
     * 注意 `Kotlin` 中NULL对象，是无法强制转换，否则触发异常
     * @param fragment [Fragment]
     */
    private fun fetchFragmentDelegate(fragment: Fragment): FragmentDelegate? {
        val delegate: Any? =
            getCacheFromFragment(fragment)?.get(FragmentDelegate.FRAGMENT_DELEGATE)
        if (delegate != null)
            return delegate as FragmentDelegate
        return null
    }

    /**
     * 获取 [IFragment.provideCache] 中缓存对象 `Cache<String,Any>`
     * @param fragment [Fragment]
     */
    private fun getCacheFromFragment(fragment: Fragment): Cache<String, Any>? {
        if (fragment is IFragment) {
            (fragment as IFragment).provideCache()
        }
        return null
    }
}