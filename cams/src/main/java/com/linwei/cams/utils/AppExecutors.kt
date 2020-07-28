package com.linwei.cams.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/17
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 线程池类
 *-----------------------------------------------------------------------
 */
const val THREAD_COUNT = 3

open class AppExecutors constructor(
    val diskIO: Executor = DiskIOThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(THREAD_COUNT),
    val mainThread: Executor = MainThreadExecutor()
) {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(runnable: Runnable) {
            mainThreadHandler.post(runnable)
        }

    }

    private class DiskIOThreadExecutor : Executor {
        private val diskIO = Executors.newSingleThreadExecutor()
        override fun execute(runnable: Runnable) {
            diskIO.execute(runnable)
        }
    }

}