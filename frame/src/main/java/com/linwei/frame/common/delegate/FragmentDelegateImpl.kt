package com.linwei.frame.common.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.linwei.frame.common.fragment.IFragment
import com.linwei.frame.utils.EventBusManager

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
class FragmentDelegateImpl(var mFragmentManager: FragmentManager?, var mFragment: Fragment?) :
    FragmentDelegate {
    private lateinit var mContext: Context
    private var mIFragment: IFragment? = null

    init {
        if (mFragment is IFragment) {
            mIFragment = mFragment as IFragment
        }
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
        TODO("Not yet implemented")
    }

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

}