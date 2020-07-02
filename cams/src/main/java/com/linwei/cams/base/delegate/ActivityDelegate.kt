package com.linwei.cams.base.delegate

import android.os.Bundle

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: ActivityDelegate类提供Activity生命周期，在ActivityDelegate类中方法通过
 *               Application.ActivityLifecycleCallbacks回调，实现Activity整个生命周期流程
 *-----------------------------------------------------------------------
 */
interface ActivityDelegate {
    companion object {
        const val ACTIVITY_DELEGATE: String = "ACTIVITY_DELEGATE"
    }

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroy()
}