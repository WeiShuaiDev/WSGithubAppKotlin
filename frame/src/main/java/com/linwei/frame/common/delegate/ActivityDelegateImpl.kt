package com.linwei.frame.common.delegate

import android.app.Activity
import android.os.Bundle
import com.linwei.frame.common.activity.IActivity
import com.linwei.frame.manager.EventBusManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: ActivityDelegateImpl类实现ActivityDelegate,并重写所有方法,
 *              允许在生命周期过程处理一些逻辑。
 *-----------------------------------------------------------------------
 */
class ActivityDelegateImpl(
    var mActivity: Activity?
) : ActivityDelegate {
    private var mIActivity: IActivity? = null

    init {
        if (mActivity is IActivity) {
            mIActivity = mActivity as IActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle) {
        //当前对象保存在内存栈
        mIActivity?.addStackSingleActivity(mActivity)
        //当前对象注册到事件总线
        if (mIActivity?.useEventBus() == true) {
            EventBusManager.getInstance().register(mActivity)
        }

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

    override fun onDestroy() {
        //当前对象从事件总线中移除
        if (mIActivity?.useEventBus() == true) {
            EventBusManager.getInstance().register(mActivity)
        }
        //当前对象从内存栈中移除
        mIActivity?.removeStackSingleActivity(mActivity)
        mIActivity = null
        mActivity = null
    }

}