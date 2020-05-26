package com.linwei.frame.manager

import com.linwei.frame.config.PlatformConfig
import org.greenrobot.eventbus.EventBus

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 内部提供RxBus、EventBus,Otto事件总线，根据Maven地址动态切换。
 *               如果需要用RxBus,EventBus,Otto库，需要在项目app build.gradle的dependencies手动增加Maven地址，
 *               不使用就不要引入进来，减少包体积。
 *-----------------------------------------------------------------------
 */
class EventBusManager {

    companion object {
        private var INSTANCE: EventBusManager? = null

        @JvmStatic
        fun getInstance(): EventBusManager {
            return INSTANCE
                ?: EventBusManager().apply {
                INSTANCE = this
            }
        }
    }

    /**
     * {@link  subscriber}注册到事件总线
     */
     fun register(subscriber: Any?) {
        if (PlatformConfig.isExitsEventBusDependencies()) {
            EventBus.getDefault().register(subscriber)
        }
    }

    /**
     * 事件总线移除{@link  subscriber}注册
     */
    fun unRegister(subscriber: Any?) {
        if (PlatformConfig.isExitsEventBusDependencies()) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

}