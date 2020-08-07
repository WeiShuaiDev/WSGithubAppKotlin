package com.linwei.cams_mvp.mvp

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 `Presenter` 接口定义
 *-----------------------------------------------------------------------
 */
interface IPresenter {
    /**
     * 初始化操作
     */
    fun onStart()

    /**
     * 在框架 `BaseActivity#onDestroy()` 方法，会默认调用 `IPresenter#onDestroy()`
     */
    fun onDestroy()
}