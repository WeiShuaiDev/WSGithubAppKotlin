package com.linwei.cams_mvvm.mvvm

import androidx.lifecycle.ViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `View` 模块,绑定生命周期，定义 `IView` 接口
 *-----------------------------------------------------------------------
 */
interface IView<VM : ViewModel> {

    /**
     * 获取 [ViewModel] 对象
     * @return VM [ViewModel]
     */
    fun createViewModel(): VM?

    /**
     * 绑定 [ViewModel] 对象，到 `DataBinding` 中
     */
    fun bindViewModel()
}