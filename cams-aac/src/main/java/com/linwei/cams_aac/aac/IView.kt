package com.linwei.cams_aac.aac

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: AAC架构中 `View` 模块,绑定生命周期，定义 `IView` 接口
 *-----------------------------------------------------------------------
 */
interface IView<VM : ViewModel> {

    /**
     * 获取 [ViewModel] 对象
     * @return VM [ViewModel]
     */
    fun createViewModel(): VM?
}