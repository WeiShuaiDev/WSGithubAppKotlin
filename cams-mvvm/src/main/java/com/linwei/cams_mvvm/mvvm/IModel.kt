package com.linwei.cams_mvvm.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构 `Model` 模块,绑定生命周期，定义 `IModel` 接口
 *-----------------------------------------------------------------------
 */
interface IModel : LifecycleObserver {

    /**
     * 资源回收
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
}