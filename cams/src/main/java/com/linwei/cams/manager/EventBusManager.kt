package com.linwei.cams.manager
import com.linwei.cams.config.PlatformConfig
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

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
class EventBusManager private constructor() {

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
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * {@link subscriber} 绑定事件总线
     * @param subscriber
     */
    fun register(subscriber: Any?) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().register(subscriber)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * {@link subscriber} 解除绑定
     * @param subscriber
     */
    fun unRegister(subscriber: Any?) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * 发送 {@code event} 事件
     * @param evnet 事件
     */
    fun post(event: Any?) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().post(event)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * 发送 {@code event} 黏性事件
     * @param evnet 事件
     */
    fun postSticky(event: Any?) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().postSticky(event)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * 注销 {@code event} 黏性事件
     * @param evnet 事件
     */
    fun removeStickyEvent(event: Any?) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().removeStickyEvent(event)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * 注销 {@code eventType} 黏性事件
     *  @param eventType 事件类型
     */
    fun removeStickyEvent(eventType: Class<Any>) {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.getDefault().removeStickyEvent(eventType)
        }
    }

    /**
     * 判断 {@link DEPENDENCY_EVENTBUS} 在 dependencies{} 中依赖EventBus远程库
     * 清除订阅者和事件的缓存
     */
    fun clear() {
        if (PlatformConfig.DEPENDENCY_EVENTBUS) {
            EventBus.clearCaches()
        }
    }
}