package com.linwei.cams.listener

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 导航栏点击事件接口回调接口定义
 *-----------------------------------------------------------------------
 */

/**
 * `TopBar` 左边监听
 */
interface OnTopBarLeftClickListener {
    fun onLeftClick()
}

/**
 * `TopBar` 右边监听
 */
interface OnTopBarRightClickListener {
    fun onRightClick()
}