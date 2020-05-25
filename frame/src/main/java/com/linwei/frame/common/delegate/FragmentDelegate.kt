package com.linwei.frame.common.delegate

import android.content.Context
import android.os.Bundle
import android.view.View

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: FragmentDelegate类提供Fragment生命周期，在FragmentDelegate类中方法通过
 *               FragmentManager.FragmentLifecycleCallbacks回调，实现Fragment整个生命周期流程
 *-----------------------------------------------------------------------
 */
interface FragmentDelegate {

    fun onAttach(context: Context)

    fun onCreate(savedInstanceState: Bundle?)

    fun onCreateView(view: View, savedInstanceState: Bundle?)

    fun onActivityCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

}