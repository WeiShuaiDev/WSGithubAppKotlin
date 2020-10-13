package com.linwei.cams_mvvm.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.linwei.cams.http.model.StatusCode

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  MVVM架构状态事件总线，提供 `observe`、`observeForever` 两种方式。
 *          这里定义四种状态值: `StatusCode.LOADING`:加载中；`StatusCode.SUCCESS`: 成功状态；
 *          `StatusCode.FAILURE`:失败状态; `StatusCode.ERROR`: 错误状态
 *-----------------------------------------------------------------------
 */
class StatusLiveEvent : LiveDataEvent<@StatusCode Int>() {

    fun observe(owner: LifecycleOwner, observer: StatusLiveObserver) {
        super.observe(owner, Observer {
            if (it != null)
                observer.onStatusChanges(it)
        })
    }

    fun observeForever(observer: StatusLiveObserver) {
        super.observeForever {
            if (it != null)
                observer.onStatusChanges(it)
        }
    }

    interface StatusLiveObserver {
        fun onStatusChanges(@StatusCode status: Int?)
    }

}