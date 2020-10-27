package com.linwei.cams_mvvm.mvvm

import android.app.Application
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.livedatabus.MessageLiveEvent
import com.linwei.cams_mvvm.livedatabus.StatusLiveEvent
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: MVVM架构 `Model` 模块，提供 [MessageLiveEvent]消息事件总线、[StatusLiveEvent]状态事件总线功能。
 *-----------------------------------------------------------------------
 */
open class BaseViewModel @Inject constructor(
        private val model: BaseModel,
        application: Application
) : AndroidViewModel(application),
        IViewModel {

    /**
     * [MessageLiveEvent] 消息事件总线
     */
    @Inject
    lateinit var mMessageLiveEvent: MessageLiveEvent

    /**
     * [StatusLiveEvent] 状态事件总线
     */
    @Inject
    lateinit var mStatusLiveEvent: StatusLiveEvent

    /**
     * 生命周期对象
     */
    var mLifecycleOwner: LifecycleOwner? = null

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        model.onDestroy()
        mMessageLiveEvent.call()
        mStatusLiveEvent.call()
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    /**
     * 获取注册进来的 [BaseModel] 模块
     * @return [BaseModel]
     *
     */
    fun fetchBaseModel(): BaseModel = model

    /**
     * 同步发送消息
     * @param message [Message] 消息内容
     */
    fun sendMessage(message: Message) {
        mMessageLiveEvent.value = message
    }

    /**
     * 同步发送消息
     * @param what [Int]  消息编号
     * @param  obj [Any] 消息数据
     */
    fun sendMessage(what: Int, obj: Any) {
        val message: Message = Message.obtain()
        message.what = what
        message.obj = obj
        mMessageLiveEvent.value = message
    }

    /**
     * 异步发送消息
     * @param message [Message] 消息内容
     */
    fun postMessage(message: Message) {
        mMessageLiveEvent.postValue(message)
    }

    /**
     * 异步发送消息
     * @param what [Int]  消息编号
     * @param  obj [Any] 消息数据
     */
    fun postMessage(what: Int = 0, obj: Any?) {
        val message: Message = Message.obtain()
        message.what = what
        message.obj = obj
        mMessageLiveEvent.postValue(message)
    }

    /**
     * 同步更新状态
     * @param status [Int] 状态码
     */
    fun sendUpdateStatus(@StatusCode status: Int) {
        mStatusLiveEvent.value = status
    }

    /**
     * 异步更新状态
     * @param status [Int] 状态码
     */
    fun postUpdateStatus(@StatusCode status: Int) {
        mStatusLiveEvent.postValue(status)
    }

    /**
     * 获取消息事件总线
     * @return  mMessageLiveEvent [MessageLiveEvent]
     */
    fun fetchMessageLiveEvent() = mMessageLiveEvent

    /**
     * 获取状态事件总线
     * @return mStatusLiveEvent [StatusLiveEvent]
     */
    fun fetchStatusLiveEvent() = mStatusLiveEvent

}