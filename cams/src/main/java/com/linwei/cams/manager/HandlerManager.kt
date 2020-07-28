package com.linwei.cams.manager

import android.os.Handler
import com.linwei.cams.base.BaseApplication
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/2
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 数据配置中心，主要初始化配置，第三方库初始化
 *-----------------------------------------------------------------------
 */
class HandlerManager private constructor() {

    var handler: Handler? = null

    private var mTaskList: MutableList<Runnable?> = mutableListOf()

    companion object {
        private var INSTANCE: HandlerManager? = null

        @JvmStatic
        fun getInstance(): HandlerManager {
            return INSTANCE
                ?: HandlerManager().apply {
                    INSTANCE = this
                }
        }
    }

    /**
     * 安全的执行一个任务
     *
     */
    fun postTaskSafely(task: Runnable) {
        val curThreadId = Thread.currentThread().id
        // 如果当前线程是主线程
        if (curThreadId == getMainThreadId()) {
            task.run()
        } else {
            // 如果当前线程不是主线程
            if (handler == null) {
                handler = Handler()
            }
            mTaskList.add(task)
            handler?.post(task)
        }
    }

    /**
     *获取主线程ID
     */
    private fun getMainThreadId(): Long {
        return BaseApplication.mContext.applicationContext.mainLooper.thread.id
    }

    /**
     * 延迟执行任务
     *
     */
    fun postTaskDelay(task: Runnable, delayMillis: Int) {
        if (handler == null) {
            handler = Handler()
        }
        mTaskList.add(task)
        handler?.postDelayed(task, delayMillis.toLong())
    }

    /**
     * 移除所有任务
     */
    fun removeTask() {
        handler?.apply {
            for (task in mTaskList) {
                if (task == null) {
                    continue
                }
                removeCallbacks(task)
            }
            mTaskList = mutableListOf()
        }
        handler = null
    }

}