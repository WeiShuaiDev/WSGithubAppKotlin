package com.linwei.frame.common

import android.os.Handler

/**
 * @Author: WS
 * @Time: 2019/10/14
 * @Description: Handler基类
 */
class HandlerMessage private constructor() {

    var handler: Handler? = null

    private var mTaskList: MutableList<Runnable?> = mutableListOf()

    companion object {
        val instance by lazy { HandlerMessage() }
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

    private fun getMainThreadId(): Long {
        return BaseApp.mContext.applicationContext.mainLooper.thread.id
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