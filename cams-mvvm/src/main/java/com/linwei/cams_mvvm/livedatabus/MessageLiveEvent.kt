package com.linwei.cams_mvvm.livedatabus

import android.os.Message
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构消息事件总线，提供 `observe`、`observeForever` 两种方式。
 *-----------------------------------------------------------------------
 */
class MessageLiveEvent : LiveDataEvent<Message>() {

    fun observe(owner: LifecycleOwner, observer: MessageLiveObserver) {
        super.observe(owner, Observer {
            if (it.obj != null)
                observer.onNewMessage(it)
        })
    }

    fun observeForever(observer: MessageLiveObserver) {
        super.observeForever {
            if (it.obj != null)
                observer.onNewMessage(it)
        }
    }

    interface MessageLiveObserver {
        fun onNewMessage(message: Message?)
    }

}