package com.linwei.cams.base.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.fragment.IFragment
import com.linwei.cams.ext.obtainAppComponent
import com.linwei.cams.manager.EventBusManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: FragmentDelegateImpl类实现FragmentDelegate,并重写所有方法,
 *              允许在生命周期过程处理一些逻辑。
 *-----------------------------------------------------------------------
 */
class FragmentDelegateImpl(
    private var mFragmentManager: FragmentManager?,
    private var mFragment: Fragment?
) :
    FragmentDelegate {
    private lateinit var mContext: Context
    private var mIFragment: IFragment? = null

    init {
        if (mFragment is IFragment) {
            mIFragment = mFragment as IFragment
        }

        mIFragment?.setupFragmentComponent(obtainAppComponent())
    }


    override fun onAttach(context: Context) {
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (mIFragment?.useEventBus() == true) {
            EventBusManager.getInstance().register(mFragment)
        }
    }

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
    }

    override fun onActivityCreate(savedInstanceState: Bundle?) {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onDestroyView() {
    }

    override fun onDestroy() {
        if (mIFragment?.useEventBus() == true) {
            EventBusManager.getInstance().unRegister(mFragment)
        }
        mIFragment = null
        mFragment = null
        mFragmentManager = null
    }

    override fun onDetach() {
    }
}